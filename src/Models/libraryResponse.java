package Models;

public class libraryResponse extends GlobalResponse{
    private LibraryStatistics library;

    public libraryResponse(int status, String message ,LibraryStatistics library) {
        super(status, message);
        this.library=library;
    }

    @Override
    public String toString() {
        return "Response{" +
        "status='" + getStatus() + '\'' +
        ", message=" + getMessage() +'\''
        + library+ "}"
        ;
    }

    

}
