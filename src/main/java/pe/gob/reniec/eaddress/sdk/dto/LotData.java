package pe.gob.reniec.eaddress.sdk.dto;

/**
 * @author Alexander Llacho
 */
public class LotData {

    private String id;
    private String tag;
    private String subject;
    private Integer quantity;
    private Integer maximumQuantity;
    private Integer errors;
    private String doc;
    private String docType;
    private String receiver;
    private Long createdAt;

    public LotData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(Integer maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public Integer getErrors() {
        return errors;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LotData{" +
                "id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                ", subject='" + subject + '\'' +
                ", quantity=" + quantity +
                ", maximumQuantity=" + maximumQuantity +
                ", errors=" + errors +
                ", doc='" + doc + '\'' +
                ", docType='" + docType + '\'' +
                ", receiver='" + receiver + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}