package model.book;

import services.book.BookApi;

import java.util.ResourceBundle;

public class Book {
    public ResourceBundle rs;
    private int book_id;
    private String title;
    private String author;
    private String category;
    private int year;
    private int pages;
    private int available_amount;
    private String image;
    private String publisher;
    private String description;

    public Book() {

    }

    public Book(int book_id, String title, String author, String category, int year, int pages, int available_amount, String image, String description, String publisher) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.pages = pages;
        this.available_amount = available_amount;
        this.image = image;
        this.description = description;
        this.publisher = publisher;
    }


    public Book(String title, String author, String category, int year, int pages, int available_amount, String image, String description, String publisher) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.pages = pages;
        this.available_amount = available_amount;
        this.image = image;
        this.description = description;
        this.publisher = publisher;
    }

    public Book(String title, String author, String category, int pages, int available_amount, String image, String publisher) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.pages = pages;
        this.available_amount = available_amount;
        this.image = image;
        this.publisher = publisher;
    }

    public Book(int book_id, String title, String author, String category, int pages, int available_amount, String image, String publisher) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.pages = pages;
        this.available_amount = available_amount;
        this.image = image;
        this.publisher = publisher;
    }

    public void setId(int book_id) {
        this.book_id = book_id;
    }

    public int getId() {
        return book_id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    public void setAvailableAmount(int available_amount) {
        this.available_amount = available_amount;
    }

    public int getAvailableAmount() {
        return available_amount;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }

    // goi API de lay thong tin sach
    public static Book bookApi(String apiKey, String query, int available_amount) {
        BookApi bookApi = new BookApi();
        Book newBook = bookApi.fetchBookInfor(apiKey, query, available_amount);
        return newBook;
    }

}