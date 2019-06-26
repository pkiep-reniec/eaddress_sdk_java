package pe.gob.reniec.eaddress.sdk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    @JsonProperty("emisor")
    private DataPerson issuer;
    @JsonProperty("delegado")
    private DataPerson delegate;
    @JsonProperty("asunto")
    private String subject;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("cantidad")
    private Integer quantity;
    @JsonProperty("aplicacion")
    private DataApp application;
    @JsonProperty("checksum")
    private String checksum;

    public Metadata() {
    }

    public DataPerson getIssuer() {
        return issuer;
    }

    public void setIssuer(DataPerson issuer) {
        this.issuer = issuer;
    }

    public DataPerson getDelegate() {
        return delegate;
    }

    public void setDelegate(DataPerson delegate) {
        this.delegate = delegate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public DataApp getApplication() {
        return application;
    }

    public void setApplication(DataApp application) {
        this.application = application;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "issuer=" + issuer +
                ", delegate=" + delegate +
                ", subject='" + subject + '\'' +
                ", tag='" + tag + '\'' +
                ", quantity=" + quantity +
                ", application=" + application +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}