package pe.gob.reniec.eaddress.sdk.dto;

import java.util.List;

/**
 * @author Alexander Llacho
 */
public class ApiNotificationResponse {

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
    private String messageHtml;
    private Long sentAt;
    private String jsonSent;
    private Long receivedAt;
    private String jsonReceived;
    private Long readAt;
    private String jsonRead;
    private String code;
    private String errorReason;
    private List<Attachment> attachments;
    private Long createdAt;

    public ApiNotificationResponse() {
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

    public String getMessageHtml() {
        return messageHtml;
    }

    public void setMessageHtml(String messageHtml) {
        this.messageHtml = messageHtml;
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

    public Long getReadAt() {
        return readAt;
    }

    public void setReadAt(Long readAt) {
        this.readAt = readAt;
    }

    public String getJsonRead() {
        return jsonRead;
    }

    public void setJsonRead(String jsonRead) {
        this.jsonRead = jsonRead;
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
        return "ApiNotificationResponse{" +
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
                ", messageHtml='" + messageHtml + '\'' +
                ", sentAt=" + sentAt +
                ", jsonSent='" + jsonSent + '\'' +
                ", receivedAt=" + receivedAt +
                ", jsonReceived='" + jsonReceived + '\'' +
                ", readAt=" + readAt +
                ", jsonRead='" + jsonRead + '\'' +
                ", code='" + code + '\'' +
                ", errorReason='" + errorReason + '\'' +
                ", attachments=" + attachments +
                ", createdAt=" + createdAt +
                '}';
    }
}