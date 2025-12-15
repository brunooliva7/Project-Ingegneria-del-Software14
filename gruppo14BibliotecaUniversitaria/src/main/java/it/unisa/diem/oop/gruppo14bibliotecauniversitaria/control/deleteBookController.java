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
      
    
    public void setModel(Model model) {
    this.model = model;
    }

    /**
     * Inizializza il controller.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Configurazione delle colonne della TableView
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title")); 
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); 
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate")); 
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN")); 
        copieColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies")); 

        // Associa i metodi ai pulsanti tramite riferimento (this::metodo)
        backButton.setOnAction(this::backPage);
        searchButton.setOnAction(this::handleSearchAction);
        deleteBookButton.setOnAction(this::handleDeleteAction);
        
        // Disabilita il pulsante Elimina all'avvio
        deleteBookButton.setDisable(true);
        
        // Ascolta la selezione della riga per abilitare il pulsante Elimina
        bookTableViewricerca.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteBookButton.setDisable(newSelection == null);
        });
    }

    /**
     * Carica tutti i libri nella TableView all'avvio o dopo una ricerca vuota.
     */
    private void loadAllBooks() {
        if (model == null) return;
        
        // 1. Ottiene la lista dei libri dal Model
        List<Book> allBooks = new java.util.ArrayList<>(model.getBookManagement().getCatalogue()); 
        
        // 2. Imposta l'elenco osservabile DIRETTAMENTE sulla TableView
        // La TableView contiene ora la sua propria lista osservabile interna.
        bookTableViewricerca.setItems(FXCollections.observableArrayList(allBooks));
    }
    
    /**
    * Gestisce l'azione del pulsante di ricerca.
    * Ora utilizza il metodo search(Book b) di BookManagement.
    */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        if (model == null) return;
        
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            loadAllBooks(); 
            return;
        }

        // CREA UN OGGETTO BOOK PARZIALE
        Book partialBook = new Book(query); 
        // Devi usare il costruttore di Book appropriato per Title, Authors, Year, ISBN, Copies

        try {
            List<Book> results = model.getBookManagement().search(partialBook); 

            // Imposta i risultati DIRETTAMENTE sulla TableView
            bookTableViewricerca.setItems(FXCollections.observableArrayList(results));

            if (results.isEmpty()) {
                showAlert("Ricerca", "Nessun libro trovato per la query: " + query, AlertType.INFORMATION);
            }
        } catch (Exception e) {
             showAlert("Errore di Ricerca", "Si è verificato un errore durante la ricerca.", AlertType.ERROR);
        }
    }

    /**
     * Gestisce l'azione di eliminazione del libro selezionato.
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        if (model == null) return;
        
        Book selectedBook = bookTableViewricerca.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("Errore", "Selezionare un libro da eliminare.", AlertType.ERROR);
            return;
        }

        // 1. Chiama il Model per l'eliminazione dal catalogo principale
        if (model.getBookManagement().remove(selectedBook)) { 
            showAlert("Successo", "Libro eliminato: " + selectedBook.getTitle(), AlertType.INFORMATION);

            // 2. AGGIORNAMENTO VISTA: Rimuove l'elemento dalla lista osservabile
            //    accedendo alla lista interna della TableView.
            bookTableViewricerca.getItems().remove(selectedBook);

        } else {
            showAlert("Errore", "Impossibile eliminare il libro. Potrebbe essere in prestito.", AlertType.ERROR);
        }
    }

    /**
     * Gestisce il ritorno alla Home Page.
     * Deve gestire l'IOException internamente perché associato tramite setOnAction.
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
     * Metodo helper per mostrare gli alert.
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