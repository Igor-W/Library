package sample.models;

import java.util.Date;

public class BookHistory {
    private String clientFullName;
    private String bookName;
    private Date borrowDate;

    public BookHistory(String clientFullName, String bookName, Date borrowDate) {
        this.clientFullName = clientFullName;
        this.bookName = bookName;
        this.borrowDate = borrowDate;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
}
