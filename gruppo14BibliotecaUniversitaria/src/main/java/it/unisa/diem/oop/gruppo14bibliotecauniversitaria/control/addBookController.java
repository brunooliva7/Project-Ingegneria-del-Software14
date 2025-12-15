/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 * 
 * @author maramariano
 */
public class addBookController {
    // Riferimenti FXML per i campi di input
    @FXML
    private TextField title;
    
    @FXML
    private TextField authors;
    
    @FXML
    private TextField publicationYear;
    
    @FXML
    private TextField ISBN;
    
    @FXML
    private TextField availableCopies;
    
    //Riferimenti FXML per i controlli e i messaggi
    @FXML
    private Button addButton;
    
    @FXML 
    private Label labelErrore;
    
    @FXML
    private Button backButton;
    
    // Riferimento al Model
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
     * @brief Metodo di inizializzazione del Controller.
     * 
     * Imposta il data binding per disabilitare il pulsante di aggiunta finché 
     * tutti i campi obbligatori (Titolo, Autori, ISBN) non sono compilati.
     */
    @FXML
    public void initialize(){
        addButton.disableProperty().bind(
                title.textProperty().isEmpty()
                .or(authors.textProperty().isEmpty())
                .or(ISBN.textProperty().isEmpty())
        );
    }
    
    
    /**
     * @brief Gestisce l'aggiunta di un libro al catalogo.
     * Recupera i dati dai campi, ne valida il formato (Anno e Copie), 
     * e chiama il Model per l'inserimento. Aggiorna la labelErrore con l'esito.
     */
    @FXML
    public void addBook() {
        if (this.model == null) {
        labelErrore.setText("Errore di sistema: Model non iniettato.");
        labelErrore.setStyle("-fx-text-fill: red;");
        return;
    }
        
        // Recupero i dati dai TextField
        String titolo = title.getText();
        String autore = authors.getText();
        String isbn = ISBN.getText();
        String anno = publicationYear.getText();
        

        int copieDisponibili;

        //Verifica che l'anno sia composto da soli numeri
        try {
            if (!anno.matches("\\d{4}")) { 
                labelErrore.setText("Errore: Anno di pubblicazione deve essere composto esattamente da 4 cifre.");
                labelErrore.setStyle("-fx-text-fill: red;");
                return;
            }
            
            int annoValue = Integer.parseInt(anno);
            
            // Verifica che l'anno sia nel range [0, 2025]
            if (annoValue < 0 || annoValue > 2025) {
                labelErrore.setText("Errore: L'anno di pubblicazione deve essere compreso tra 0 e 2025.");
                labelErrore.setStyle("-fx-text-fill: red;");
                return;
            }
            
            // Conversione delle copie (da String a int)
            copieDisponibili = Integer.parseInt(availableCopies.getText());
            if (copieDisponibili < 0) {
                labelErrore.setText("Errore: Il numero di copie disponibili non può essere negativo.");
                labelErrore.setStyle("-fx-text-fill: red;");
                return; // Ferma l'esecuzione se le copie sono negative
            }
            
        } catch (NumberFormatException e) {
            // Questo gestisce l'errore se availableCopies non è un numero
            labelErrore.setText("Errore: Copie disponibili deve essere un numero intero.");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        }


        // Creazione dell'oggetto Libro
        Book b = new Book(titolo, autore, anno, isbn, copieDisponibili);
        
        // Controllo preliminare di duplicato basato su equals/ISBN
        if (model.getBookManagement().getCatalogue().contains(b)) {
            labelErrore.setText("Errore: Un libro con questo ISBN è già presente nel catalogo.");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        }
        
        // Chiama il Model per l'inserimento e gestisce il risultato
        else if (model.getBookManagement().add(b)) { 
            // Inserimento avvenuto con successo
            labelErrore.setText("Libro inserito correttamente.");
            labelErrore.setStyle("-fx-text-fill: green;");
        
            // Pulizia dei campi dopo l'inserimento
            title.clear();
            authors.clear();
            publicationYear.clear();
            ISBN.clear();
            availableCopies.clear();
        } else {
            // Fallimento nell'inserimento
            labelErrore.setText("Errore nell'inserimento del libro (es. ISBN già presente).");
            labelErrore.setStyle("-fx-text-fill: red;");
        }
}
    /**
     * @brief Gestisce il ritorno alla pagina principale.
     * Carica la Home Page (View.Homepage()).
     * 
     * @throws IOException se il file FXML della Homepage non può essere caricato.
     */
    @FXML
    public void backPage() throws IOException{
        View.Homepage();
    }
}
