/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author bruno
 */
public class searchUserController {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField surnameField;
    
     @FXML
    private TextField emailField;
     
     @FXML
    private TextField numberidField;
    
    @FXML
    private Label labelMessage;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Button searchButton;
    
    UserManagement user = new UserManagement();
     private User risultato = null;
    
    @FXML
    public void initialize(){
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        nameField.setDisable(true);
        surnameField.setDisable(true);
        emailField.setDisable(true);
        numberidField.setDisable(true);
    }
    
    @FXML
    public void search(){
        this.user = new UserManagement(); 
        String input = searchField.getText();
        User userSonda = new User(input);
        this.risultato = user.search(userSonda);

        if (risultato != null) {
            
        nameField.setText(risultato.getName());
        surnameField.setText(risultato.getSurname());
        numberidField.setText(risultato.getNumberId());
        emailField.setText(risultato.getEmail());

        } else {
            labelMessage.setText("Utente non trovato.");
            labelMessage.setStyle("-fx-text-fill: red;");
            }  
        }
    }
