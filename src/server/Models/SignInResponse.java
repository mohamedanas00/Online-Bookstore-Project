package server.Models;

public class SignInResponse extends GlobalResponse{
   private String role;

    public SignInResponse(int status, String message, String role) {
        super(status, message);
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    

}
