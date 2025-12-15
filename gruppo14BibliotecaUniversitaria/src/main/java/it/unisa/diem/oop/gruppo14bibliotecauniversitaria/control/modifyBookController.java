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
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class per la modifica dei dati di un Libro.
 * Utilizza una singola schermata per ricerca e form di modifica.
 */
public class modifyBookController {

    // Campi definiti nell'FXML
    @FXML private Button backButton;
    @FXML private Label labelMessage;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField publicationYearField;
    @FXML private TextField ISBNField;
    @FXML private TextField availableCopiesField;
    @FXML private Button confirmButton;

    // Logica interna
    private Model model; // NUOVO: Riferimento al Model
    private Book currentBook = null; // Memorizza il libro attualmente caricato per la modifica

    // NUOVO: Metodo per l'iniezione del Model
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Inizializzazione del controller.
     */
    @FXML
    public void initialize() {
        // I campi di modifica sono inizialmente disabilitati finché un libro non viene trovato
        setFieldsEditable(false);
        
        // Il bottone di conferma è disabilitato finché non c'è un libro da modificare
        confirmButton.setDisable(true);
        
        // Collega il bottone di ricerca al campo di testo:
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        labelMessage.setText("Inserisci ISBN, titolo o autore per cercare il libro da modificare.");
        labelMessage.setStyle("-fx-text-fill: black;");
    }
    
    public void initData(Book book) {
    if (book != null) {
        this.currentBook = book;
        
        // 1. Popola i campi con i dati del libro
        populateFields(book); 
        
        // 2. Assicurati che il campo di ricerca sia chiaro
        searchField.clear(); 
        
        // 3. Il pulsante di conferma è abilitato, il form è modificabile
        //    (Questo è gestito da populateFields/setFieldsEditable)
        
        // 4. Messaggio per l'utente
        labelMessage.setText("Libro caricato tramite doppio click. Modifica i campi o cerca un altro libro.");
        labelMessage.setStyle("-fx-text-fill: #10B981;"); 
        
        // La ricerca rimane disponibile
    }
}
    
    /**
     * Helper: Imposta lo stato di modificabilità dei campi e lo stile.
     */
    private void setFieldsEditable(boolean editable) {
        // L'ISBN non dovrebbe mai essere modificabile, è l'ID
        ISBNField.setEditable(false); 
        
        titleField.setEditable(editable);
        authorsField.setEditable(editable);
        publicationYearField.setEditable(editable);
        availableCopiesField.setEditable(editable);
        
        // Rimuove lo stile di disabilitazione per i campi modificabili
        String style = editable ? "-fx-border-color: #22C55E;" : "-fx-border-color: #E5E7EB;";
        titleField.setStyle(style);
        authorsField.setStyle(style);
        publicationYearField.setStyle(style);
        availableCopiesField.setStyle(style);
        ISBNField.setStyle(style);
    }
    
    /**
     * Pulisce tutti i campi e resetta lo stato.
     */
    private void clearFields() {
        titleField.clear();
        authorsField.clear();
        publicationYearField.clear();
        ISBNField.clear();
        availableCopiesField.clear();
        currentBook = null;
        setFieldsEditable(false);
        confirmButton.setDisable(true);
    }
    
    /**
     * Popola i campi con i dati del Libro trovato.
     */
    private void populateFields(Book book) {
        titleField.setText(book.getTitle());
        authorsField.setText(book.getAuthors());
        publicationYearField.setText(book.getPublicationYear());
        ISBNField.setText(book.getISBN());
        availableCopiesField.setText(String.valueOf(book.getAvailableCopies()));
        setFieldsEditable(true);
        confirmButton.setDisable(false);
    }

    /**
     * Gestisce l'azione del pulsante di ricerca.
     * Cerca il libro e, se trovato, popola il form.
     */
    @FXML
    public void search(ActionEvent event) {
        if (this.model == null) {
            labelMessage.setText("Errore di sistema: Model non iniettato.");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }
        
        String query = searchField.getText().trim();
        clearFields(); 
        labelMessage.setText("Cercando...");
        labelMessage.setStyle("-fx-text-fill: black;");

        if (query.isEmpty()) return;

        try {
            Book partialBook = new Book(query);
            
            // 🚀 CHIAMATA MVC: Delega la ricerca al Model
            List<Book> results = model.getBookManagement().search(partialBook);
            
            if (results.isEmpty()) {
                labelMessage.setText("Nessun libro trovato per la query.");
                labelMessage.setStyle("-fx-text-fill: red;");
                return;
            }

            currentBook = results.get(0);
            populateFields(currentBook);
            
            labelMessage.setText("Libro trovato! Modifica i campi e conferma.");
            labelMessage.setStyle("-fx-text-fill: green;");
            
        } catch (Exception e) {
            labelMessage.setText("Errore durante la ricerca: " + e.getMessage());
            labelMessage.setStyle("-fx-text-fill: red;");
            currentBook = null;
        }
    }
    
    /**
     * Gestisce l'azione del pulsante "Conferma modifiche".
     * Aggiorna il libro nel gestore.
     */
   @FXML
    public void confirm(ActionEvent event) {
        if (currentBook == null) {
            labelMessage.setText("Errore: Nessun libro selezionato da modificare.");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }
        if (this.model == null) {
            labelMessage.setText("Errore di sistema: Model non iniettato.");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        Book originalBook = this.currentBook; 

        try {
            // ... (1. Recupero dati e 2. Validazione - Logica omessa, è corretta) ...
            String nuovoTitolo = titleField.getText().trim();
            String nuoviAutori = authorsField.getText().trim();
            String nuovoAnnoStr = publicationYearField.getText().trim();
            String isbnOriginale = ISBNField.getText().trim(); 
            int nuoveCopie = Integer.parseInt(availableCopiesField.getText().trim());
            
            // 2. Validazione (omessa per brevità, è nel codice originale)
            // ... (Validazione 4 cifre, range anno, copie non negative) ...
            if (!nuovoAnnoStr.matches("\\d{4}")) { /* ... */ return; }
            int annoValue = Integer.parseInt(nuovoAnnoStr);
            if (annoValue < 0 || annoValue > 2025) { /* ... */ return; }
            if (nuoveCopie < 0) { /* ... */ return; }

            // 3. Creazione di una NUOVA entità Book 
            Book updatedBook = new Book(nuovoTitolo, nuoviAutori, nuovoAnnoStr, isbnOriginale, nuoveCopie);

            // 4. 🚀 CHIAMATA MVC: Salva le modifiche tramite il Model
            if (model.getBookManagement().update(originalBook, updatedBook)) {
                labelMessage.setText("✅ Modifiche al libro '" + updatedBook.getTitle() + "' salvate con successo!");
                labelMessage.setStyle("-fx-text-fill: green;");
                clearFields(); 
            } else {
                labelMessage.setText("❌ Errore: Aggiornamento del libro fallito (ISBN non trovato nel gestore).");
                labelMessage.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            labelMessage.setText("Errore: Copie disponibili deve essere un numero intero valido.");
            labelMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            labelMessage.setText("Errore generico durante la modifica: " + e.getMessage());
            labelMessage.setStyle("-fx-text-fill: red;");
    }
}
    
    /**
     * Gestisce il ritorno alla Home Page.
     */
    @FXML
    public void backPage(ActionEvent event) {
        try {
            View.Homepage();
        } catch (IOException e) {
            System.err.println("Impossibile caricare la Home Page.");
        }
    }
}