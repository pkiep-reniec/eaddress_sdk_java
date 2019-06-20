package pe.gob.reniec.eaddress.sdk.common;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public enum Acuse {
    SENT("sent"),
    RECEIVED("received"),
    READ("read");

    private final String alias;

    Acuse(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
