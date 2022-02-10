package sample.controllers;

import java.io.IOException;

import java.sql.Date;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.services.DataBaseHandler;
import sample.data.Store;
import sample.models.*;

public class AppController {
    DataBaseHandler dbHandler = new DataBaseHandler();
    Book selectedBook;
    private ObservableList<Integer> notes = FXCollections.observableList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    Store store = Store.getInstance();
    ObservableList<Book> allBooks;
    ObservableList<Book> myBooks;
    private String searchBy = "Name";
    private User user = store.getUser();

    @FXML
    private Button addBookBtn;
    @FXML
    private ImageView booKImage;
    @FXML
    private AnchorPane addBookSection;
    @FXML
    private AnchorPane addReviewSection;
    @FXML
    private Button addReviewBtn;
    @FXML
    private AnchorPane allBooksSection;
    @FXML
    private AnchorPane myBooksSection;
    @FXML
    private TableView<Book> allBooksTable;
    @FXML
    private TableView<Book> myBooksTable;
    @FXML
    private TableColumn<Book, String> authCol;
    @FXML
    private TableColumn<Book, String> myBooksAuthorCol;
    @FXML
    private ChoiceBox<Author> bookAuthorChoiceBox;
    @FXML
    private TableColumn<BookHistory, String> bookCol;
    @FXML
    private ChoiceBox<Domain> bookDomainChoiceBox;
    @FXML
    private AnchorPane bookHistorySection;
    @FXML
    private TableView<BookHistory> bookHistoryTable;
    @FXML
    private ChoiceBox<Language> bookLanguagesChoiceBox;
    @FXML
    private TextField bookNameInput;
    @FXML
    private TextField bookUrlInput;
    @FXML
    private Button borrowBookBtn;
    @FXML
    private AnchorPane borrowBookSection;
    @FXML
    private TableColumn<BookHistory, java.util.Date> borrowDateCol;
    @FXML
    private TableColumn<BookHistory, String> clientCol;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TableColumn<Book, String> domainCol;
    @FXML
    private TableColumn<Book, String> myBooksDomainCol;
    @FXML
    private TableColumn<Book, String> langCol;
    @FXML
    private TableColumn<Book, String> myBooksLanguageCol;
    @FXML
    private Button logOutBtn;
    @FXML
    private AnchorPane mainSection;
    @FXML
    private Button myBooksBtn;
    @FXML
    private TableColumn<Book, String> nameCol;
    @FXML
    private TableColumn<Book, String> myBooksNameCol;
    @FXML
    private Button returnBookBtn;
    @FXML
    private Label returnBookNameLabel;
    @FXML
    private AnchorPane returnBookSection;
    @FXML
    private Text reviewBookNameText;
    @FXML
    private ChoiceBox<Integer> reviewNoteChoiceBox;
    @FXML
    private TextArea reviewTextArea;
    @FXML
    private TextField searchInput;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ImageView userIcon;
    @FXML
    private Label bookHistoryNameLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userSurnameLabel;
    @FXML
    private Button viewHistoryBtn;
    @FXML
    private Label borrowBookNameText;
    @FXML
    private Label borrowBookAuthorText;
    @FXML
    private Text borrowBookLanguageText;
    @FXML
    private Text borrowBookDomainText;
    @FXML
    private TextArea borrowBookDescriptionArea;
    @FXML
    private ToggleGroup bookFieldRadioGroup;

    @FXML
    private void addBook() {
        toggleSections(addBookSection);
    }

    @FXML
    private void addReview() {
        reviewBookNameText.setText(selectedBook.getName());
        toggleSections(addReviewSection);
    }

    @FXML
    private void borrowBook() {
        borrowBookNameText.setText(selectedBook.getName());
        borrowBookAuthorText.setText(selectedBook.getAuthor());
        borrowBookLanguageText.setText(selectedBook.getLanguage());
        borrowBookDomainText.setText(selectedBook.getDomain());
        borrowBookDescriptionArea.setText(selectedBook.getDescription());
        borrowBookBtn.setDisable(true);
        String defaultImageUrl = "https://cdn.bookauthority.org/dist/images/book-cover-not-available.6b5a104fa66be4eec4fd16aebd34fe04.png";
        String url = selectedBook.getUrl() == null ? defaultImageUrl : selectedBook.getUrl();
        Image image = new Image(url);
        booKImage.setImage(image);
        toggleSections(borrowBookSection);
    }

    @FXML
    private void openMyBooks() {
        disableButtons(true);
        toggleSections(myBooksSection);
        fillMyBooksTable();
    }

    @FXML
    private void openReturnBookSection() {
        toggleSections(returnBookSection);
        returnBookNameLabel.setText(selectedBook.getName());
    }

    @FXML
    private void returnBook() {
        dbHandler.returnBook(user.getId(), selectedBook.getId());
        fillAllBooksTable();
        exit();
    }

    @FXML
    private void viewHistory(ActionEvent event) {
        toggleSections(bookHistorySection);
        bookHistoryNameLabel.setText(selectedBook.getName());
        fillBookHistoryTable();
    }

    @FXML
    private void saveBook() {
        Author author = bookAuthorChoiceBox.getValue();
        Language language = bookLanguagesChoiceBox.getValue();
        Domain domain = bookDomainChoiceBox.getValue();
        String name = bookNameInput.getText();
        String description = descriptionArea.getText();
        String url = bookUrlInput.getText();
        Book book = new Book(name, description, author.getId(), domain.getId(), language.getId(), url);
        dbHandler.addBook(book);
        exit();
        resetAddBookFields();
        fillAllBooksTable();
        allBooksTable.refresh();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        logOutBtn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/sample/view/auth.fxml"));
        Scene scene = new Scene(parent);
        Stage authStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        authStage.setScene(scene);
        authStage.show();
    }
    @FXML
    private void exit() {
        toggleSections(allBooksSection);
        allBooksTable.getSelectionModel().clearSelection();
        disableButtons(true);
        returnBookBtn.setDisable(true);
        addReviewBtn.setDisable(true);
    }

    @FXML
    private void saveBorrowBook() {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        dbHandler.borrowBook(user.getId(), selectedBook.getId(), date);
        fillAllBooksTable();
        exit();
    }

    @FXML
    private void reviewSave() {
        int clientId = user.getId();
        int bookId = selectedBook.getId();
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        String text = reviewTextArea.getText();
        int note = reviewNoteChoiceBox.getValue();
        Review review = new Review(clientId, bookId, date, text, note);
        dbHandler.addReview(review);
        reviewTextArea.clear();
        reviewNoteChoiceBox.getSelectionModel().selectLast();
        exit();
    }

    @FXML
    void initialize() {
        prepareViewForDifferentRoles();
        fillAllBooksTable();
        fillLanguages();
        fillAuthors();
        fillDomains();
        fillNotes();
        onAllBooksListSelectionChanged();
        onMyBooksListSelectionChanged();
        onRadioGroupChanged();
        onAllBooksTableSearch();
        initializeUserInfo();
    }

    private void onAllBooksTableSearch() {
        searchInput.textProperty().addListener((observable, oldValue, newValue) ->
                allBooksTable.setItems(filterList(allBooks, newValue))
        );

    }

    private void fillBookHistoryTable() {
        ObservableList<BookHistory> bookHistory = FXCollections.observableArrayList(dbHandler.getBookHistory(selectedBook.getId()));
        for (int i = 0; i < bookHistory.size(); i++) {
            clientCol.setCellValueFactory(new PropertyValueFactory<>("clientFullName"));
            bookCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        }
        bookHistoryTable.setItems(bookHistory);
        bookHistoryTable.refresh();
    }

    private void fillAllBooksTable() {
        allBooks = FXCollections.observableArrayList(dbHandler.getAllBooks(user.isLibrarian()));
        for (int i = 0; i < allBooks.size(); i++) {
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            authCol.setCellValueFactory(new PropertyValueFactory<>("author"));
            langCol.setCellValueFactory(new PropertyValueFactory<>("language"));
            domainCol.setCellValueFactory(new PropertyValueFactory<>("domain"));
        }
        allBooksTable.setItems(allBooks);
        allBooksTable.refresh();
    }

    private void fillMyBooksTable() {
        myBooks = FXCollections.observableArrayList(dbHandler.getMyBooks(user.getId()));
        for (int i = 0; i < myBooks.size(); i++) {
            myBooksNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            myBooksAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
            myBooksLanguageCol.setCellValueFactory(new PropertyValueFactory<>("language"));
            myBooksDomainCol.setCellValueFactory(new PropertyValueFactory<>("domain"));
        }
        myBooksTable.setItems(myBooks);
        myBooksTable.refresh();
    }

    private void toggleSections(AnchorPane sectionToOpen) {
        ObservableList<Node> sections = mainSection.getChildren();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).isVisible()) {
                sections.get(i).setVisible(false);
            }
        }
        sectionToOpen.setVisible(true);
    }

    private void initializeUserInfo() {
        userNameLabel.setText(user.getName());
        userSurnameLabel.setText(user.getSurname());
        userEmailLabel.setText(user.getEmail());
    }

    private void onAllBooksListSelectionChanged() {
        allBooksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedBook = newSelection;
            if (selectedBook == null) disableButtons(true);
            else disableButtons(false);
        });

    }

    private void onMyBooksListSelectionChanged() {
        myBooksTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedBook = newSelection;
            if (selectedBook == null) {
                addReviewBtn.setDisable(true);
                returnBookBtn.setDisable(true);
            }
            addReviewBtn.setDisable(false);
            returnBookBtn.setDisable(false);
        });

    }

    private void onRadioGroupChanged() {
        bookFieldRadioGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
            RadioButton selectedRadioButton = (RadioButton) bookFieldRadioGroup.getSelectedToggle();
            searchBy = selectedRadioButton.getText();
        });

    }

    private void fillLanguages() {
        ObservableList<Language> languages = FXCollections.observableArrayList(dbHandler.getLanguages());
        bookLanguagesChoiceBox.setItems(languages);
        bookLanguagesChoiceBox.getSelectionModel().selectFirst();
    }

    private void fillDomains() {
        ObservableList<Domain> domains = FXCollections.observableArrayList(dbHandler.getDomains());
        bookDomainChoiceBox.setItems(domains);
        bookDomainChoiceBox.getSelectionModel().selectFirst();
    }

    private void fillAuthors() {
        ObservableList<Author> authors = FXCollections.observableArrayList(dbHandler.getAuthors());
        bookAuthorChoiceBox.setItems(authors);
        bookAuthorChoiceBox.getSelectionModel().selectFirst();
    }

    private void fillNotes() {
        reviewNoteChoiceBox.setItems(notes);
        reviewNoteChoiceBox.getSelectionModel().selectLast();
    }

    private void disableButtons(boolean value) {
        addReviewBtn.setDisable(value);
        borrowBookBtn.setDisable(value);
        viewHistoryBtn.setDisable(value);
    }

    private void prepareViewForDifferentRoles() {
        System.out.println(user.isLibrarian());
        if (user.isLibrarian()) {
            addReviewBtn.setVisible(false);
            borrowBookBtn.setVisible(false);
            myBooksBtn.setVisible(false);
            returnBookBtn.setVisible(false);
        } else {
            addBookBtn.setVisible(false);
            viewHistoryBtn.setVisible(false);
        }
    }

    private void resetAddBookFields() {
        bookAuthorChoiceBox.getSelectionModel().selectFirst();
        bookLanguagesChoiceBox.getSelectionModel().selectFirst();
        bookDomainChoiceBox.getSelectionModel().selectFirst();
        bookNameInput.clear();
        descriptionArea.clear();
        bookUrlInput.clear();
    }

    private ObservableList<Book> filterList(List<Book> books, String searchText) {
        List<Book> filteredList = new ArrayList<>();
        for (Book order : books) {
            if (searchFindsBooks(order, searchText)) filteredList.add(order);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindsBooks(Book book, String searchValue) {
        switch (searchBy) {
            case "Name":
                return book.getName().toLowerCase().contains(searchValue.toLowerCase());
            case "Author":
                return book.getAuthor().toLowerCase().contains(searchValue.toLowerCase());
            case "Language":
                return book.getLanguage().toLowerCase().contains(searchValue.toLowerCase());
            case "Domain":
                return book.getDomain().toLowerCase().contains(searchValue.toLowerCase());
        }
        return false;
    }

}
