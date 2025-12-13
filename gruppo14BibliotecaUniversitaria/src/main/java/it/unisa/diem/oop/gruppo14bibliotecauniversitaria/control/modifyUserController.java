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
     private User risultato = null;
     
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
     public void search() {
       
    this.user = new UserManagement(); 
    String input = searchField.getText();
    User userSonda = new User(input);
    this.risultato = user.search(userSonda);

    if (risultato != null) {
       
        nameField.setText(risultato.getName());
        surnameField.setText(risultato.getSurname());
        numberidField.setText(risultato.getNumberId());
        emailField.setText(risultato.getEmail());
        
        
        
         nameField.setDisable(false);
        surnameField.setDisable(false);
         emailField.setDisable(false);
         numberidField.setDisable(false);
         confirmButton.setDisable(false);
    } else {
        labelMessage.setText("Utente non trovato.");
        labelMessage.setStyle("-fx-text-fill: red;");
    }
}

     
     @FXML 
     public void confirm(){
          User newUser = new User(nameField.getText(),surnameField.getText(),numberidField.getText(),emailField.getText());
            
            if(user.update(risultato, newUser)){
                labelMessage.setText("Modifica Riuscita");
                labelMessage.setStyle("-fx-text-fill: green;");
            }
            
            else{ labelMessage.setText("Modifica non riuscita");
            labelMessage.setStyle("-fx-text-fill: red;");
            }
     }
     
      @FXML
    public void backPage() throws IOException{
        View.Homepage();
    } 
}
