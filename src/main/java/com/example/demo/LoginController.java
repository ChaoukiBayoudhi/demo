package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;


public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    public Button cancelButton;
    //    private Label Login;
//    @FXML
//    private Label Password;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    private boolean blocked = false;
    @FXML
    protected void onLoginButtonClick(ActionEvent actionEvent)  {

        Window owner = loginButton.getScene().getWindow();
        if(login.getText() .trim().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a login");
            blocked=true;
        }
       if(password.getText() .trim().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            blocked=true;
        }


    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    @FXML
    protected void onCancelButtonClick(ActionEvent actionEvent) {
        Platform.exit();
    }
//    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//        @Override
//        public void handle(WindowEvent t) {
//            Platform.exit();
//            System.exit(0);
//        }
//    });
}
