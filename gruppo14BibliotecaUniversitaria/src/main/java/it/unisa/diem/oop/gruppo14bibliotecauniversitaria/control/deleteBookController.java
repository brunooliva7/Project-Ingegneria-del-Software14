/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate; // Necessario per la colonna della data/anno
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
 * FXML Controller class per la schermata di eliminazione libro.
 *
 * @author maramariano
 */
public class deleteBookController implements Initializable {

    // Campi FXML dal layout
    @FXML
    private Button backButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;
    
    @FXML
    private Button deleteBookButton; // Bottone "Elimina libro"

    @FXML
    private TableView<Book> bookTableViewricerca; // La TableView principale

    // Colonne della TableView
    @FXML
    private TableColumn<Book, String> titoloColumn;
    
    @FXML
    private TableColumn<Book, String> autoriColumn;
    
    @FXML
    private TableColumn<Book, LocalDate> dataColumn; // Usiamo LocalDate
    
    @FXML
    private TableColumn<Book, String> isbnColumn;
    
    @FXML
    private TableColumn<Book, Integer> copieColumn;
    
    // Gestione dei dati
    private ObservableList<Book> bookList;
    private final BookManagement bookManager = new BookManagement(); // Istanza di BookManagement

    /**
     * Inizializza il controller.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Configurazione delle colonne della TableView
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title")); // Assumendo campo 'title' in Book
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); // Assumendo campo 'authors' in Book
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate")); // Assumendo campo 'publicationDate' in Book
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN")); // Assumendo campo 'ISBN' in Book
        copieColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies")); // Assumendo campo 'availableCopies' in Book

        // 2. Associa i metodi ai pulsanti
        backButton.setOnAction(this::backPage);
        searchButton.setOnAction(this::handleSearchAction);
        deleteBookButton.setOnAction(this::handleDeleteAction);
        
        // Inizializza la lista (carica tutti i libri all'avvio)
        loadAllBooks();
        
        // Disabilita il pulsante Elimina all'avvio (si abiliterà alla selezione)
        deleteBookButton.setDisable(true);
        
        // 3. Ascolta la selezione della riga per abilitare il pulsante Elimina
        bookTableViewricerca.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteBookButton.setDisable(newSelection == null);
        });
    }

    /**
     * Carica tutti i libri nella TableView (ad esempio, all'avvio).
     */
    private void loadAllBooks() {
    // Converte il Set (catalogue) in List
    List<Book> allBooks = new java.util.ArrayList<>(bookManager.getCatalogue());
    
    bookList = FXCollections.observableArrayList(allBooks);
    bookTableViewricerca.setItems(bookList);
}
    
    /**
     * Gestisce l'azione del pulsante di ricerca.
     */
    private void handleSearchAction(ActionEvent event) {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            loadAllBooks(); 
            return;
        }

        // Chiama il metodo corretto che esegue la ricerca parziale su stringa
        List<Book> results = bookManager.searchBooks(query); 
        bookList = FXCollections.observableArrayList(results);
        bookTableViewricerca.setItems(bookList);

        if (results.isEmpty()) {
            showAlert("Ricerca", "Nessun libro trovato per la query: " + query, AlertType.INFORMATION);
        }
    }

    /**
     * Gestisce l'azione di eliminazione del libro selezionato.
     */
    private void handleDeleteAction(ActionEvent event) {
        Book selectedBook = bookTableViewricerca.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("Errore", "Selezionare un libro da eliminare.", AlertType.ERROR);
            return;
        }

        // ORA: Chiama il metodo remove() che hai in BookManagement
        if (bookManager.remove(selectedBook)) { 
            showAlert("Successo", "Libro eliminato: " + selectedBook.getTitle(), AlertType.INFORMATION);

            // Rimuove l'elemento dalla lista visualizzata
            bookList.remove(selectedBook);

        } else {
            showAlert("Errore", "Impossibile eliminare il libro.", AlertType.ERROR);
        }
    }

    /**
     * Gestisce il ritorno alla Home Page.
     * @param event
     */
    @FXML
    public void backPage(ActionEvent event){
        try {
            View.Homepage(); 
        } catch (IOException e) {
            showAlert("Errore di Caricamento", "Impossibile caricare la pagina iniziale.", AlertType.ERROR);
        }
    } 
    
    /**
     * Metodo helper per mostrare gli alert.
     */
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}