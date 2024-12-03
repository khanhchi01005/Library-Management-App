//
//    // tra sach
//    public void returnBook(int borrowed_id, String returned_date) {
//        String query = "SELECT borrowed_date, user_id, book_id FROM borrowed WHERE borrowed_id = ?";
//        String insertQuery = "INSERT INTO returned (user_id, book_id, book_status, state, fine, reason, returned_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        String updateAmount = "UPDATE books SET available_amount = available_amount + 1 WHERE book_id = ?";
//
//        try Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setInt(1, borrowed_id);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                LocalDate borrowedDate = rs.getDate("borrowed_date").toLocalDate();
//                LocalDate dueDate = borrowedDate.plusDays(90);
//                LocalDate second = borrowedDate.minusDays(1);
//                LocalDate returnedDate = LocalDate.parse(returned_date);
//                if (returnedDate.isAfter(dueDate)) {
//                    System.out.println("You have put exceeded date. Due date is only 90 days from borrowed date.");
//                    return;
//                }
//                else if(returnedDate.isBefore(second)){
//                    System.out.println("You have put exceeded date. You can only return the book 90 days after borrowed date.");
//                    return;
//                }
//                int user_id = rs.getInt("user_id");
//                int book_id = rs.getInt("book_id");
//
//                try (PreparedStatement insertPstmt = connection.prepareStatement(insertQuery)) {
//                    insertPstmt.setInt(1, user_id);
//                    insertPstmt.setInt(2, book_id);
//                    insertPstmt.setString(3, "pending");
//                    insertPstmt.setString(4, "borrowed");
//                    insertPstmt.setDouble(5, fine);
//                    insertPstmt.setString(6, "null");
//                    insertPstmt.setString(7, returned_date);
//                    insertPstmt.executeUpdate();
//                    System.out.println("Successfully returned book with id: " + book_id);
//
//                }
//                try (PreparedStatement updatePstmt = conn.prepareStatement(updateAmount)) {
//                    updatePstmt.setInt(1, book_id);
//                    updatePstmt.executeUpdate();
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Failed to return book: " + e.getMessage());
//        }
//    }
//
//    //duyet don muon sach
//    public void reviewReturnApplication (int returned_id, String state, String book_status, double fine, String reason) {
//        String query = "UPDATE returned SET state =?, book_status = ?, state = ?, fine = ?, reason =?" + "WHERE returned_id = ?";
//        try (Connection conn = Database.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)){
//            pstmt.setString(1,state);
//            pstmt.setString(2,book_status);
//            pstmt.setInt(3,returned_id);
//            pstmt.setString(4,book_status);
//            pstmt.setDouble(5,fine);
//            pstmt.setString(6,reason);
//            pstmt.executeUpdate();
//            System.out.println("Successfully approved application with id: " + returned_id);
//        } catch (SQLException e){
//            System.err.println("Failed to approve application: " + e.getMessage());
//        }
//    }
//    // xem cac don muon sach
//    public void viewReturnedBook (String username) {
//        String query = "SELECT * FROM returned WHERE username = ?";
//        try (Connection conn = Database.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)){
//            pstmt.setString(1,username);
//            pstmt.executeQuery();
//        }catch (SQLException e) {
//            System.err.println("Failed to view returned book: " + e.getMessage());
//        }
//    }
//}