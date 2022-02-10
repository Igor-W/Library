package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.models.Account;
import sample.services.DataBaseHandler;
import sample.models.User;

public class SignUpController {
    DataBaseHandler dbHandler = new DataBaseHandler();

    @FXML
    private ResourceBundle resources;
    @FXML
    private void signUp(){
        signUpNewUser();
        createNewAccount();
    }
    @FXML
    private URL location;

    @FXML
    private Button signUpButton;


    @FXML
    private TextField signUp_email;

    @FXML
    private TextField signUp_name;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private TextField signUp_surname;

    @FXML
    void initialize() {




        }


public  void createNewAccount(){
    String email = signUp_email.getText();
    String password = signUp_password.getText();

    Account account = new Account(email, password);
    dbHandler.createAccount(account);

}

    public  void signUpNewUser() {

            String name = signUp_name.getText();
            String surname = signUp_surname.getText();
            String email = signUp_email.getText();
            User user = new User(name, surname, email);
            dbHandler.signUpUser(user);

    }


    }
