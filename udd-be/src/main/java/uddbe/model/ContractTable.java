package uddbe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contract_table")
public class ContractTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "server_filename")
    private String serverFilename;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "agency_address")
    private String agencyAddress;

    @Column(name = "agency_email")
    private String agencyEmail;

    @Column(name = "agency_phone")
    private String agencyPhone;

    @Column(name = "government_name")
    private String governmentName;

    @Column(name = "government_level")
    private String governmentLevel;

    @Column(name = "government_address")
    private String governmentAddress;

    @Column(name = "government_email")
    private String governmentEmail;

    @Column(name = "government_phone")
    private String governmentPhone;

    @Column(name = "contact_title")
    private String contractTitle;

    @Column(name = "client_signatory_name")
    private String clientSignatoryName;

    @Column(name = "client_signatory_surname")
    private String clientSignatorySurname;

    @Column(name = "agency_signatory_name")
    private String agencySignatoryName;

    @Column(name = "agency_signatory_surname")
    private String agencySignatorySurname;
}
