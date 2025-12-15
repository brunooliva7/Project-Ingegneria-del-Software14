
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth.Librarian;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controller per la schermata di modifica credenziali del bibliotecario.
 */
public class modifycredentialController {

    private Librarian librarian = new Librarian();

    @FXML private TextField pin;
    @FXML private TextField usernameModified;
    @FXML private TextField passwordModified;
    @FXML private Button confirmModified;
    @FXML private Label labelMessage;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        // Disabilita password finché pin è vuoto o username disabilitato
        passwordModified.disableProperty().bind(pin.textProperty().isEmpty().or(usernameModified.disableProperty()));

        // Disabilita conferma finché uno dei campi è vuoto
        confirmModified.disableProperty().bind(
            usernameModified.textProperty().isEmpty()
                .or(passwordModified.textProperty().isEmpty())
                .or(pin.disableProperty())
        );
    }

    /**
     * Gestisce la modifica delle credenziali.
     */
    @FXML
    private void handleModify() {
        String pinString = pin.getText();
        String username = usernameModified.getText();
        String password = passwordModified.getText();

        if ("0061270".equals(pinString)) {
            librarian.modifyCredentials(username, password);
            labelMessage.setText("Credenziali Modificate!");
        } else {
            labelMessage.setText("Pin non corretto");
        }
    }

    /**
     * Torna alla schermata di login.
     */
    @FXML
    private void handleBack() throws IOException {
        View.Login();
    }
}