/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file searchUserController.java
 * @brief Controller per la ricerca di un utente nel sistema.
 * 
 * La classe `searchUserController` gestisce l'interfaccia utente per la ricerca di un utente
 * tramite la sua matricola, nome o cognome. Consente di visualizzare le informazioni dell'utente,
 * tra cui prestiti attivi, all'interno di una `TableView`. La ricerca avviene sulla base dell'input
 * fornito e i risultati vengono mostrati in tempo reale.
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 * @class searchUserController
 * @brief Gestisce la ricerca degli utenti.
 * 
 * La classe `searchUserController` gestisce l'interfaccia grafica e le azioni per cercare un
 * utente in base a una stringa di input, che può essere una matricola, un nome o un cognome.
 * I risultati della ricerca vengono mostrati in una `TableView`, che visualizza le informazioni
 * dell'utente come nome, cognome, matricola, email e prestiti attivi. 
 */
public class searchUserController {
    
    /**
     * Campo di testo per inserire la query di ricerca dell'utente.
     */
    @FXML
    private TextField searchField;

    /**
     * Pulsante per avviare la ricerca dell'utente.
     */
    @FXML
    private Button searchButtonu;

    /**
     * Etichetta per visualizzare i messaggi relativi alla ricerca.
     */
    @FXML
    private Label labelMessage;

    /**
     * Tabella che mostra i risultati della ricerca.
     */
    @FXML
    private TableView<User> userTableViewricerca;

    /**
     * Colonna della tabella per visualizzare il nome dell'utente.
     */
    @FXML
    private TableColumn<User, String> nomeColumn;

    /**
     * Colonna della tabella per visualizzare il cognome dell'utente.
     */
    @FXML
    private TableColumn<User, String> cognomeColumn;

    /**
     * Colonna della tabella per visualizzare la matricola dell'utente.
     */
    @FXML
    private TableColumn<User, String> matricolaColumn;

    /**
     * Colonna della tabella per visualizzare l'email dell'utente.
     */
    @FXML
    private TableColumn<User, String> emailColumn;

    /**
     * Colonna della tabella per visualizzare i prestiti dell'utente.
     */
    @FXML
    private TableColumn<User, String> mapColumn;

    /**
     * Pulsante per tornare alla homepage.
     */
    @FXML
    private Button backButton;

    /**
     * Lista osservabile che contiene gli utenti trovati dalla ricerca.
     */
    private ObservableList<User> userList;

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
     * Configura la `TableView`, le colonne e abilita/disabilita i pulsanti in base agli input dell'utente.
     * 
     * @post Le colonne della `TableView` vengono configurate e il pulsante di ricerca è abilitato solo
     *       quando il campo di ricerca non è vuoto.
     */
    @FXML
    public void initialize() {
        
        // Disabilita il pulsante di ricerca se il campo di ricerca è vuoto
        searchButtonu.disableProperty().bind(searchField.textProperty().isEmpty());
        
        // Imposto le colonne della TableView
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Visualizzare la mappa dei prestiti come stringa
        mapColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            if (user.getBooksOnloan() != null && !user.getBooksOnloan().isEmpty()) {
                String prestiti = user.getBooksOnloan().entrySet().stream()
                        .map(e -> "ISBN libro:" + e.getKey().getISBN() + " -> Data scadenza:" + e.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .collect(Collectors.joining(", "));
                return new SimpleStringProperty(prestiti);
            } else {
                return new SimpleStringProperty("");
            }
        });

        // Inizializza la lista osservabile
        userList = FXCollections.observableArrayList();
        userTableViewricerca.setItems(userList);

        // Aggiungo un'azione al pulsante di ricerca
        searchButtonu.setOnAction(e -> search());
    }
    
    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante di ricerca.
     * Cerca un utente e aggiorna la `TableView` con i risultati.
     * 
     * @post La `TableView` viene aggiornata con i risultati della ricerca. Se non vengono trovati utenti,
     *       viene mostrato un messaggio di errore.
     */
    @FXML
    public void search() {
        // Svuota il messaggio precedente
        labelMessage.setText("");

        // Recupera l'input di ricerca
        String input = searchField.getText().trim();

        // Se il campo di ricerca è vuoto, svuota la lista e esci
        if (input.isEmpty()) {
            userTableViewricerca.getItems().clear();
            return;
        }

        // Crea un nuovo oggetto User con i dati di ricerca
        User userSonda = new User(input);

        // Esegui la ricerca tramite il modello
        List<User> risultati = model.getUserManagement().search(userSonda);

        // Converte i risultati in una lista osservabile per la TableView
        ObservableList<User> datiTabella = FXCollections.observableArrayList(risultati);
        userTableViewricerca.setItems(datiTabella);

        // Se non ci sono risultati, mostra un messaggio di errore
        if (risultati.isEmpty()) {
            labelMessage.setText("Nessun utente trovato");
            labelMessage.setStyle("-fx-text-fill: red;");
        } else {
            // Se ci sono risultati, pulisci il messaggio
            labelMessage.setText("");
        }
    }
    
    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante "Indietro".
     * Torna alla homepage dell'applicazione.
     * 
     * @throws IOException Se c'è un errore nel caricamento della homepage.
     */
    @FXML
    public void backPage() throws IOException {
        View.Homepage();
    }
}