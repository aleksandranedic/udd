package uddbe.service.interfaces;

import uddbe.dto.ContractDTO;

public interface DocumentParser {
    ContractDTO parseContract(String documentContent);
}
