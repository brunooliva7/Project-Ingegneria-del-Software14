/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
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
 * FXML Controller class
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
    
    private Model model;
      
    
    public void setModel(Model model) {
    this.model = model;
    }
    
    @FXML
    public void initialize(){
        // Logica per disabilitare il bottone "Aggiungi" finché i campi obbligatori non sono compilati.
        // Ad esempio, rendiamo l'aggiunta disponibile solo se Titolo, Autori e ISBN non sono vuoti.
        addButton.disableProperty().bind(
                title.textProperty().isEmpty()
                .or(authors.textProperty().isEmpty())
                .or(ISBN.textProperty().isEmpty())
        );
    }
    
    @FXML
    public void addBook() {
        if (this.model == null) {
        labelErrore.setText("Errore di sistema: Model non iniettato.");
        labelErrore.setStyle("-fx-text-fill: red;");
        return;
    }
        
        // 1. Recupero i dati dai TextField
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
            
            // Conversione delle COPIE (da String a int)
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


        // 3. Creazione dell'oggetto Libro (utilizzando la data creata)
        Book b = new Book(titolo, autore, anno, isbn, copieDisponibili);

        if (model.getBookManagement().getCatalogue().contains(b)) {
            labelErrore.setText("Errore: Un libro con questo ISBN è già presente nel catalogo.");
            labelErrore.setStyle("-fx-text-fill: red;");
            return;
        }
        
        else if (model.getBookManagement().add(b)) { 
        labelErrore.setText("Libro inserito correttamente.");
        labelErrore.setStyle("-fx-text-fill: green;");
        
        // Pulizia dei campi dopo l'inserimento
        title.clear();
        authors.clear();
        publicationYear.clear();
        ISBN.clear();
        availableCopies.clear();
    } else {
        // Se l'add fallisce (es. ISBN duplicato), il Model ritorna false.
        labelErrore.setText("Errore nell'inserimento del libro (es. ISBN già presente).");
        labelErrore.setStyle("-fx-text-fill: red;");
    }
}
    
    @FXML
    public void backPage() throws IOException{
        View.Homepage();
    }
}
