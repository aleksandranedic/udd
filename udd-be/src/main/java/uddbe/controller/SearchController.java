package uddbe.controller;

import uddbe.dto.GeoLocationQueryDTO;
import uddbe.dto.SearchQueryDTO;
import uddbe.dto.SearchResult;
import uddbe.dto.SimpleSearchQueryDTO;
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
    public SearchResult simpleSearch(@RequestBody SimpleSearchQueryDTO simpleSearchQuery,
                                     Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public SearchResult advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                              Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.parameters(), pageable);
    }

    @PostMapping("/geolocation")
    public SearchResult geoLocationSearch(@RequestBody GeoLocationQueryDTO geoLocationSearchQuery,
                                       Pageable pageable) {
        return searchService.geolocationSearch(geoLocationSearchQuery, pageable);
    }
}
