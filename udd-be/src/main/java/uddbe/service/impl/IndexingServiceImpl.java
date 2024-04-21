package uddbe.service.impl;

import org.elasticsearch.common.geo.GeoPoint;
import uddbe.dto.ContractDTO;
import uddbe.exceptionhandling.exception.LoadingException;
import uddbe.exceptionhandling.exception.StorageException;
import uddbe.indexmodel.ContractIndex;
import uddbe.indexrepository.ContractIndexRepository;
import uddbe.model.ContractTable;
import uddbe.respository.ContractRepository;
import uddbe.service.interfaces.FileService;
import uddbe.service.interfaces.IndexingService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.language.detect.LanguageDetector;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uddbe.service.mapper.ContractMapper;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

    private final ContractIndexRepository contractIndexRepository;

    private final ContractRepository contractRepository;

    private final FileService fileService;

    private final LanguageDetector languageDetector;

    private final ContractMapper contractMapper;

    private final PdfParser pdfParser;

    private final GeoLocationService geolocationService;

    @Override
    @Transactional
    public ContractDTO saveContract(MultipartFile documentFile) {
        var newEntity = new ContractTable();
        var title = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
        newEntity.setTitle(title);
        newEntity.setMimeType(detectMimeType(documentFile));
        var documentContent = extractDocumentContent(documentFile);
        ContractDTO contractDTO = pdfParser.parseContract(documentContent);
        newEntity = contractMapper.mapFromContractDTOToContractTable(contractDTO, newEntity);
        var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
        newEntity.setServerFilename(serverFilename);
        var savedContract = contractRepository.save(newEntity);
        contractDTO.setId(savedContract.getId());
        return contractDTO;
    }

    @Override
    @Transactional
    public String indexDocument(ContractDTO contractDTO) {
        try {
            ContractIndex newIndex = new ContractIndex();
            Optional<ContractTable> contractTableOpt = contractRepository.findById(contractDTO.getId());
            if(contractTableOpt.isEmpty()){
                throw new RuntimeException("Contract with id " + contractDTO.getId() + " not found");
            }
            ContractTable contractTable = contractTableOpt.get();
            contractTable = contractMapper.mapFromContractDTOToContractTable(contractDTO, contractTable);
            contractRepository.save(contractTable);

            newIndex = contractMapper.mapFromContractTableToContractIndex(contractTable, newIndex);
            GeoPoint geoPoint = geolocationService.getCoordinatesBasedOnAddress(newIndex.getGovernmentAddress());
            newIndex.setLocation(geoPoint);
            if (detectLanguage(contractTable.getContent()).equals("SR")) {
                newIndex.setContentSr(contractTable.getContent());
            } else {
                newIndex.setContentEn(contractTable.getContent());
            }

            contractIndexRepository.save(newIndex);

            return contractTable.getServerFilename();

        } catch (Exception e) {
            throw new RuntimeException("Error while indexing document");
        }
    }

    @Override
    @Transactional
    public ContractDTO saveLaw(MultipartFile documentFile) {
        var newEntity = new ContractTable();
        var title = Objects.requireNonNull(documentFile.getOriginalFilename()).split("\\.")[0];
        newEntity.setTitle(title);
        newEntity.setMimeType(detectMimeType(documentFile));
        var documentContent = extractDocumentContent(documentFile);
        ContractDTO contractDTO = new ContractDTO();
        newEntity.setLawContent(documentContent);
        var serverFilename = fileService.store(documentFile, UUID.randomUUID().toString());
        newEntity.setServerFilename(serverFilename);
        var savedContract = contractRepository.save(newEntity);
        contractDTO.setId(savedContract.getId());
        contractDTO.setLawContent(savedContract.getLawContent());
        return contractDTO;
    }

    @Override
    @Transactional
    public String indexLaw(ContractDTO contractDTO) {
        try {
            ContractIndex newIndex = new ContractIndex();
            Optional<ContractTable> contractTableOpt = contractRepository.findById(contractDTO.getId());
            if(contractTableOpt.isEmpty()){
                throw new RuntimeException("Contract with id " + contractDTO.getId() + " not found");
            }
            ContractTable contractTable = contractTableOpt.get();
            contractTable.setLawContent(contractDTO.getLawContent());
            contractRepository.save(contractTable);

            if (detectLanguage(contractTable.getLawContent()).equals("SR")) {
                newIndex.setLawContentSr(contractTable.getLawContent());
            } else {
                newIndex.setLawContentEn(contractTable.getLawContent());
            }
            newIndex.setDatabaseId(contractTable.getId());
            newIndex.setServerFilename(contractTable.getServerFilename());
            newIndex.setTitle(contractTable.getTitle());

            contractIndexRepository.save(newIndex);

            return contractTable.getServerFilename();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while indexing document");
        }
    }

    private String extractDocumentContent(MultipartFile multipartPdfFile) {
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String detectLanguage(String text) {
        var detectedLanguage = languageDetector.detect(text).getLanguage().toUpperCase();
        if (detectedLanguage.equals("HR")) {
            detectedLanguage = "SR";
        }

        return detectedLanguage;
    }

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
            !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }
}