//package model.transaction;
//
//import services.transaction.TransactionService;
//
//public class Borrowed extends Transaction {
//    public Borrowed() {
//
//    }
//    public Borrowed(int id, String userName, int bookId, String bookTitle, String borrowedDate, String returnedDate, String state) {
//        super(id,a userName, bookId, bookTitle, borrowedDate, returnedDate, state);
//    }
//
//    public void borrowBook(String username, int book_id, String borrowed_date, String returned_date) {
//        TransactionService transactionService = new TransactionService();
//        transactionService.borrowBook(username, book_id, borrowed_date, returned_date);
//    }
//}