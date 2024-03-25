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
        StringBuilder responseString = new StringBuilder();
        responseString.append("------------------------------\n");
        responseString.append("Response\n{");
        responseString.append("\n  Status: '").append(status).append("',\n");
        responseString.append("  Message: ").append(message).append("\n");
        responseString.append("}");
        responseString.append("\n------------------------------");
        return responseString.toString();
    }
    

}
