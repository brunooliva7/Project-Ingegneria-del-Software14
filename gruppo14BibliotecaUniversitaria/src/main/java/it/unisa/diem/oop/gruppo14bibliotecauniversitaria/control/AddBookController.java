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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author maramariano
 */
public class AddBookController implements Initializable {

    // Campi dei dati per l'inserimento del libro
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
    
    // Elementi di controllo
    @FXML
    private Button addButton;
    
    @FXML 
    private Label labelErrore;
    
    @FXML
    private Button backButton;
    
    //Inizializzazione del controller
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        int annoPubblicazione;
        int copieDisponibili;

        try {
            // 2. Converto i valori numerici e gestisco errori di input
            annoPubblicazione = Integer.parseInt(publicationYear.getText());
            copieDisponibili = Integer.parseInt(availableCopies.getText());
        } catch (NumberFormatException e) {
            labelErrore.setText("Errore: Anno e/o Copie devono essere numeri interi.");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        }

        // 3. Creazione dell'oggetto Libro
        Book b = new Book(titolo, autore, annoPubblicazione, isbn, copieDisponibili);

        // 4. Inserimento del Libro
        BookManagement bm = new BookManagement();

        if (bm.add(b)) {
            labelErrore.setText("Libro inserito correttamente.");
            labelErrore.setStyle("-fx-text-fill: green;");
            
            // Pulizia deii campi dopo l'inserimento
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
