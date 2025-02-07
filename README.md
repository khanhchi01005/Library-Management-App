# Library Management System

## 📚 Overview
The **Library Management System** comprehensive software solution designed to facilitate the organization and administration of library resources, including books, users, borrowing, and returns. Built with MySQL database support, it enhances operational efficiency and improves the user experience.

---

## ✨ Features
### Admins:

- **Book Management**:  Add, modify, remove, and search for books.
- **User Management**: Manage library members and their access.
- **Loan and Return**: Track book checkouts and due dates.
- **Documents Search**: Locate and register new books using title, author, genre, ISBN, or APIs.

### Users:

- **Borrow Books**: Request and return books with ease.
- **Rating**: Provide stars for books.
- **View history**: View the history of borrowed books and the status of books being borrowed.

### Application Supports:
- APIs search.
- Multithreading.
- Read document by using DeepGram .
- JUnit test.

---

## 💻 Installation

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/khanhchi01005/Library-Management-App.git
   cd library-management-system
   ```

2. Configure the database in the `ConnectJDBC.java`.
    ``` bash
   DB_URL = "jdbc:mysql://your_database";
   DB_USERNAME = "your_username";
   DB_PASSWORD = "your_password";
   ```
3. Build the application.
4. Run application.
   ``` bash
    java -jar target/Library_management.jar
   ```
---

## 🛠️ Technologies Used
- **Frontend**: CSS, JavaFx
- **Backend**: Java
- **Database**: MySQL

---

## 🚀 Usage
### Admins:
1. **Dashboard**: See the management summary chart for documents and users.
2. **Profile**: Monitor and edit user profiles.
3. **Users**: Add, edit, delete and manage user accounts.
4. **Documents**: Add, edit, delete and manage documents.
5. **Search docs**: Search and add new documents to the library.

### Users:
1. **Dashboard**: Follow the summary of the borrowed documents.
2. **Profile**: Monitor and edit user profiles.
3. **Documents**: Search and borrow documents.
4. **History**: View book borrowing history and borrowed book status.

---

## 📝 Creators

### Hoang Khanh Chi: 
- **Role**: Backend developer, Database designer.
- **Contact**:
  - Email: khanhchi01005@gmail.com
  - Facebook: https://www.facebook.com/khanh.chi.337840/
### Nguyen Phuoc Nguong Long:
- **Role**: Project analyst, Frontend developer, JUnit Developer.
- **Contact**:
    - Email: npnlong@gmail.com
    - Facebook: https://www.facebook.com/LongNpn
---
