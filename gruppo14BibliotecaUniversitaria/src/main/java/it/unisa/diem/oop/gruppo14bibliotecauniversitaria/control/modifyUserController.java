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
     public void search() {
        System.out.println("--- INIZIO RICERCA ---");
    
    // 1. FORZA LA RILETTURA (Fondamentale se hai appena aggiunto un utente dall'altra schermata)
    this.user = new UserManagement(); 
    
    // 2. STAMPA DI DIAGNOSTICA: Cosa abbiamo caricato dal file?
    System.out.println("Utenti caricati in memoria: " + user.getList().size());
    for(User u : user.getList()) {
        System.out.println("   [MEMORIA] Matricola: '" + u.getNumberId() + "' - Cognome: '" + u.getSurname() + "'");
    }

    String input = searchField.getText();
    System.out.println("Input utente grezzo: '" + input + "'");

    // Crea l'oggetto sonda
    User userSonda = new User(input);
    
    // Vediamo come il costruttore ha interpretato l'input
    System.out.println("Interpretato come Matricola? " + userSonda.getNumberId());
    System.out.println("Interpretato come Cognome? " + userSonda.getSurname());

    // Esegui la ricerca
    User risultato = user.search(userSonda);

    if (risultato != null) {
        System.out.println("RISULTATO: TROVATO!");
        // ... codice per riempire i campi ...
        nameField.setText(risultato.getName());
        surnameField.setText(risultato.getSurname());
        numberidField.setText(risultato.getNumberId());
        emailField.setText(risultato.getEmail());
    } else {
        System.out.println("RISULTATO: NON TROVATO.");
        labelMessage.setText("Utente non trovato.");
    }
    System.out.println("--- FINE RICERCA ---");
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
