package Models;

public class Book {
    private int Id;
    private String title;
    private String author;
    private String genre;
    private double price;
    private double ratingAvg;
    private int quantity;

    public Book(String title, String author, String genre, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantity = quantity;
        this.ratingAvg = 0.00;
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

    public void setId(int id) {
        Id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
