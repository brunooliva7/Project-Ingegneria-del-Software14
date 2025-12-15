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
 * @class LoginController
 * @brief Controller per la gestione della schermata di Login.
 *
 * Questa classe gestisce l'interazione tra l'interfaccia grafica di login e il modello di autenticazione (Librarian).
 * Si occupa di:
 * - Gestire i binding per abilitare/disabilitare i campi in sequenza.
 * - Verificare le credenziali inserite.
 * - Gestire la navigazione verso la Homepage o la schermata di modifica password.
 */
public class LoginController {
    
    /** @brief Istanza del modello Librarian per la verifica delle credenziali. */
    private Librarian librarian = new Librarian();
    
    @FXML
    private TextField usernameField; ///< Campo di testo per l'inserimento dell'username.
    
    @FXML
    private TextField passwordField; ///< Campo di testo per l'inserimento della password.
    
    @FXML
    private Button loginButton; ///< Bottone per avviare la procedura di login.
    
    @FXML
    private Label labelErrore; ///< Etichetta per visualizzare messaggi di errore (es. "Credenziali Errate").
    
    
    /**
     * @brief Metodo di inizializzazione del controller.
     * * Viene chiamato automaticamente da JavaFX dopo il caricamento del file FXML.
     * Configura i binding delle proprietà:
     * - Il campo password è disabilitato finché l'username è vuoto.
     * - Il bottone di login è disabilitato finché uno dei due campi è vuoto.
     */
    @FXML
    private void initialize(){
        passwordField.disableProperty().bind(usernameField.textProperty().isEmpty());
        
        loginButton.disableProperty().bind(usernameField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty()));
    }
    
    /**
     * @brief Gestisce l'evento di login.
     * * Recupera i dati inseriti, interroga il modello `Librarian` per la verifica.
     * Se le credenziali sono corrette, naviga verso la Homepage.
     * Altrimenti, mostra un messaggio di errore.
     * * @throws IOException Se il caricamento della vista Homepage fallisce.
     * @post Se credenziali valide: cambio scena verso Homepage.
     * @post Se credenziali errate: labelErrore aggiornata.
     */
    @FXML
    private void handleLogin() throws IOException{
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(librarian.checkCredentials(username, password)){
            View.Homepage();
        }
        else {
            labelErrore.setText("Credenziali Errate");  
        }
    }
    
    /**
     * @brief Gestisce la navigazione verso la schermata di modifica credenziali.
     * * Invocato quando l'utente clicca sul link/bottone per modificare la password.
     * * @throws IOException Se il caricamento della vista Modify fallisce.
     */
    @FXML
    private void modifyCredentials() throws IOException{
        View.Modify();
    }
}