package model.user;

//import model.transaction.Borrowed;

public class Student extends Account {
    public Student() {

    }

    public Student(String fullname, String username, String password, String email, String phonenumber, String user_identification_id){
        super(fullname, username, password, email, phonenumber, user_identification_id);
    }


    public void studentViewBook() {
        viewBook();
    }

    public void studentViewOneBook(int bookId) {
        viewOneBook(bookId);
    }

    // tim kiem sach theo ten
    public void studentSearchByTitle(String title) {
        searchBookByTitle(title);
    }

    //tim sach theo the loai
    public void studentSearchByGenre(String category) {
        searchBookByTitle(category);
    }

    //tim sach theo ten
    public void studentSearchByPublisher(String publisher) {
        searchBookByPublisher(publisher);
    }


    public void studentRegister(String username, String password, String identificationId, String fullname, String email, String phonenumber) {
        register(username, password, identificationId, fullname, email, phonenumber);
    }

    public void studentLogin(String username, String password) {
        login(username, password);
    }

    public void studentLogout() {
        logout();
    }

    public void studentChangePassword(String username, String newPassword) {
        changePassword(username, newPassword);
    }

    //CAC THAO TAC MUON, TRA SACH
    // nop don muon sach
    // nop don tra sach
    // xem don muon sach
    // xem don tra sach

    public void studentBorrowBook(String username, int book_id, String borrowed_date, String returned_date) {
//        Borrowed borrowed = new Borrowed();
//        borrowed.borrowBook(username, book_id, borrowed_date, returned_date);
    }

}
