package uddbe.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import uddbe.dto.AdvancedSearchParameter;
import uddbe.dto.GeoLocationQueryDTO;
import uddbe.dto.SearchResult;
import uddbe.dto.SearchResultContent;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.stereotype.Service;
import uddbe.service.mapper.ContractMapper;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchOperations elasticsearchTemplate;
    private final ContractMapper contractMapper;
    private final GeoLocationService geolocationService;

    @Override
    public SearchResult simpleSearch(List<String> keywords, Pageable pageable) {
        var searchQueryBuilder =
            new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords))
                    .withHighlightQuery(buildHighlightQuery())
                    .withPageable(pageable);

        var result = runQuery(searchQueryBuilder.build());
        return result;
    }

    @Override
    public SearchResult advancedSearch(List<AdvancedSearchParameter> expressions, Pageable pageable) {
        if (expressions.size() < 1) {
            throw new MalformedQueryException("Search query malformed.");
        }

        var searchQueryBuilder =
            new NativeQueryBuilder().withQuery(buildAdvancedSearchQuery(expressions))
                    .withHighlightQuery(buildHighlightQuery())
                    .withPageable(pageable);

        var result = runQuery(searchQueryBuilder.build());
        return result;
    }

    public SearchResult geolocationSearch(GeoLocationQueryDTO geoLocationQueryDTO, Pageable pageable) {
        GeoPoint geoPoint = null;
        try {
            geoPoint = geolocationService.getCoordinatesBasedOnAddress(geoLocationQueryDTO.getCity());
            GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location")
                    .point(geoPoint.getLat(),geoPoint.getLon())
                    .distance(geoLocationQueryDTO.getRadius(), DistanceUnit.KILOMETERS);

            var searchQuery = new NativeQueryBuilder().withQuery(new StringQuery(geoDistanceQueryBuilder.toString()))
                    .withHighlightQuery(buildHighlightQuery())
                    .withPageable(pageable);

            return runQuery(searchQuery.build());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HighlightQuery buildHighlightQuery() {
        List<HighlightField> fields = new ArrayList<>();
        fields.add(new HighlightField("content_sr"));
        fields.add(new HighlightField("content_en"));
        fields.add(new HighlightField("law_content_sr"));
        fields.add(new HighlightField("law_content_sr"));
        fields.add(new HighlightField("agency_signatory_name"));
        fields.add(new HighlightField("agency_signatory_surname"));
        fields.add(new HighlightField("government_name"));
        fields.add(new HighlightField("government_level"));
        Highlight highlight1 = new Highlight(fields);
        HighlightQuery query = new HighlightQuery(highlight1, ContractIndex.class);
        return query;
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

    private String extractField(String field) {
        switch (field) {
            case "Contract content":
                return "content_sr";
            case "Law content":
                return "law_content_sr";
            case "Administrative level":
                return "government_level";
            case "Government name":
                return "government_name";
            case "Employee surname":
                return "agency_signatory_surname";
            default:
                return "agency_signatory_name";
        }

    }

    public Query buildAdvancedSearchQuery(List<AdvancedSearchParameter> operands) {
        return BoolQuery.of(b -> {
            AtomicBoolean firstToken = new AtomicBoolean(true);
            operands.forEach(operand -> {
                String field = extractField(operand.getField());
                String value = operand.getValue();
                String operator = operand.getOperator();
                boolean isPhrase = operand.isPhrase();
                boolean isNegation = operand.isNegation();

                if (isPhrase){
                    if (firstToken.get()){
                        if (operands.size() > 1){
                            if (isNegation) {
                                b.mustNot(s -> s.matchPhrase(mt -> mt.field(field).query(value)));
                            }
                            else if (operands.get(1).getOperator().equalsIgnoreCase("or")){
                                b.should(s -> s.matchPhrase(mt -> mt.field(field).query(value)));
                            } else {
                                b.must(s -> s.matchPhrase(mt -> mt.field(field).query(value)));
                            }
                        }else {
                            if (isNegation) {
                                b.mustNot(s -> s.matchPhrase(mt -> mt.field(field).query(value)));
                            } else {
                                b.must(s -> s.matchPhrase(mt -> mt.field(field).query(value)));
                            }
                        }
                        firstToken.set(false);
                    }else{
                        if (isNegation) {
                            b.mustNot(mn -> mn.matchPhrase(mt -> mt.field(field).query(value)));
                        }
                        else if (operator.equalsIgnoreCase("AND")) {
                            b.must(m -> m.matchPhrase(mt -> mt.field(field).query(value)));
                        } else if (operator.equalsIgnoreCase("OR")) {
                            b.should(s -> s.matchPhrase(mt -> mt.field(field).query(value)));
                        }
                    }
                }else{
                    if (firstToken.get()){
                        if (operands.size() > 1){
                            if (isNegation) {
                                b.mustNot(s -> s.match(mt -> mt.field(field).query(value)));
                            }
                            else if (operands.get(1).getOperator().equalsIgnoreCase("or")){
                                b.should(s -> s.match(mt -> mt.field(field).query(value)));
                            }else{
                                b.must(s -> s.match(mt -> mt.field(field).query(value)));
                            }
                        }else{
                            if (isNegation) {
                                b.mustNot(s -> s.match(mt -> mt.field(field).query(value)));
                            } else {
                                b.must(s -> s.match(mt -> mt.field(field).query(value)));
                            }
                        }
                        firstToken.set(false);
                    }else{
                        if (isNegation) {
                            b.mustNot(mn -> mn.match(mt -> mt.field(field).query(value)));
                        }
                        else if (operator.equalsIgnoreCase("AND")) {
                            b.must(m -> m.match(mt -> mt.field(field).query(value)));
                        } else if (operator.equalsIgnoreCase("OR")) {
                            b.should(s -> s.match(mt -> mt.field(field).query(value)));
                        }
                    }
                }
            });
            return b;
        })._toQuery();
    }

    private SearchResult runQuery(NativeQuery searchQuery) {

        var searchHits = elasticsearchTemplate.search(searchQuery, ContractIndex.class,
            IndexCoordinates.of("document_index"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        var pagination = (Page<ContractIndex>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);
        var highlightResults = new ArrayList<SearchResultContent>();
        for (SearchHit<ContractIndex> hit : searchHitsPaged.getSearchHits()) {
            Map<String, String> highlight = hit.getHighlightFields().keySet().stream()
                .collect(Collectors.toMap(k -> k, k -> hit.getHighlightFields().get(k).get(0)));
            var result = contractMapper.mapSearchResult(hit.getContent(), highlight);
            highlightResults.add(result);
        }
        return new SearchResult(highlightResults, pagination.getTotalPages(), pagination.getNumber(), pagination.getSize());
    }
}
