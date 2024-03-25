package Models;

public class LogInResponse extends GlobalResponse{

    private User user;
    
    public LogInResponse(int status, String message, User user) {
        super(status, message);
        this.user = user;
    }
    
    @Override
    public String toString() {
        StringBuilder responseString = new StringBuilder();
        responseString.append("------------------------------\n");
        responseString.append("Response\n{");
        responseString.append("\n  Status: '").append(getStatus()).append("',\n");
        responseString.append("  Message: ").append(getMessage()).append(",\n");
        responseString.append("  User: {\n");
        responseString.append("    ID: ").append(user.getId()).append(",\n");
        responseString.append("    Username: '").append(user.getUsername()).append("',\n");
        responseString.append("    Role: '").append(user.getRole()).append("'\n");
        responseString.append("  }\n");
        responseString.append("}");
        responseString.append("\n------------------------------");
        return responseString.toString();
    }
    

    public User getUser() {
        return user;
    }
}
