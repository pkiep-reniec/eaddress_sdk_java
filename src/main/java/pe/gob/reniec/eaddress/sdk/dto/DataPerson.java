package pe.gob.reniec.eaddress.sdk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexander Llacho
 */
public class DataPerson {

    @JsonProperty("tipo_doc")
    private String docType;
    @JsonProperty("doc")
    private String doc;
    @JsonProperty("nombre")
    private String name;

    public DataPerson() {
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

    @Override
    public String toString() {
        return "DataPerson{" +
                "docType='" + docType + '\'' +
                ", doc='" + doc + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
