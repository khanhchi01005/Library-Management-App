package model.transaction;

public class Transaction {
    private int id;
    private String userName;
    private int bookId;
    private String bookTitle;
    private String borrowedDate;
    private String returnedDate;
    private String state;

    // Constructor
    public Transaction(int id, String userName, int bookId, String bookTitle, String borrowedDate, String returnedDate, String state) {
        this.id = id;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.state = state;
    }

    public Transaction(int id, String userName, int bookId, String bookTitle, String borrowedDate, String returnedDate) {
        this.id = id;
        this.userName = userName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public Transaction(int id,  int bookId, String bookTitle, String borrowedDate, String returnedDate) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public Transaction(int id,  int bookId, String bookTitle, String borrowedDate, String returnedDate, String state) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.state = state;
    }

    // Default constructor
    public Transaction() {
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void viewTransaction() {
        System.out.println("Transaction ID: " + id);
        System.out.println("User Name: " + userName);
        System.out.println("Book ID: " + bookId);
        System.out.println("Book Title: " + bookTitle);
        System.out.println("Borrowed Date: " + borrowedDate);
        System.out.println("Returned Date: " + returnedDate);
        System.out.println("State: " + state);
    }
}