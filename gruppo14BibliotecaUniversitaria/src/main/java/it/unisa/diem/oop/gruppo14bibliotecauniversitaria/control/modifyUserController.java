/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file modifyUserController.java
 * @brief Controller per la gestione della modifica di un utente esistente.
 * 
 * La classe `modifyUserController` gestisce l'interfaccia utente per la ricerca di un utente 
 * e la modifica delle sue informazioni. Consente di cercare un utente, visualizzare le sue informazioni,
 * e di modificarle. I dati vengono aggiornati nel modello e la `TableView` viene aggiornata automaticamente.
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * @class modifyUserController
 * @brief Gestisce la ricerca e modifica delle informazioni di un utente.
 * 
 * La classe `modifyUserController` permette al bibliotecario di cercare un utente esistente
 * e di modificarne i dati. Una volta selezionato l'utente, vengono visualizzate le informazioni 
 * nelle caselle di testo, che possono essere modificate. Il sistema aggiorna i dati nel modello
 * e riflette le modifiche nella `TableView` automaticamente.
 */
public class modifyUserController {
    
    /**
     * Pane che contiene il modulo di ricerca dell'utente.
     */
    @FXML
    private AnchorPane searchPane;
    
    /**
     * Pane che contiene il modulo di modifica dell'utente.
     */
    @FXML
    private AnchorPane modifyPane;
    
    /**
     * Contenitore principale dell'interfaccia.
     */
    @FXML
    private VBox mainContainer;
    
    /**
     * Campo di testo per il nome dell'utente da modificare.
     */
    @FXML
    private TextField nameField;
    
    /**
     * Campo di testo per il cognome dell'utente da modificare.
     */
    @FXML
    private TextField surnameField;
    
    /**
     * Campo di testo per l'email dell'utente da modificare.
     */
    @FXML
    private TextField emailField;
    
    /**
     * Campo di testo per la matricola dell'utente da modificare.
     */
    @FXML
    private TextField numberidField;
    
    /**
     * Campo di testo per cercare un utente.
     */
    @FXML
    private TextField searchField;
    
    /**
     * Pulsante per avviare la ricerca dell'utente.
     */
    @FXML
    private Button searchButton;
    
    /**
     * Pulsante per confermare la modifica dell'utente.
     */
    @FXML
    private Button confirmButton;
    
    /**
     * Pulsante per tornare alla pagina di prestito dell'utente.
     */
    @FXML
    private Button prestititenteButton;
    
    /**
     * Etichetta per mostrare i messaggi relativi alla modifica dell'utente.
     */
    @FXML
    private Label labelMessageModify;
    
    /**
     * Etichetta per mostrare i messaggi relativi alla ricerca dell'utente.
     */
    @FXML
    private Label labelMessageSearch;
    
    /**
     * Tabella che mostra i risultati della ricerca degli utenti.
     */
    @FXML
    private TableView<User> userTableView;
   
    /**
     * Colonna per visualizzare il nome dell'utente.
     */
    @FXML
    private TableColumn<User, String> nomeColumn;

    /**
     * Colonna per visualizzare il cognome dell'utente.
     */
    @FXML
    private TableColumn<User, String> cognomeColumn;

    /**
     * Colonna per visualizzare la matricola dell'utente.
     */
    @FXML
    private TableColumn<User, String> matricolaColumn;
     
    /**
     * Oggetto `User` che rappresenta l'utente selezionato per la modifica.
     */
    private User risultato = null;
     
    /**
     * Lista di utenti trovati durante la ricerca.
     */
    List<User> list;
    
    /**
     * Lista osservabile di utenti per l'aggiornamento della `TableView`.
     */
    private ObservableList<User> observableList;
    
    /**
     * Modello dell'applicazione che gestisce i dati.
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
     * Metodo chiamato all'inizializzazione del controller.
     * Configura la visibilità dei pannelli di ricerca e modifica e imposta le colonne della `TableView`.
     * 
     * Abilita il pulsante di ricerca solo se il campo di ricerca non è vuoto.
     * 
     * @post Le colonne della `TableView` vengono configurate e l'utente può iniziare la ricerca.
     */
    @FXML
    public void initialize() {
        modifyPane.setVisible(false);
        modifyPane.setManaged(false);
        
        searchPane.setVisible(true);
        searchPane.setManaged(true);
        
        // Configurazione delle colonne della TableView
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        
        observableList = FXCollections.observableArrayList();
        userTableView.setItems(observableList);
        
        // Disabilita il pulsante di ricerca se il campo di ricerca è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        list = new ArrayList<>();
        
        // Gestisce la selezione di una riga nella TableView
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gestisciSelezione(newValue);
            }
        });
        
        userTableView.getSelectionModel().clearSelection();
    }
    
    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante di ricerca.
     * Cerca un utente in base al nome, cognome o matricola e aggiorna la `TableView` con i risultati.
     * 
     * @post La `TableView` viene aggiornata con gli utenti trovati, se esistono.
     */
    @FXML
    public void search() {
        String input = searchField.getText();
        User userSonda = new User(input);
        this.list = model.getUserManagement().search(userSonda);

        if (!list.isEmpty()) {
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
            matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        
            observableList = FXCollections.observableArrayList(this.list);
            userTableView.setItems(observableList);
        } else {
            observableList.clear();
            labelMessageSearch.setText("Utente non trovato.");
            labelMessageSearch.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Gestisce la selezione di un utente nella `TableView`.
     * Quando un utente viene selezionato, le sue informazioni vengono visualizzate nei campi di modifica.
     * 
     * @param userSelezionato L'utente selezionato nella `TableView`.
     */
    private void gestisciSelezione(User userSelezionato) {
        this.risultato = userSelezionato;
        
        nameField.setText(risultato.getName());
        surnameField.setText(risultato.getSurname());
        emailField.setText(risultato.getEmail());
        numberidField.setText(risultato.getNumberId());
        
        modifyPane.setVisible(true);
        modifyPane.setManaged(true);
        
        searchPane.setVisible(false);
        searchPane.setManaged(false); 
    }

    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante di conferma per modificare l'utente.
     * 
     * Crea un nuovo oggetto `User` con i dati modificati e aggiorna il modello.
     * Se la modifica è riuscita, aggiorna la `TableView` e mostra un messaggio di conferma.
     * 
     * @post I dati dell'utente vengono aggiornati nel sistema e la `TableView` viene aggiornata.
     */
    @FXML 
    public void confirm() {
        User newUser = new User(nameField.getText(), surnameField.getText(), numberidField.getText(), emailField.getText());
        
        if (model.getUserManagement().update(risultato, newUser)) {
            labelMessageModify.setText("Modifica Riuscita");
            labelMessageModify.setStyle("-fx-text-fill: green;");
            
            // Aggiorno la TableView con il nuovo utente
            int index = observableList.indexOf(risultato);
            if (index >= 0) {
                observableList.set(index, newUser);
            }
            
            risultato = newUser; // aggiorno il riferimento
        } else {
            labelMessageModify.setText("Modifica non riuscita");
            labelMessageModify.setStyle("-fx-text-fill: red;");
        }
    }
    
    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante "Indietro".
     * Torna alla homepage dell'applicazione.
     * 
     * @throws IOException Se c'è un errore nel caricamento della homepage.
     */
    @FXML
    public void backHomepage() throws IOException {
        View.Homepage();
    }

    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante "Ricerca".
     * Mostra il pannello di ricerca e nasconde il pannello di modifica.
     * 
     * @throws IOException Se c'è un errore nel caricamento della pagina di ricerca.
     */
    @FXML
    public void SearchPage() throws IOException {
        modifyPane.setVisible(false);
        modifyPane.setManaged(false);
        
        searchPane.setVisible(true);
        searchPane.setManaged(true);
    }
}