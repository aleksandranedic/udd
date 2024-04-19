package uddbe.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uddbe.dto.ContractDTO;

@Service
public interface IndexingService {
    ContractDTO saveContract(MultipartFile documentFile);

    String indexDocument(ContractDTO contractDTO);
    ContractDTO saveLaw(MultipartFile documentFile);

    String indexLaw(ContractDTO contractDTO);
}
