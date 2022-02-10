package sample.models;

import java.util.Date;

public class Review {
    private int id;
    private int clientId;
    private int bookId;
    private Date date;
    private String text;
    private int note;

    public Review(int clientId, int bookId, Date date, String text, int note) {
        this.clientId = clientId;
        this.bookId = bookId;
        this.date = date;
        this.text = text;
        this.note = note;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
