package pe.gob.reniec.eaddress.sdk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexander Llacho
 */
public class Config {

    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("security_uri")
    private String securityUri;
    @JsonProperty("eaddress_uri")
    private String eaddressUri;
    @JsonProperty("doc_type")
    private String docType;
    @JsonProperty("doc")
    private String doc;
    @JsonProperty("name")
    private String name;
    @JsonProperty("app_name")
    private String appName;

    public Config() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getSecurityUri() {
        return securityUri;
    }

    public void setSecurityUri(String securityUri) {
        this.securityUri = securityUri;
    }

    public String getEaddressUri() {
        return eaddressUri;
    }

    public void setEaddressUri(String eaddressUri) {
        this.eaddressUri = eaddressUri;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", securityUri='" + securityUri + '\'' +
                ", eaddressUri='" + eaddressUri + '\'' +
                ", docType='" + docType + '\'' +
                ", doc='" + doc + '\'' +
                ", name='" + name + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}