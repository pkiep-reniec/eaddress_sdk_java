package pe.gob.reniec.eaddress.sdk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Alexander Llacho
 */
public class Metadata {

    @JsonProperty("tipo_acuse")
    private String accuseType;
    @JsonProperty("emisor")
    private DataPerson issuer;
    @JsonProperty("aplicacion")
    private DataApp application;
    @JsonProperty("asunto")
    private String subject;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("cantidad")
    private Integer quantity;
    @JsonProperty("fecha_hora")
    private Date date;

    public Metadata() {
    }

    public String getAccuseType() {
        return accuseType;
    }

    public void setAccuseType(String accuseType) {
        this.accuseType = accuseType;
    }

    public DataPerson getIssuer() {
        return issuer;
    }

    public void setIssuer(DataPerson issuer) {
        this.issuer = issuer;
    }

    public DataApp getApplication() {
        return application;
    }

    public void setApplication(DataApp application) {
        this.application = application;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "accuseType='" + accuseType + '\'' +
                ", issuer=" + issuer +
                ", application=" + application +
                ", subject='" + subject + '\'' +
                ", tag='" + tag + '\'' +
                ", quantity=" + quantity +
                ", date=" + date +
                '}';
    }
}
