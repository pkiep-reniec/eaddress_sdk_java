package pe.gob.reniec.eaddress.sdk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

/**
 * @author Alexander Llacho
 */
public class Message {

    @JsonProperty("doc")
    private String doc;
    @JsonProperty("oc_type")
    private String docType;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("message")
    private String message;
    @JsonProperty("rep")
    private String rep;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("callback")
    private String callback;
    @JsonProperty("attachments")
    private String attachments;
    @JsonProperty("metadata")
    private String metadata;

    public Message() {
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Message{" +
                "doc='" + doc + '\'' +
                ", docType='" + docType + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", rep='" + rep + '\'' +
                ", tag='" + tag + '\'' +
                ", callback='" + callback + '\'' +
                ", attachments='" + attachments + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }

}