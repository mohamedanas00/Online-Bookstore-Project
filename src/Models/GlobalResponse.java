package Models;

import java.io.Serializable;

public class GlobalResponse implements Serializable {
    private int status;
    private String message;

    public GlobalResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response{" +
        "status='" + status + '\'' +
        ", message=" + message +
        '}';
    }

}
