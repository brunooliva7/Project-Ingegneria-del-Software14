/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View; // Assumendo che esista
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author maramariano
 */
public class searchBookController implements Initializable {
    
    // Campi di Ricerca
    @FXML 
    private TextField searchField;
    @FXML 
    private Button searchButton;

    // Campi di Dettaglio del Libro (in lettura)
    @FXML 
    private TextField titleField;
    @FXML 
    private TextField authorsField;
    @FXML 
    private TextField publicationYearField; 
    @FXML 
    private TextField ISBNField;
    @FXML 
    private TextField availableCopiesField; 
    
    @FXML 
    private Label labelMessage;
    
    // Logica interna
    private final BookManagement bookManager = new BookManagement();
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Disabilita i campi di dettaglio (sono solo in lettura)
        setFieldsEditable(false);
        
        // 2. Disabilita il pulsante di ricerca se il campo è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        // 3. Imposta il messaggio iniziale
        labelMessage.setText("Cerca un libro per titolo, autore o ISBN.");
        labelMessage.setStyle("-fx-text-fill: black;");
        
        // 4. Pulisci la vista (opzionale, se l'FXML lo richiede)
        clearFields();
    }
    
    /**
     * Helper: Imposta lo stato di modificabilità dei campi.
     */
    @FXML
    private void setFieldsEditable(boolean editable) {
        titleField.setEditable(editable);
        authorsField.setEditable(editable);
        publicationYearField.setEditable(editable);
        ISBNField.setEditable(editable);
        availableCopiesField.setEditable(editable);
    }
    
    /**
     * Helper: Pulisce i campi di dettaglio.
     */
    @FXML
    private void clearFields() {
        titleField.clear();
        authorsField.clear();
        publicationYearField.clear();
        ISBNField.clear();
        availableCopiesField.clear();
    }

    /**
     * Helper: Popola i campi con i dati del Libro.
     */
    @FXML
    private void populateFields(Book book) {
        titleField.setText(book.getTitle());
        authorsField.setText(book.getAuthors());
        publicationYearField.setText(book.getPublicationYear()); // String
        ISBNField.setText(book.getISBN());
        availableCopiesField.setText(String.valueOf(book.getAvailableCopies())); // Int -> String
    }
    
    /**
     * Gestisce l'azione del pulsante di ricerca.
     */
    @FXML
    public void search(ActionEvent event) {
        String query = searchField.getText().trim();
        
        // 1. Pulisce la vista prima di cercare
        clearFields();
        labelMessage.setText("Cercando...");
        labelMessage.setStyle("-fx-text-fill: black;");

        if (query.isEmpty()) {
            labelMessage.setText("Inserisci un criterio di ricerca.");
            return;
        }

        try {
            // 2. CREA L'OGGETTO BOOK PARZIALE (la logica di discriminazione è in Book.java)
            Book partialBook = new Book(query); 
            
            // 3. Esegue la ricerca
            List<Book> results = bookManager.search(partialBook); 
            
            if (results.isEmpty()) {
                labelMessage.setText("Nessun libro trovato per la query: " + query);
                labelMessage.setStyle("-fx-text-fill: red;");
                return;
            }

            // 4. Popola i campi con il primo risultato trovato
            Book foundBook = results.get(0);
            populateFields(foundBook);
            
            labelMessage.setText("Libro trovato: " + foundBook.getTitle() + ".");
            labelMessage.setStyle("-fx-text-fill: green;");
            
        } catch (IllegalArgumentException e) {
            // Questo cattura le eccezioni lanciate dal costruttore/manager se l'input è invalido (es. anno non valido)
            labelMessage.setText("Errore: " + e.getMessage());
            labelMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            labelMessage.setText("Errore generico durante la ricerca.");
            labelMessage.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void backPage(ActionEvent event) {
        try {
            View.Homepage();
        } catch (IOException e) {
            System.err.println("Impossibile caricare la Home Page.");
            e.printStackTrace();
        }
    }
}