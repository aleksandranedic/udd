package uddbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private String id;
    private String content;
    private String agencyAddress;
    private String agencyEmail;
    private String agencyPhone;
    private String governmentName;
    private String governmentLevel;
    private String governmentAddress;
    private String governmentEmail;
    private String governmentPhone;
    private String contractTitle;
    private String clientSignatoryName;
    private String clientSignatorySurname;
    private String agencySignatoryName;
    private String agencySignatorySurname;
    private String lawContent;
    private String serverFilename;
}
