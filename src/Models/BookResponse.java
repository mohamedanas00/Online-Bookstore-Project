package Models;

import java.util.List;

public class BookResponse extends GlobalResponse {

    private List<Book> books;

    public BookResponse(int status, String message, List<Book> books) {
        super(status, message);
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        StringBuilder responseString = new StringBuilder();
        responseString.append("BookResponse {\n");
        responseString.append("  Status: '").append(getStatus()).append("',\n");
        responseString.append("  Message: '").append(getMessage()).append("',\n");
        responseString.append("  Books: [\n");
        for (Book book : books) {
            responseString.append("    ").append(Book.bookDetailsToString(book)).append(",\n");
        }
        responseString.append("  ]\n");
        responseString.append("}");
        return responseString.toString();
    }
    

}
