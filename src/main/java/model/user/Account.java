package model.user;

import model.book.Book;
import services.book.BookService;
import services.user.Credentials;

import java.util.List;


public class Account {
    private int id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private String identificationId;
    private boolean loggedIn = false;
    BookService service = new BookService();
    Credentials credentials = new Credentials();

    public Account() {

    }

    public Account(String fullname, String username, String password, String email, String phonenumber, String identificationId) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.identificationId = identificationId;
    }

    public Account(int id, String username, String password, String identificationId, String fullname, String phonenumber, String email) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.identificationId =identificationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }



    // xem tat ca sach hien co
    protected void viewBook (){
        List<Book> books = service.getAllBooks();
    }

    // xem 1 cuon sach bat ky
    protected void viewOneBook(int bookId){
        service.viewOneBook(bookId);
    }

    // tim kiem sach theo ten
    protected void searchBookByTitle(String title){
        service.searchBookByTitle(title);
    }

    // tim kiem sach theo the loai
    protected void searchBookByGenre(String category){
        service.searchBookByGenre(category);
    }

    // tim kiem sach theo NXB
    protected void searchBookByPublisher(String publisher) {
        service.searchBookByPublisher(publisher);
    }

    //them user vao database
    public void register(String username, String password, String identificationId, String fullname, String email, String phonenumber){
        credentials.register(username, password, identificationId, fullname, email, phonenumber);
    };


    // xu ly dang nhap
    public void login(String username, String password) {
        credentials.login(username, password);
        this.loggedIn = true;
    };

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logout() {
        System.out.println("Logout successfully");
    };

    public void changePassword(String username, String newPassword) {
        credentials.changePassword(username, newPassword);
    }

}