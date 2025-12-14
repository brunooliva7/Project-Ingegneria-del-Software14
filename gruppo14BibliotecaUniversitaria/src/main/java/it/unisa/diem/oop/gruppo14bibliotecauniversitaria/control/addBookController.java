/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author maramariano
 */
public class addBookController {
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
    
    @FXML
    private Button addButton;
    
    @FXML 
    private Label labelErrore;
    
    @FXML
    private Button backButton;
    
    @FXML
    public void initialize(){
        // Logica per disabilitare il bottone "Aggiungi" finché i campi obbligatori non sono compilati.
        // Ad esempio, rendiamo l'aggiunta disponibile solo se Titolo e ISBN non sono vuoti.
        addButton.disableProperty().bind(
                title.textProperty().isEmpty()
                .or(authors.textProperty().isEmpty())
                .or(ISBN.textProperty().isEmpty())
        );
    }
    
   @FXML
    public void addBook() {
        // 1. Recupero i dati dai TextField
        String titolo = title.getText();
        String autore = authors.getText();
        String isbn = ISBN.getText();
        
        // Recuperiamo l'anno come testo dal TextField
        String annoTesto = publicationYear.getText(); 

        LocalDate dataPubblicazione; // Sarà la data finale richiesta dal costruttore Book
        int copieDisponibili;

        try {
            // 2. Tenta la conversione dell'anno e delle copie

            // Conversione dell'ANNO (da String a int)
            int anno = Integer.parseInt(annoTesto);
            
            // Creazione di una LocalDate utilizzando l'anno inserito
            // Impostiamo il mese e il giorno a Gennaio 1 per default (o quello che preferisci)
            dataPubblicazione = LocalDate.of(anno, 1, 1);
            
            // Conversione delle COPIE (da String a int)
            copieDisponibili = Integer.parseInt(availableCopies.getText());
            
        } catch (NumberFormatException e) {
            // Questo gestisce sia un errore nell'anno che nelle copie (se non sono numeri)
            labelErrore.setText("Errore: Anno di pubblicazione e/o Copie disponibili devono essere numeri interi.");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        } catch (java.time.DateTimeException e) {
            // Gestisce se l'anno non fosse valido (es. anno 0 o negativo, anche se Integer.parseInt previene già molto)
            labelErrore.setText("Errore: Anno di pubblicazione non valido.");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        }


        // 3. Creazione dell'oggetto Libro (utilizzando la data creata)
        Book b = new Book(titolo, autore, dataPubblicazione, isbn, copieDisponibili);

        // 4. Inserimento del Libro
        BookManagement bm = new BookManagement();

        if (bm.add(b)) {
            labelErrore.setText("Libro inserito correttamente.");
            labelErrore.setStyle("-fx-text-fill: green;");
            
            // Pulizia dei campi dopo l'inserimento
            title.clear();
            authors.clear();
            publicationYear.clear();
            ISBN.clear();
            availableCopies.clear();
        } else {
            labelErrore.setText("Errore nell'inserimento del libro.");
            labelErrore.setStyle("-fx-text-fill: red;");
        }
    }
    
    @FXML
    public void backPage() throws IOException{
        View.Homepage();
    }
}
