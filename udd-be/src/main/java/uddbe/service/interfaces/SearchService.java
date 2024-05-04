package uddbe.service.interfaces;

import uddbe.dto.SearchResult;
import uddbe.indexmodel.ContractIndex;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    SearchResult simpleSearch(List<String> keywords, Pageable pageable);

    SearchResult advancedSearch(List<String> expression, Pageable pageable);
}
