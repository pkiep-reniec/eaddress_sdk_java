package pe.gob.reniec.eaddress.sdk.dto;

/**
 * @author Alexander Llacho
 */
public class NotificationsResponse {

    private String id;
    private String lotId;
    private String tag;
    private String issuerDoc;
    private String issuerDocType;
    private String issuerName;
    private String receiverDoc;
    private String receiverDocType;
    private String receiverName;
    private String subject;
    private Long sentAt;
    private Long receivedAt;
    private Long readAt;
    private String errorReason;
    private Boolean withAttachments;
    private Long createdAt;
    private String link;

    public NotificationsResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIssuerDoc() {
        return issuerDoc;
    }

    public void setIssuerDoc(String issuerDoc) {
        this.issuerDoc = issuerDoc;
    }

    public String getIssuerDocType() {
        return issuerDocType;
    }

    public void setIssuerDocType(String issuerDocType) {
        this.issuerDocType = issuerDocType;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getReceiverDoc() {
        return receiverDoc;
    }

    public void setReceiverDoc(String receiverDoc) {
        this.receiverDoc = receiverDoc;
    }

    public String getReceiverDocType() {
        return receiverDocType;
    }

    public void setReceiverDocType(String receiverDocType) {
        this.receiverDocType = receiverDocType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getSentAt() {
        return sentAt;
    }

    public void setSentAt(Long sentAt) {
        this.sentAt = sentAt;
    }

    public Long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Long receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Long getReadAt() {
        return readAt;
    }

    public void setReadAt(Long readAt) {
        this.readAt = readAt;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public Boolean getWithAttachments() {
        return withAttachments;
    }

    public void setWithAttachments(Boolean withAttachments) {
        this.withAttachments = withAttachments;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "NotificationsResponse{" +
                "id='" + id + '\'' +
                ", lotId='" + lotId + '\'' +
                ", tag='" + tag + '\'' +
                ", issuerDoc='" + issuerDoc + '\'' +
                ", issuerDocType='" + issuerDocType + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", receiverDoc='" + receiverDoc + '\'' +
                ", receiverDocType='" + receiverDocType + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", subject='" + subject + '\'' +
                ", sentAt=" + sentAt +
                ", receivedAt=" + receivedAt +
                ", readAt=" + readAt +
                ", errorReason='" + errorReason + '\'' +
                ", withAttachments=" + withAttachments +
                ", createdAt=" + createdAt +
                ", link='" + link + '\'' +
                '}';
    }
}