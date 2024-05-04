package uddbe.service.mapper;

import org.springframework.stereotype.Service;
import uddbe.dto.ContractDTO;
import uddbe.dto.SearchResultContent;
import uddbe.indexmodel.ContractIndex;
import uddbe.model.ContractTable;

import java.util.Map;

@Service
public class ContractMapper {

    public ContractTable mapFromContractDTOToContractTable(ContractDTO contractDTO, ContractTable contractTable) {
        contractTable.setContent(contractDTO.getContent());
        contractTable.setAgencyAddress(contractDTO.getAgencyAddress());
        contractTable.setAgencyEmail(contractDTO.getAgencyEmail());
        contractTable.setAgencyPhone(contractDTO.getAgencyPhone());
        contractTable.setGovernmentName(contractDTO.getGovernmentName());
        contractTable.setGovernmentLevel(contractDTO.getGovernmentLevel());
        contractTable.setGovernmentAddress(contractDTO.getGovernmentAddress());
        contractTable.setGovernmentEmail(contractDTO.getGovernmentEmail());
        contractTable.setGovernmentPhone(contractDTO.getGovernmentPhone());
        contractTable.setContractTitle(contractDTO.getContractTitle());
        contractTable.setClientSignatoryName(contractDTO.getClientSignatoryName());
        contractTable.setClientSignatorySurname(contractDTO.getClientSignatorySurname());
        contractTable.setAgencySignatoryName(contractDTO.getAgencySignatoryName());
        contractTable.setAgencySignatorySurname(contractDTO.getAgencySignatorySurname());
        return contractTable;
    }

    public ContractIndex mapFromContractTableToContractIndex(ContractTable contractTable, ContractIndex newIndex) {
        newIndex.setTitle(contractTable.getTitle());
        newIndex.setDatabaseId(contractTable.getId());
        newIndex.setServerFilename(contractTable.getServerFilename());
        newIndex.setAgencyAddress(contractTable.getAgencyAddress());
        newIndex.setAgencyEmail(contractTable.getAgencyEmail());
        newIndex.setAgencyPhone(contractTable.getAgencyPhone());
        newIndex.setGovernmentName(contractTable.getGovernmentName());
        newIndex.setGovernmentLevel(contractTable.getGovernmentLevel());
        newIndex.setGovernmentAddress(contractTable.getGovernmentAddress());
        newIndex.setGovernmentEmail(contractTable.getGovernmentEmail());
        newIndex.setGovernmentPhone(contractTable.getGovernmentPhone());
        newIndex.setContractTitle(contractTable.getContractTitle());
        newIndex.setClientSignatoryName(contractTable.getClientSignatoryName());
        newIndex.setClientSignatorySurname(contractTable.getClientSignatorySurname());
        newIndex.setAgencySignatoryName(contractTable.getAgencySignatoryName());
        newIndex.setAgencySignatorySurname(contractTable.getAgencySignatorySurname());
        return newIndex;
    }

    public SearchResultContent mapSearchResult(ContractIndex contractIndex, Map<String, String> highlights) {
        SearchResultContent searchResult = new SearchResultContent();
        searchResult.setServerFilename(contractIndex.getServerFilename());
        searchResult.setAgencyAddress(contractIndex.getAgencyAddress());
        searchResult.setAgencyEmail(contractIndex.getAgencyEmail());
        searchResult.setAgencyPhone(contractIndex.getAgencyPhone());
        searchResult.setGovernmentName(contractIndex.getGovernmentName());
        searchResult.setGovernmentLevel(contractIndex.getGovernmentLevel());
        searchResult.setGovernmentAddress(contractIndex.getGovernmentAddress());
        searchResult.setGovernmentEmail(contractIndex.getGovernmentEmail());
        searchResult.setGovernmentPhone(contractIndex.getGovernmentPhone());
        searchResult.setContractTitle(contractIndex.getContractTitle());
        searchResult.setClientSignatoryName(contractIndex.getClientSignatoryName());
        searchResult.setClientSignatorySurname(contractIndex.getClientSignatorySurname());
        searchResult.setAgencySignatoryName(contractIndex.getAgencySignatoryName());
        searchResult.setAgencySignatorySurname(contractIndex.getAgencySignatorySurname());
        searchResult.setContent(contractIndex.getContentSr());
        searchResult.setLawContent(contractIndex.getLawContentSr());
        searchResult.setHighlights(highlights);
        searchResult.setId(contractIndex.getId());
        return searchResult;
    }
}
