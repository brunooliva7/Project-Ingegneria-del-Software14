/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import javafx.scene.control.Label;

/**
 *
 * @author bruno
 */
public class modifyUserController {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField surnameField;
    
     @FXML
    private TextField emailField;
     
     @FXML
    private TextField numberidField;
     
     @FXML
    private TextField searchField;
     
     @FXML
    private Button searchButton;
     
     @FXML
    private Button confirmButton;
     
     @FXML
     private Button prestititenteButton;
     
     @FXML
     private Label labelMessage;
     
     UserManagement user = new UserManagement();
     private User userached = null;
     
     @FXML
     public void initialize(){
         nameField.setDisable(true);
        surnameField.setDisable(true);
         emailField.setDisable(true);
         numberidField.setDisable(true);
         confirmButton.setDisable(true);
         
         searchButton.disableProperty().bind(searchField.textProperty().isEmpty());       
     }
     
     @FXML
     public void search(){
         String utente = searchField.getText();
         User u = new User(utente);
         
         userached = user.search(u);
         
         if(userached != null){
          
             nameField.setText(userached.getName());
            surnameField.setText(userached.getSurname());
            emailField.setText(userached.getEmail());
            numberidField.setText(userached.getNumberId());
    
         }
         
         else labelMessage.setText("Ricerca non riuscita");
     }
     
     @FXML 
     public void confirm(){
          User newUser = new User(nameField.getText(),surnameField.getText(),numberidField.getText(),emailField.getText());
            
            if(user.update(userached, newUser)){
                labelMessage.setText("Modifica Riuscita");
            }
            
            else labelMessage.setText("Modifica non riuscita");
     }
     
      @FXML
    public void backPage() throws IOException{
        View.Homepage();
    } 
}
