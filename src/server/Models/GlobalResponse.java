package server.Models;

public class GlobalResponse {
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

}
