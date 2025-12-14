/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author bruno
 */
public class deleteUserController {
    
     @FXML
    private TextField searchField;
    
     @FXML
    private Button searchButton;
     
     @FXML
     private Button deleteButton;
     
     @FXML
     private Label labelMessage; 
     
      @FXML
     private Button backButton;
     
     UserManagement userManagement;
     User risultato = null;
     
     @FXML
     public void initialize(){
         searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
         
         deleteButton.setDisable(true);
         
         this.userManagement = new UserManagement();
     }
     
     @FXML
     public void search(){
         this.userManagement = new UserManagement(); 
        
         String input = searchField.getText();
        
         User userSonda = new User(input);
        
        this.risultato = userManagement.search(userSonda);

        if (risultato != null) {
           labelMessage.setText("Utente Trovato: " + risultato.getSurname() + ":" + risultato.getNumberId());
           labelMessage.setStyle("-fx-text-fill: green;");
           deleteButton.setDisable(false);
        } else {
            labelMessage.setText("Utente non trovato");
            labelMessage.setStyle("-fx-text-fill: red;");
        }
    }
     
     @FXML
     public void delete(){
         
         if(userManagement.remove(risultato)){
             labelMessage.setText("Utente eliminato Correttamente");
             deleteButton.setDisable(true);
             risultato = null;
         }
         
         else labelMessage.setText("Utente non eliminato");
     }
     
     @FXML
    public void backPage() throws IOException{
        View.Homepage();
    } 
     
}
