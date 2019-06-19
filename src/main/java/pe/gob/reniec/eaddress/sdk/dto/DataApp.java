package pe.gob.reniec.eaddress.sdk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Alexander Llacho
 */
public class DataApp {

    @JsonProperty("nombre")
    private String name;
    @JsonProperty("identificador")
    private String clientId;

    public DataApp() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "DataApp{" +
                "name='" + name + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
