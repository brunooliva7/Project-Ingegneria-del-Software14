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
 * FXML Controller class
 *
 * @author maramariano
 */
public class modifyBookController {

    //Campi FXML: Ricerca
    @FXML private Button backButton;
    @FXML private Label labelMessage;
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    
    //Campi FXML: Form di modifica
    @FXML private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField publicationYearField;
    @FXML private TextField ISBNField;
    @FXML private TextField availableCopiesField;
    @FXML private Button confirmButton;

    // Logica interna
    private Model model; //Riferimento al Model
    private Book currentBook = null; // Memorizza il riferimento al libro originale trovato

    /**
     * @brief Imposta l'istanza del Model.
     * 
     * @param model L'istanza del Model dell'applicazione.
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * @brief Metodo di inizializzazione del Controller.
     * 
     * Configura lo stato iniziale dell'interfaccia: disabilita i campi di modifica, 
     * il pulsante di conferma e imposta il data binding per la ricerca.
     */
    @FXML
    public void initialize() {
        // I campi di modifica sono inizialmente disabilitati finché un libro non viene trovato
        setFieldsEditable(false);
        
        // Il bottone di conferma è disabilitato finché non c'è un libro da modificare
        confirmButton.setDisable(true);
        
        // Collega il bottone di ricerca al campo di testo: si abilita solo se il campo non è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        labelMessage.setText("Inserisci ISBN, titolo o autore per cercare il libro da modificare.");
        labelMessage.setStyle("-fx-text-fill: black;");
    }
    
    /**
     * @brief Popola il form con i dati di un libro ricevuto da una vista precedente.
     * 
     * Utilizzato se si arriva a questa schermata selezionando un libro da una TableView esterna.
     * 
     * @param book L'oggetto Book da visualizzare e modificare.
     */
    public void initData(Book book) {
    if (book != null) {
        this.currentBook = book;
        
        // Popola i campi con i dati del libro
        populateFields(book); 
        
        // Per assicurarsi che il campo di ricerca sia chiaro
        searchField.clear(); 
       
        // Messaggio per l'utente
        labelMessage.setText("Libro caricato tramite selezione. Modifica i campi e conferma.");
        labelMessage.setStyle("-fx-text-fill: #10B981;"); 
        
 
    }
}
    
    /**
     * @brief Helper: Imposta lo stato di modificabilità dei campi e applica uno stile visivo.
     * 
     * @param editable Lo stato booleano (true per modificabile, false altrimenti).
     */
    private void setFieldsEditable(boolean editable) {
        // L'ISBN non dovrebbe mai essere modificabile, è l'ID
        ISBNField.setEditable(false); 
        
        titleField.setEditable(editable);
        authorsField.setEditable(editable);
        publicationYearField.setEditable(editable);
        availableCopiesField.setEditable(editable);
        
        // Rimozione dello stile di disabilitazione per i campi modificabili
        String style = editable ? "-fx-border-color: #22C55E;" : "-fx-border-color: #E5E7EB;";
        titleField.setStyle(style);
        authorsField.setStyle(style);
        publicationYearField.setStyle(style);
        availableCopiesField.setStyle(style);
        ISBNField.setStyle(style); // L'ISBN resta non editabile, ma riceve lo stile
    }
    
    /**
     * @brief Pulisce tutti i campi del form e resetta lo stato interno del controller.
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
     * @brief Popola i campi del form con i dati del Libro fornito.
     * 
     * Abilita i campi di modifica e il pulsante di conferma.
     * 
     * @param book L'oggetto Book i cui dati devono essere visualizzati.
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
     * @brief Gestisce l'azione del pulsante di ricerca.
     * 
     * Utilizza la query per cercare un libro tramite il Model. Se trovato, 
     * ne carica i dati nel form di modifica. Se la ricerca restituisce più risultati, viene considerato solo il primo.
     * 
     * @param event L'evento di azione.
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
            // Creazione del Book parziale
            Book partialBook = new Book(query);
            
            //Chiamata MVC: Delega la ricerca al Model
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
     * @brief Gestisce l'azione del pulsante "Conferma modifiche".
     * 
     * Esegue la validazione dei dati modificati e chiama il Model per aggiornare il libro nel gestore.
     * 
     * @param event L'evento di azione.
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
            // Recupero dati e validazione
            String nuovoTitolo = titleField.getText().trim();
            String nuoviAutori = authorsField.getText().trim();
            String nuovoAnnoStr = publicationYearField.getText().trim();
            String isbnOriginale = ISBNField.getText().trim(); 
            
            int nuoveCopie;
            int annoValue;
            
            // Validazione Anno
            if (!nuovoAnnoStr.matches("\\d{4}")) { 
                 labelMessage.setText("Errore: Anno di pubblicazione deve essere composto esattamente da 4 cifre.");
                 labelMessage.setStyle("-fx-text-fill: red;");
                 return;
            }
            annoValue = Integer.parseInt(nuovoAnnoStr);
            if (annoValue < 0 || annoValue > 2025) { 
                 labelMessage.setText("Errore: Anno di pubblicazione non valido (0-2025).");
                 labelMessage.setStyle("-fx-text-fill: red;");
                 return;
            }
            
            // Validazione Copie
            nuoveCopie = Integer.parseInt(availableCopiesField.getText().trim());
            if (nuoveCopie < 0) { 
                 labelMessage.setText("Errore: Il numero di copie disponibili non può essere negativo.");
                 labelMessage.setStyle("-fx-text-fill: red;");
                 return;
            }

            //Creazione di una nuova netità Book con i dati modificati
            Book updatedBook = new Book(nuovoTitolo, nuoviAutori, nuovoAnnoStr, isbnOriginale, nuoveCopie);

            // Chiamata MVC: Salva le modifiche tramite il Model
            if (model.getBookManagement().update(originalBook, updatedBook)) {
                labelMessage.setText("Modifiche al libro '" + updatedBook.getTitle() + "' salvate con successo!");
                labelMessage.setStyle("-fx-text-fill: green;");
                clearFields(); 
            } else {
                labelMessage.setText(" Errore: Aggiornamento del libro fallito (ISBN non trovato nel gestore).");
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
     * @brief Gestisce il ritorno alla Home Page.
     * * Carica la Home Page (View.Homepage()).
     * * @param event L'evento di azione.
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