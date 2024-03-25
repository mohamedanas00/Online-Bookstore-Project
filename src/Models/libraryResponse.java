package Models;

public class libraryResponse extends GlobalResponse{
    private LibraryStatistics library;

    public libraryResponse(int status, String message ,LibraryStatistics library) {
        super(status, message);
        this.library=library;
    }

    @Override
    public String toString() {
        StringBuilder responseString = new StringBuilder();
        responseString.append("------------------------------\n");

        responseString.append("Response {\n");
        responseString.append("  Status: '").append(getStatus()).append("',\n");
        responseString.append("  Message: ").append(getMessage()).append(",\n");
        responseString.append("  Library: ").append(library).append("\n");
        responseString.append("}");
        responseString.append("\n------------------------------");
        return responseString.toString();
    }
    

    

}
