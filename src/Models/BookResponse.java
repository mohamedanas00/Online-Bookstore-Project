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
        responseString.append("BookResponse{\n");
        responseString.append("status='").append(getStatus()).append("',\n");
        responseString.append("message='").append(getMessage()).append("',\n");
        responseString.append("books=[\n");
        for (Book book : books) {
            responseString.append(bookDetailsToString(book)).append(",\n");
        }
        responseString.append("]\n");
        responseString.append("}");
        return responseString.toString();
    }

    private String bookDetailsToString(Book book) {
        return "Book{id=" + book.getId() +
                ", title='" + book.getTitle() + '\'' +
                ", author='" + book.getAuthor() + '\'' +
                ", genre='" + book.getGenre() + '\'' +
                ", price=" + book.getPrice() +
                ", ratingAvg=" + book.getRatingAvg() +
                ", quantity=" + book.getQuantity() +
                "}";
    }
}
