package uddbe.controller;

import uddbe.dto.ContractFileDTO;
import uddbe.dto.ContractFileResponseDTO;
import uddbe.service.interfaces.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexingService indexingService;

    @PostMapping(value = "contract")
    @ResponseStatus(HttpStatus.CREATED)
    public ContractFileResponseDTO addDocumentFile(
        @ModelAttribute ContractFileDTO documentFile) {
        var serverFilename = indexingService.indexDocument(documentFile.file());
        return new ContractFileResponseDTO(serverFilename);
    }
}
