package uddbe.service.interfaces;

import uddbe.indexmodel.ContractIndex;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    Page<ContractIndex> simpleSearch(List<String> keywords, Pageable pageable);

    Page<ContractIndex> advancedSearch(List<String> expression, Pageable pageable);
}
