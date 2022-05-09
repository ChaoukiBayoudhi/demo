package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label labDisplay;


    public void transferMessage(String message) {
        labDisplay.setText("You are connect with login = "+message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
