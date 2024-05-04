package uddbe.indexmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "docs_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class ContractIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "title")
    private String title;

    @Field(type = FieldType.Text, store = true, name = "content_sr", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String contentSr;

    @Field(type = FieldType.Text, store = true, name = "content_en", analyzer = "english", searchAnalyzer = "english")
    private String contentEn;

    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
    private String serverFilename;

    @Field(type = FieldType.Integer, store = true, name = "database_id")
    private Integer databaseId;

    @Field(type = FieldType.Text, store = true, name = "agency_address", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String agencyAddress;

    @Field(type = FieldType.Text, store = true, name = "agency_email", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String agencyEmail;

    @Field(type = FieldType.Text, store = true, name = "agency_phone", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String agencyPhone;

    @Field(type = FieldType.Keyword, store = true, name = "government_name")
    private String governmentName;

    @Field(type = FieldType.Text, store = true, name = "government_level", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String governmentLevel;

    @Field(type = FieldType.Keyword, store = true, name = "government_address")
    private String governmentAddress;

    @Field(type = FieldType.Text, store = true, name = "government_email", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String governmentEmail;

    @Field(type = FieldType.Text, store = true, name = "government_phone", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String governmentPhone;

    @Field(type = FieldType.Text, store = true, name = "contact_title", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String contractTitle;

    @Field(type = FieldType.Text, store = true, name = "client_signatory_name", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String clientSignatoryName;

    @Field(type = FieldType.Text, store = true, name = "client_signatory_surname", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String clientSignatorySurname;

    @Field(type = FieldType.Text, store = true, name = "agency_signatory_name", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String agencySignatoryName;

    @Field(type = FieldType.Keyword, store = true, name = "agency_signatory_surname")
    private String agencySignatorySurname;

    @Field(type = FieldType.Text, store = true, name = "law_content_sr", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String lawContentSr;

    @Field(type = FieldType.Text, store = true, name = "law_content_en", analyzer = "english", searchAnalyzer = "english")
    private String lawContentEn;


    @GeoPointField
    @Field(store = true, name = "location")
    private GeoPoint location;
}
