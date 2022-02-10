package sample.services;
import sample.data.Configs;
import sample.models.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class DataBaseHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(User user) {
        String insertClient = "INSERT INTO CLIENT (name, surname, email) VALUES(?,?,?)";

        PreparedStatement prSt;
        try {
            prSt = getDbConnection().prepareStatement(insertClient);
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getSurname());
            prSt.setString(3, user.getEmail());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void createAccount(Account account) {
        String insertAccount = "INSERT INTO ACCOUNT(USER_EMAIL, USER_PASSWORD) VALUES(?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insertAccount);
            prSt.setString(1, account.getEmail());
            prSt.setString(2, account.getPassword());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(Account account) {
        ResultSet accountSet = null;
        ResultSet clientSet = null;
        User user = new User();
        String getAccount = "SELECT * FROM ACCOUNT WHERE user_email=? AND user_password=?";
        String getClient = "SELECT * FROM CLIENT WHERE email=?";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getAccount);
            prSt.setString(1, account.getEmail());
            prSt.setString(2, account.getPassword());
            accountSet = prSt.executeQuery();

            boolean isAccountExist = false;
            while (accountSet.next())
                isAccountExist = true;

            if (isAccountExist) {
                prSt = getDbConnection().prepareStatement(getClient);
                prSt.setString(1, account.getEmail());
                clientSet = prSt.executeQuery();
                while (clientSet.next()) {
                    user.setId(clientSet.getInt("id"));
                    user.setName(clientSet.getString("name"));
                    user.setEmail(clientSet.getString("email"));
                    user.setSurname(clientSet.getString("surname"));
                    user.setLibrarian(clientSet.getBoolean("isLibrarian"));
                }
            } else return null;


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addBook(Book book) {
        String insertBook = "INSERT INTO BOOK(name, description, authorId, domainId, languageId, url) VALUES(?,?,?,?,?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insertBook);
            prSt.setString(1, book.getName());
            prSt.setString(2, book.getDescription());
            prSt.setString(3, String.valueOf(book.getAuthorId()));
            prSt.setString(4, String.valueOf(book.getDomainId()));
            prSt.setString(5, String.valueOf(book.getLanguageId()));
            prSt.setString(6, book.getUrl());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addReview(Review review) {
        String insertReview = "INSERT INTO REVIEW( clientId, bookId, date, text, note) VALUES(?,?,?,?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insertReview);
            prSt.setString(1, String.valueOf(review.getClientId()));
            prSt.setString(2, String.valueOf(review.getBookId()));
            prSt.setString(3, String.valueOf(review.getDate()));
            prSt.setString(4, review.getText());
            prSt.setString(5, String.valueOf(review.getNote()));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Language> getLanguages() {
        ResultSet languagesSet;
        List<Language> languages = new ArrayList<>();

        String getLanguages = "SELECT * FROM library.language";

        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getLanguages);
            languagesSet = prSt.executeQuery();

            while (languagesSet.next()) {
                int id = languagesSet.getInt("id");
                String name = languagesSet.getString("name");

                Language language = new Language(id, name);
                languages.add(language);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return languages;
    }

    public List<Domain> getDomains() {
        ResultSet domainsSet;
        List<Domain> domains = new ArrayList<>();

        String getDomains = "SELECT * FROM library.domain";

        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getDomains);
            domainsSet = prSt.executeQuery();

            while (domainsSet.next()) {
                int id = domainsSet.getInt("id");
                String name = domainsSet.getString("name");

                Domain domain = new Domain(id, name);
                domains.add(domain);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return domains;
    }

    public List<Author> getAuthors() {
        ResultSet authorsSet;
        List<Author> authors = new ArrayList<>();

        String getAuthors = "SELECT * FROM library.author";

        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getAuthors);
            authorsSet = prSt.executeQuery();

            while (authorsSet.next()) {
                int id = authorsSet.getInt("id");
                String name = authorsSet.getString("name");
                String surname = authorsSet.getString("surname");

                Author author = new Author(id, name, surname);
                authors.add(author);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public List<Book> getAllBooks(boolean isForLibrarian) {
        String isBorrowed = isForLibrarian ? "": "WHERE book.isBorrowed=0";
        ResultSet booksSet;
        List<Book> books = new ArrayList<>();

        String getBooks = "SELECT book.id, book.name,book.description, author.name AS authorName, author.surname AS authorSurname, language.name AS language, domain.name AS domain, book.url\n" +
                "FROM book \n" +
                "JOIN author ON book.authorId=author.id\n" +
                "JOIN language ON book.languageId=language.id\n" +
                "JOIN domain ON book.domainId=domain.id " + isBorrowed;
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getBooks);
            booksSet = prSt.executeQuery();

            while (booksSet.next()) {
                int id = booksSet.getInt("id");
                String name = booksSet.getString("name");
                String description = booksSet.getString("description");
                String authorName = booksSet.getString("authorName");
                String authorSurname = booksSet.getString("authorSurname");
                String author = authorName + " " + authorSurname;
                String language = booksSet.getString("language");
                String domain = booksSet.getString("domain");
                String url = booksSet.getString("url");
                Book book = new Book(id, name, description, author, language, domain, url);
                books.add(book);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getMyBooks(int clientId) {
        ResultSet booksSet;
        List<Book> books = new ArrayList<>();

        String getBooks = "SELECT book.id, book.name,book.description, author.name AS authorName, author.surname AS authorSurname, language.name AS language, domain.name AS domain, book.url\n" +
                "FROM book \n" +
                "JOIN author ON book.authorId=author.id\n" +
                "JOIN language ON book.languageId=language.id\n" +
                "JOIN domain ON book.domainId=domain.id " +
                "JOIN book_account ON book.id=book_account.bookId " +
                "WHERE book_account.clientId=" + clientId;
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getBooks);
            booksSet = prSt.executeQuery();

            while (booksSet.next()) {
                int id = booksSet.getInt("id");
                String name = booksSet.getString("name");
                String description = booksSet.getString("description");
                String authorName = booksSet.getString("authorName");
                String authorSurname = booksSet.getString("authorSurname");
                String author = authorName + " " + authorSurname;
                String language = booksSet.getString("language");
                String domain = booksSet.getString("domain");
                String url = booksSet.getString("url");
                Book book = new Book(id, name, description, author, language, domain, url);
                books.add(book);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void borrowBook(int clientId, int bookId, Date date) {
        updateBookBorrow(1, bookId);
        createBookAccount(clientId, bookId, date);
        createBookHistory(clientId, bookId, date);
    }

    public void returnBook(int clientId, int bookId) {
        updateBookBorrow(0, bookId);
        deleteBookAccount(clientId, bookId);
    }

    private void updateBookBorrow(int isBorrowed, int bookId) {
        String updateBook = "UPDATE BOOK SET isBorrowed=" + isBorrowed + " WHERE id=" + bookId;
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(updateBook);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void deleteBookAccount(int clientId, int bookId) {
        String deleteBookAccount = "DELETE FROM BOOK_ACCOUNT WHERE clientId=" + clientId + " AND bookId=" + bookId;
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(deleteBookAccount);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createBookAccount(int clientId, int bookId, Date date) {
        String insertBookAccount = "INSERT INTO BOOK_ACCOUNT( clientId, bookId, date) VALUES(?,?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insertBookAccount);
            prSt.setString(1, String.valueOf(clientId));
            prSt.setString(2, String.valueOf(bookId));
            prSt.setString(3, String.valueOf(date));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createBookHistory(int clientId, int bookId, Date date) {
        String insertBookHistory = "INSERT INTO BOOK_HISTORY( clientId, bookId, date) VALUES(?,?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insertBookHistory);
            prSt.setString(1, String.valueOf(clientId));
            prSt.setString(2, String.valueOf(bookId));
            prSt.setString(3, String.valueOf(date));
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<BookHistory> getBookHistory(int bookId) {
        String getBookHistory = "SELECT  book.name as bookName, client.name as clientName, client.surname as clientSurname, book_history.date as borrowDate FROM book_history " +
                "JOIN book ON book.id=book_history.bookId " +
                "JOIN client ON book_history.clientId = client.id " +
                "WHERE book_history.bookId=" + bookId;
        ResultSet bookHistorySet;
        List<BookHistory> allBookHistory = new ArrayList<>();
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getBookHistory);
            bookHistorySet = prSt.executeQuery();

            while (bookHistorySet.next()) {
                String clientFullName = bookHistorySet.getString("clientName")+" "+
                                        bookHistorySet.getString("clientSurname");
                String bookName = bookHistorySet.getString("bookName");
                Date borrowDate = bookHistorySet.getDate("borrowDate");
                BookHistory bookHistory = new BookHistory(clientFullName, bookName, borrowDate);
                allBookHistory.add(bookHistory);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allBookHistory;
    }

    public List<String> getBookNamesBorrowedToMuch(int clientId, java.sql.Date criticalDate){

        String getToMuchBorrowedBookNames = "SELECT book.name FROM book " +
                "JOIN book_account ON book.id=book_account.bookId " +
                "JOIN client ON client.id=book_account.clientId " +
                "WHERE client.id=?" +
                " AND book_account.date < ?";
        System.out.println(getToMuchBorrowedBookNames);
        ResultSet bookNamesSet;
        List<String> bookNames = new ArrayList<>();
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(getToMuchBorrowedBookNames);
            prSt.setInt(1,clientId);
            prSt.setDate(2,criticalDate);
            bookNamesSet = prSt.executeQuery();
            while (bookNamesSet.next()) {
                String bookName = bookNamesSet.getString("name");
                bookNames.add(bookName);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookNames;
    }
}

