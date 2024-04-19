package uddbe.controller;

import org.springframework.web.bind.annotation.*;
import uddbe.dto.ContractDTO;
import uddbe.dto.ContractFileDTO;
import uddbe.dto.ContractFileResponseDTO;
import uddbe.service.interfaces.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexingService indexingService;

    @PostMapping(value = "contract/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDTO addDocumentFile(
        @ModelAttribute ContractFileDTO documentFile) {
        return indexingService.saveContract(documentFile.file());
    }

    @PostMapping(value = "contract")
    @ResponseStatus(HttpStatus.CREATED)
    public String indexContract(@RequestBody ContractDTO contract) {
        return indexingService.indexDocument(contract);
    }

    @PostMapping(value = "law/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractDTO addLawFile(
            @ModelAttribute ContractFileDTO documentFile) {
        return indexingService.saveLaw(documentFile.file());
    }

    @PostMapping(value = "law")
    @ResponseStatus(HttpStatus.CREATED)
    public String indexLaw(@RequestBody ContractDTO contract) {
        return indexingService.indexLaw(contract);
    }
}
