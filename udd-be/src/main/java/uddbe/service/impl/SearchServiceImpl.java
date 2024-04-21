package uddbe.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import uddbe.exceptionhandling.exception.MalformedQueryException;
import uddbe.indexmodel.ContractIndex;
import uddbe.service.interfaces.SearchService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchOperations elasticsearchTemplate;

    @Override
    public Page<ContractIndex> simpleSearch(List<String> keywords, Pageable pageable) {
        List<HighlightField> fields = new ArrayList<>();
        fields.add(new HighlightField("content_sr"));
        fields.add(new HighlightField("content_en"));
        fields.add(new HighlightField("law_content_sr"));
        fields.add(new HighlightField("law_content_sr"));
        Highlight highlight1 = new Highlight(fields);
        HighlightQuery query = new HighlightQuery(highlight1, ContractIndex.class);

        var searchQueryBuilder =
            new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords))
                    .withHighlightQuery(query)
                    .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<ContractIndex> advancedSearch(List<String> expression, Pageable pageable) {
        if (expression.size() != 3) {
            throw new MalformedQueryException("Search query malformed.");
        }

        String operation = expression.get(1);
        expression.remove(1);
        var searchQueryBuilder =
            new NativeQueryBuilder().withQuery(buildAdvancedSearchQuery(expression, operation))
                .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    private Query buildSimpleSearchQuery(List<String> tokens) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
             // Matches documents with exact term in "title" field
             b.should(sb -> sb.term(m -> m.field("title").value(token)));
                b.should(sb -> sb.term(m -> m.field("agency_signatory_name").value(token)));
                b.should(sb -> sb.term(m -> m.field("agency_signatory_surname").value(token)));
                b.should(sb -> sb.term(m -> m.field("government_name").value(token)));
                b.should(sb -> sb.term(m -> m.field("government_level").value(token)));

            // Matches documents with any of the specified terms in "title" field
            var terms = new ArrayList<>(tokens);
            var titleTerms = new TermsQueryField.Builder()
                .value(terms.stream().map(FieldValue::of).toList())
                .build();
            b.should(sb -> sb.terms(m -> m.field("title").terms(titleTerms)));
                b.should(sb -> sb.terms(m -> m.field("agency_signatory_name").terms(titleTerms)));
                b.should(sb -> sb.terms(m -> m.field("agency_signatory_surname").terms(titleTerms)));
                b.should(sb -> sb.terms(m -> m.field("government_name").terms(titleTerms)));
                b.should(sb -> sb.terms(m -> m.field("government_level").terms(titleTerms)));

                // Match Query - full-text search in other fields
                b.should(sb -> sb.match(m -> m.field("content_sr").query(token)));
                b.should(sb -> sb.match(m -> m.field("content_sr").query(token)));
                b.should(sb -> sb.match(m -> m.field("law_content_sr").query(token)));
                b.should(sb -> sb.match(m -> m.field("law_content_en").query(token)));

                // Fuzzy Query - similar to Match Query with fuzziness, useful for spelling errors
                // Matches documents with fuzzy matching in "title" field
           b.should(sb -> sb.match(
               m -> m.field("title").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(
                        m -> m.field("agency_signatory_name").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(
                        m -> m.field("agency_signatory_surname").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(
                        m -> m.field("government_name").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(
                        m -> m.field("government_level").fuzziness(Fuzziness.ONE.asString()).query(token)));
            });

            return b;
        })))._toQuery();
    }

    private Query buildAdvancedSearchQuery(List<String> operands, String operation) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            var field1 = operands.get(0).split(":")[0];
            var value1 = operands.get(0).split(":")[1];
            var field2 = operands.get(1).split(":")[0];
            var value2 = operands.get(1).split(":")[1];

            switch (operation) {
                case "AND":
                    b.must(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.must(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "OR":
                    b.should(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.should(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "NOT":
                    b.must(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.mustNot(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
            }

            return b;
        })))._toQuery();
    }

    private Page<ContractIndex> runQuery(NativeQuery searchQuery) {

        var searchHits = elasticsearchTemplate.search(searchQuery, ContractIndex.class,
            IndexCoordinates.of("document_index"));

        var fields = searchHits.getSearchHit(0).getHighlightFields();
        System.out.println(fields);
      //  System.out.println(fields.values());
        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        return (Page<ContractIndex>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);
    }
}
