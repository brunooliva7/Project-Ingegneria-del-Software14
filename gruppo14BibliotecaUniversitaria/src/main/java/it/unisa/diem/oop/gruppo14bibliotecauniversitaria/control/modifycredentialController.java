
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
 * @class modifycredentialController
 * @brief Controller per la gestione della schermata di modifica delle credenziali del bibliotecario.
 *
 * Questa classe gestisce l'interazione dell'utente con la vista "modifyPage.fxml".
 * Permette di aggiornare username e password del bibliotecario previa verifica di un PIN di sicurezza.
 * Gestisce inoltre la validazione dei campi di input (bindings) e la navigazione verso la schermata di login.
 */
public class modifycredentialController {
    
    /** * @brief Istanza del modello Librarian.
     * Utilizzata per effettuare l'operazione di aggiornamento delle credenziali sul file.
     */
    private Librarian librarian = new Librarian();

    @FXML 
    private TextField pin; ///< Campo di testo per l'inserimento del PIN di sicurezza.
    
    @FXML 
    private TextField usernameModified; ///< Campo di testo per il nuovo username.
    
    @FXML 
    private TextField passwordModified; ///< Campo di testo per la nuova password.
    
    @FXML 
    private Button confirmModified; ///< Bottone per confermare la modifica.
    
    @FXML 
    private Label labelMessage; ///< Etichetta per visualizzare messaggi di successo o errore.
    
    @FXML 
    private Button backButton; ///< Bottone per tornare alla schermata di login.

    /**
     * @brief Metodo di inizializzazione del controller.
     * * Viene chiamato automaticamente dopo il caricamento del file FXML.
     * Configura i binding delle proprietà per gestire l'abilitazione/disabilitazione
     * dei campi e del pulsante di conferma in base al contenuto degli input.
     * * @post Il campo password è disabilitato se il PIN è vuoto.
     * @post Il tasto di conferma è disabilitato se uno qualsiasi dei campi è vuoto.
     */

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
     * @brief Gestisce l'evento di pressione del tasto "Conferma".
     * * Verifica che il PIN inserito corrisponda al codice di sicurezza ("0061270").
     * Se corretto, invoca il metodo del modello per aggiornare le credenziali e mostra un messaggio di successo.
     * Altrimenti, mostra un messaggio di errore.
     * * @pre I campi di input non devono essere vuoti (garantito dal binding in initialize).
     * @post Se PIN corretto: credenziali aggiornate nel file e messaggio di successo visualizzato.
     * @post Se PIN errato: messaggio di errore visualizzato.
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
     * @brief Gestisce l'evento di pressione del tasto "Indietro".
     * * Richiama il metodo statico della View per tornare alla schermata di Login,
     * assicurando che la finestra venga reimpostata correttamente (es. maximized).
     * * @throws IOException Se il caricamento della vista Login fallisce.
     */
    @FXML
    private void handleBack() throws IOException {
        View.Login();
    }
}