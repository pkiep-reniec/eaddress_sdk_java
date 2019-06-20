package pe.gob.reniec.eaddress.sdk.dto;

import java.util.List;

/**
 * @author Alexander Llacho
 */
public class NotificationResponse {

    private String id;
    private String lotId;
    private String tag;
    private String issuerDoc;
    private String issuerDocType;
    private String issuerName;
    private String issuerNameShort;
    private String issuerIcon;
    private String receiverDoc;
    private String receiverDocType;
    private String receiverName;
    private Integer serviceId;
    private String subject;
    private String messageHtml;
    private Boolean important;
    private Long sentAt;
    private String jsonSent;
    private String jsonSentSignature;
    private Long receivedAt;
    private String jsonReceived;
    private String jsonReceivedSignature;
    private Long readAt;
    private Integer readCount;
    private String code;
    private String errorReason;
    private List<Attachment> attachments;
    private Long createdAt;

    public NotificationResponse() {
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

    public String getIssuerNameShort() {
        return issuerNameShort;
    }

    public void setIssuerNameShort(String issuerNameShort) {
        this.issuerNameShort = issuerNameShort;
    }

    public String getIssuerIcon() {
        return issuerIcon;
    }

    public void setIssuerIcon(String issuerIcon) {
        this.issuerIcon = issuerIcon;
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

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageHtml() {
        return messageHtml;
    }

    public void setMessageHtml(String messageHtml) {
        this.messageHtml = messageHtml;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public Long getSentAt() {
        return sentAt;
    }

    public void setSentAt(Long sentAt) {
        this.sentAt = sentAt;
    }

    public String getJsonSent() {
        return jsonSent;
    }

    public void setJsonSent(String jsonSent) {
        this.jsonSent = jsonSent;
    }

    public String getJsonSentSignature() {
        return jsonSentSignature;
    }

    public void setJsonSentSignature(String jsonSentSignature) {
        this.jsonSentSignature = jsonSentSignature;
    }

    public Long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Long receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getJsonReceived() {
        return jsonReceived;
    }

    public void setJsonReceived(String jsonReceived) {
        this.jsonReceived = jsonReceived;
    }

    public String getJsonReceivedSignature() {
        return jsonReceivedSignature;
    }

    public void setJsonReceivedSignature(String jsonReceivedSignature) {
        this.jsonReceivedSignature = jsonReceivedSignature;
    }

    public Long getReadAt() {
        return readAt;
    }

    public void setReadAt(Long readAt) {
        this.readAt = readAt;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "id='" + id + '\'' +
                ", lotId='" + lotId + '\'' +
                ", tag='" + tag + '\'' +
                ", issuerDoc='" + issuerDoc + '\'' +
                ", issuerDocType='" + issuerDocType + '\'' +
                ", issuerName='" + issuerName + '\'' +
                ", issuerNameShort='" + issuerNameShort + '\'' +
                ", issuerIcon='" + issuerIcon + '\'' +
                ", receiverDoc='" + receiverDoc + '\'' +
                ", receiverDocType='" + receiverDocType + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", serviceId=" + serviceId +
                ", subject='" + subject + '\'' +
                ", messageHtml='" + messageHtml + '\'' +
                ", important=" + important +
                ", sentAt=" + sentAt +
                ", jsonSent='" + jsonSent + '\'' +
                ", jsonSentSignature='" + jsonSentSignature + '\'' +
                ", receivedAt=" + receivedAt +
                ", jsonReceived='" + jsonReceived + '\'' +
                ", jsonReceivedSignature='" + jsonReceivedSignature + '\'' +
                ", readAt=" + readAt +
                ", readCount=" + readCount +
                ", code='" + code + '\'' +
                ", errorReason='" + errorReason + '\'' +
                ", attachments=" + attachments +
                ", createdAt=" + createdAt +
                '}';
    }
}