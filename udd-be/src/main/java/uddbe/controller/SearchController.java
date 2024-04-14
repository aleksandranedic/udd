package uddbe.controller;

import uddbe.dto.SearchQueryDTO;
import uddbe.indexmodel.ContractIndex;
import uddbe.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/simple")
    public Page<ContractIndex> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                            Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<ContractIndex> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                              Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }
}
