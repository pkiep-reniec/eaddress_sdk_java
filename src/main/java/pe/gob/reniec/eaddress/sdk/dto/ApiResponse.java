package pe.gob.reniec.eaddress.sdk.dto;

/**
 * @author Alexander LLacho
 */
public class ApiResponse {

    private Boolean success;
    private String message;
    private Integer code;

    public ApiResponse() {
        this.success = false;
    }

    public ApiResponse(String message) {
        this.success = false;
        this.code = 500;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }

}
