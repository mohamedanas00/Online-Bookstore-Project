package Models;

public class LogInResponse extends GlobalResponse{

    private User user;
    
    public LogInResponse(int status, String message, User user) {
        super(status, message);
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "Response{" +
        "status='" + getStatus() + '\'' +
        ", message=" + getMessage() + '\n'+
        ", user[" + '\'' +
        "id:"+user.getId()+ '\'' +
        ", username:"+ user.getUsername()+ '\'' +
        ", role:"+user.getRole()+ '\'' +
        "]"+
        '}';
    }
}
