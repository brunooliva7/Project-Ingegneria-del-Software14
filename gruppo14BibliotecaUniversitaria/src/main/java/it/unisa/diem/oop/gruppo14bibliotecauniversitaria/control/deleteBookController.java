/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * FXML Controller class
 *
 * @author maramariano
 */
public class deleteBookController implements Initializable {

    // Campi FXML dal layout
    @FXML private Button backButton;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button deleteBookButton;
    @FXML private TableView<Book> bookTableViewricerca; 

    // Colonne della TableView
    @FXML private TableColumn<Book, String> titoloColumn;
    @FXML private TableColumn<Book, String> autoriColumn;
    @FXML private TableColumn<Book, String> dataColumn; 
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Integer> copieColumn;
    
    // Gestione dei dati
    private Model model;
      
    /**
     * @brief Imposta l'istanza del Model.
     * 
     * @param model L'istanza del Model dell'applicazione.
     */
    public void setModel(Model model) {
    this.model = model;
    }

    /**
     * @brief Inizializza il controller.
     * 
     * Configura le CellValueFactory delle colonne della TableView, associa i gestori di eventi
     * ai pulsanti e imposta un Listener per abilitare/disabilitare il pulsante di eliminazione.
     * 
     * @param url Posizione relativa del file FXML.
     * @param rb Risorse specifiche per la localizzazione.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Configurazione delle colonne della TableView
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title")); 
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); 
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN")); 
        copieColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies")); 

        // Associa i metodi ai pulsanti tramite riferimento (this::metodo)
        backButton.setOnAction(this::backPage);
        searchButton.setOnAction(this::handleSearchAction);
        deleteBookButton.setOnAction(this::handleDeleteAction);
        
        // Disabilita il pulsante Elimina all'avvio
        deleteBookButton.setDisable(true);
        
        // Listener: Abilita/Disabilita il pulsante di eliminazione in base alla selezione della riga
        bookTableViewricerca.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteBookButton.setDisable(newSelection == null);
        });
    }

    /**
     * @brief Carica tutti i libri dal catalogo nella TableView.
     * 
     * Utilizzato all'avvio o quando il campo di ricerca è vuoto.
     */
    private void loadAllBooks() {
        if (model == null) return;
        
        // Ottiene la lista dei libri dal Model
        List<Book> allBooks = new java.util.ArrayList<>(model.getBookManagement().getCatalogue()); 
        
        // Imposta l'elenco osservabile sulla TableView
        bookTableViewricerca.setItems(FXCollections.observableArrayList(allBooks));
    }
    
    /**
     * @brief Gestisce l'azione di ricerca (pulsante "Cerca").
     * 
     * Se la query è vuota, carica tutti i libri. Altrimenti, esegue la ricerca
     * parziale tramite il Model e aggiorna la TableView con i risultati.
     *
     * @param event L'evento di azione.
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        if (model == null) return;
        
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            loadAllBooks(); 
            return;
        }

        // Crea un oggetto book parziale
        Book partialBook = new Book(query);

        try {
            // Chiama il Model per eseguire la ricerca
            List<Book> results = model.getBookManagement().search(partialBook); 

            // Imposta i risultati direttamente sulla TableView
            bookTableViewricerca.setItems(FXCollections.observableArrayList(results));

            if (results.isEmpty()) {
                showAlert("Ricerca", "Nessun libro trovato per la query: " + query, AlertType.INFORMATION);
            }
        } catch (Exception e) {
             showAlert("Errore di Ricerca", "Si è verificato un errore durante la ricerca.", AlertType.ERROR);
        }
    }

    /**
     * @brief Gestisce l'azione di eliminazione (pulsante "Elimina").
     * 
     * Rimuove il libro selezionato dalla TableView e chiama il Model per rimuoverlo
     * dal catalogo. Fornisce feedback all'utente tramite Alert.
     * 
     * @param event L'evento di azione.
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        if (model == null) return;
        
        // Ottiene il libro selezionato nella TableView
        Book selectedBook = bookTableViewricerca.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("Errore", "Selezionare un libro da eliminare.", AlertType.ERROR);
            return;
        }

        // Chiama il Model per l'eliminazione dal catalogo principale
        if (model.getBookManagement().remove(selectedBook)) { 
            showAlert("Successo", "Libro eliminato: " + selectedBook.getTitle(), AlertType.INFORMATION);

            // Rimuove l'elemento dalla lista osservabile associata alla TableView.
            bookTableViewricerca.getItems().remove(selectedBook);

        } else {
            //Fallimento
            showAlert("Errore", "Impossibile eliminare il libro. Potrebbe essere in prestito.", AlertType.ERROR);
        }
    }

    /**
     * @brief Gestisce il ritorno alla Home Page.
     * 
     * Carica la Home Page (View.Homepage()).
     * 
     * @param event L'evento di azione.
     */
    @FXML
    private void backPage(ActionEvent event){ 
        try {
            View.Homepage(); 
        } catch (IOException e) {
            showAlert("Errore di Caricamento", "Impossibile caricare la pagina iniziale.", AlertType.ERROR);
        }
    }
    
    /**
     * @brief Metodo helper per mostrare una finestra di alert all'utente.
     * 
     * @param title Titolo della finestra di alert.
     * @param message Contenuto del messaggio visualizzato.
     * @param type Tipo di alert.
     * 
     */
    @FXML
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}