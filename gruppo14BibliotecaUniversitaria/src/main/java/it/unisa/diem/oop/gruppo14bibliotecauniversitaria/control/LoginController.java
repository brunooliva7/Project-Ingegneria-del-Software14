/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth.Librarian;
import javafx.scene.control.Label;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
/**
 *
 * @author bruno
 */
public class LoginController {
    
    private Librarian librarian = new Librarian();
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label labelErrore;
    
    
    @FXML
    private void initialize(){
        passwordField.disableProperty().bind(usernameField.textProperty().isEmpty());
        
        loginButton.disableProperty().bind(usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
         
    }
    
    @FXML
    private void handleLogin() throws IOException{
        
       
        String username = usernameField.getText();
    
        String password = passwordField.getText();
        
        if(librarian.checkCredentials(username, password)){
            
            View.Homepage();
        }
        
        else labelErrore.setText("Credenziali Errate");  
    }
    
    @FXML
    private void modifyCredentials() throws IOException{
        View.Modify();
    }
    
    
}
