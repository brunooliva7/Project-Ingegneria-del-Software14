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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bruno
 */
public class modifycredentialController {
    
    Librarian librarian = new Librarian();
    
    @FXML
    private TextField pin;
    
    @FXML
    private TextField usernameModified;
    
    @FXML
    private TextField passwordModified;
    
    @FXML
    private Button confirmModified;
    
    @FXML 
    private Label labelMessage;
    
    @FXML
    private Button backButton;
    
    @FXML 
    public void initialize(){
        passwordModified.disableProperty().bind(pin.textProperty().isEmpty().or(usernameModified.disableProperty()));
        
        confirmModified.disableProperty().bind(usernameModified.textProperty().isEmpty().or(passwordModified.textProperty().isEmpty()).or(pin.disableProperty()));
    }
    
    @FXML
    private void handleModify(){
        String pinString = pin.getText();
        
        String username = usernameModified.getText();
        
        String password = passwordModified.getText();
        
        if(pinString.compareTo("0061270") == 0){
        librarian.modifyCredentials(username, password);
        labelMessage.setText("Credenziali Modificate!");
            
        }
        
        else {
          labelMessage.setText("Pin non corretto");
        }
        
    } 
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // utile per debug
            labelMessage.setText("Errore nel caricamento della login page");
        }
}   
}

