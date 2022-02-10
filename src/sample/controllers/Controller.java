package sample.controllers;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Account;
import sample.services.DataBaseHandler;
import sample.data.Store;
import sample.animations.Shake;
import sample.models.User;

public class Controller {
    public DataBaseHandler dbHandler = new DataBaseHandler();

    @FXML
    private ResourceBundle resources;
    @FXML
    private void register(){
        openNewScene("/sample/view/signUp.fxml");
    }
    @FXML
    private void authorize(){
        String loginText = login_field.getText().trim();
        String loginPassword = password_field.getText().trim();
        Account account = new Account(loginText, loginPassword);
        User user = dbHandler.getUser(account);


        if (user == null)
            animateLoginInputs();
        else
        {
            Store store = Store.getInstance();
            store.setUser(user);
            loginSignUpButton.getScene().getWindow().hide();
            openNewScene("/sample/view/app.fxml");

        }
    }
    @FXML
    private void validateAuth(){
        String loginText = login_field.getText().trim();
        String loginPassword = password_field.getText().trim();
        if (loginText.equals("") || loginPassword.equals(""))
            authSignInButton.setDisable(true);
        else  authSignInButton.setDisable(false);

    }




    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {
    }


    public  void openNewScene(String window){
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(window));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
    }

    public void animateLoginInputs(){
    Shake userLoginAnim = new Shake(login_field);
    Shake userPassAnim = new Shake(password_field);
    userLoginAnim.playAnimation();
    userPassAnim.playAnimation();
}

}
