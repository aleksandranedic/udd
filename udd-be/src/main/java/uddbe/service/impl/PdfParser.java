package uddbe.service.impl;

import org.springframework.stereotype.Service;
import uddbe.dto.ContractDTO;
import uddbe.service.interfaces.DocumentParser;

@Service
public class PdfParser implements DocumentParser {
    @Override
    public ContractDTO parseContract(String documentContent) {
        String[] lines = documentContent.split("\n");
        ContractDTO contract = new ContractDTO();
        contract.setAgencyAddress(lines[1]);
        contract.setAgencyEmail(lines[2]);
        contract.setAgencyPhone(lines[3]);
        contract.setGovernmentName(lines[4].split("Uprava za ")[1]);
        contract.setGovernmentLevel(lines[5].split("Nivo uprave: ")[1]);
        contract.setGovernmentAddress(lines[6]);
        contract.setGovernmentEmail(lines[7]);
        contract.setGovernmentPhone(lines[8]);
        contract.setContractTitle(lines[9]);

        String content = "";
        int startingContentIndex = findStartingContentIndex(lines, 10);
        for (int i = startingContentIndex; i < lines.length-4; i++) {
            content += lines[i] + "\n";
        }
        contract.setContent(content);

        String[] clientSignatoryName = lines[lines.length-4].split(" ");
        contract.setClientSignatoryName(clientSignatoryName[0]);
        contract.setClientSignatorySurname(clientSignatoryName[1]);

        String[] agencySignatoryName = lines[lines.length-2].split(" ");
        contract.setAgencySignatoryName(agencySignatoryName[0]);
        contract.setAgencySignatorySurname(agencySignatoryName[1]);

        return contract;
    }

    private int findStartingContentIndex(String[] lines, int startingIndex) {
        for (int i = startingIndex; i < lines.length; i++) {
            if (lines[i].contains("u daljem tekstu agencija")) {
                return i+1;
            }
        }
        return -1;
    }
}
