package Models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book implements Serializable {

    private int Id;
    private String title;
    private String author;
    private String genre;
    private double price;
    private double ratingAvg;
    private int quantity;
    private int userId;
    private User user;

    public Book( String title, String author, String genre, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.ratingAvg = 0.00;
        this.quantity = quantity;
    }



    public Book(ResultSet resultSet) throws SQLException {
        Id = resultSet.getInt("id");
        title = resultSet.getString("title");
        author = resultSet.getString("author");
        genre = resultSet.getString("genre");
        price = resultSet.getDouble("price");
        ratingAvg = resultSet.getDouble("ratingAvg");
        quantity = resultSet.getInt("quantity");
        userId = resultSet.getInt("user_id");
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUserId() {
        return userId;
    }

    public User getUser(){
        return user;
    }
    
    public static String bookDetailsToString(Book book) {
        return "Book{id=" + book.getId() +
                ", title='" + book.getTitle() + '\'' +
                ", author='" + book.getAuthor() + '\'' +
                ", genre='" + book.getGenre() + '\'' +
                ", price=" + book.getPrice() + '\'' +
                ", ratingAvg=" + book.getRatingAvg() +'\'' +
                ", quantity=" + book.getQuantity() +'\n' +
                "," + book.getUser() +
                "}";
    }




}
