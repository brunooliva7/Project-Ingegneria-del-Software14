/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file addUserController.java
 * @brief Controller per la gestione dell'aggiunta di un nuovo utente.
 * 
 * La classe `addUserController` gestisce l'inserimento di un nuovo utente nel sistema.
 * Include il controllo della validità dell'email, il controllo della matricola duplicata 
 * e la gestione dell'interfaccia utente tramite i campi di input e i messaggi di errore.
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @class addUserController
 * @brief Gestisce l'interfaccia utente per l'aggiunta di un nuovo utente.
 * 
 * La classe `addUserController` fornisce la logica per aggiungere un nuovo utente
 * al sistema, inclusi i controlli per la validità dell'email e il controllo della matricola.
 */
public class addUserController {
    
    /**
     * Campo di testo per il nome dell'utente.
     */
    @FXML
    private TextField name;
    
    /**
     * Campo di testo per il cognome dell'utente.
     */
    @FXML
    private TextField surname;
    
    /**
     * Campo di testo per l'email dell'utente.
     */
    @FXML
    private TextField email;
    
    /**
     * Campo di testo per la matricola dell'utente.
     */
    @FXML
    private TextField numberID;
    
    /**
     * Pulsante per aggiungere un nuovo utente.
     */
    @FXML
    private Button addButton;
    
    /**
     * Etichetta per visualizzare i messaggi di errore o conferma.
     */
    @FXML 
    private Label labelErrore;
    
    /**
     * Pulsante per tornare alla pagina precedente.
     */
    @FXML
    private Button backButton;
    
    /**
     * Riferimento al modello dell'applicazione che gestisce i dati.
     */
    private Model model;
    
    /**
     * Imposta il modello dell'applicazione.
     * 
     * @param model Il modello dell'applicazione da impostare.
     */
    public void setModel(Model model) {
        this.model = model;
    }
    
    /**
     * Verifica la validità dell'email fornita.
     * 
     * Utilizza una espressione regolare per controllare se l'email segue il formato standard.
     * 
     * @param email L'email da verificare.
     * @return true se l'email è valida, false altrimenti.
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Metodo chiamato all'inizializzazione del controller.
     * Disabilita i campi di input e il pulsante "Aggiungi" se i campi obbligatori non sono compilati.
     */
    @FXML
    public void initialize() {
        numberID.disableProperty().bind(email.textProperty().isEmpty().or(surname.textProperty().isEmpty().or(name.textProperty().isEmpty())));
        surname.disableProperty().bind(name.textProperty().isEmpty());
        email.disableProperty().bind(name.textProperty().isEmpty().or(surname.textProperty().isEmpty()));   
        addButton.disableProperty().bind(numberID.textProperty().isEmpty());
    }
    
    /**
     * Metodo chiamato quando l'utente clicca sul pulsante "Aggiungi".
     * 
     * Aggiunge un nuovo utente al sistema, eseguendo la validazione dell'email
     * e controllando che la matricola non sia già esistente.
     * 
     * @post Se l'utente viene aggiunto con successo, i campi vengono resettati.
     */
    @FXML
    public void addUser() {
        String nome = name.getText();
        String cognome = surname.getText();
        String mail = email.getText();
        String matricola = numberID.getText();
        
        // Verifica che l'email sia valida
        if (!isValidEmail(mail)) {
            labelErrore.setText("Email non valida");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        }
        
        // Crea un nuovo oggetto User con i dati inseriti
        User u = new User(nome, cognome, matricola, mail);
        
        // Verifica se la matricola è già presente nel sistema
        if (model.getUserManagement().getList().contains(u)) {
            labelErrore.setText("Matricola già esistente");
            labelErrore.setStyle("-fx-text-fill: red;");
        }
        // Aggiunge l'utente al sistema se la matricola non esiste
        else if (model.getUserManagement().add(u)) {
            labelErrore.setText("Inserimento avvenuto correttamente");
            labelErrore.setStyle("-fx-text-fill: green;");
            name.clear();
            surname.clear();
            email.clear();
            numberID.clear();
        }
        // In caso di errore nell'inserimento
        else {
            labelErrore.setText("Errore nell'inserimento oppure matricola già esistente");
            labelErrore.setStyle("-fx-text-fill: red;");
        }
    }
    
    /**
     * Metodo chiamato quando l'utente clicca sul pulsante "Indietro".
     * Torna alla pagina della homepage.
     * 
     * @throws IOException Se c'è un errore nel caricamento della homepage.
     */
    @FXML
    public void backPage() throws IOException {
        View.Homepage();
    }
}