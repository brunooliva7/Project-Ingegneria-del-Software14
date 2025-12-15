/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

/**
 * FXML Controller class
 *
 * @author maramariano
 */

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class per la ricerca di Libri con TableView.
 */
public class searchBookController implements Initializable {
    
    // Campi di Ricerca
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button backButton; // Aggiunto per coerenza con l'altro controller
    @FXML private Label labelMessage;
    
    // NUOVI ELEMENTI: TableView e Colonne
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorsColumn;
    @FXML private TableColumn<Book, Integer> publicationYearColumn; // Integer è più appropriato per l'anno
    @FXML private TableColumn<Book, String> ISBNColumn;
    @FXML private TableColumn<Book, Integer> copiesColumn; // Integer per le copie
    
    // Logica interna
    private final BookManagement bookManager = new BookManagement();
    private ObservableList<Book> bookList; // Lista osservabile per la TableView
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // 1. Inizializzazione della ObservableList
        bookList = FXCollections.observableArrayList();
        bookTableView.setItems(bookList);
        
        // 2. Definizione delle CellValueFactory per le colonne
        // Queste stringhe devono corrispondere esattamente ai nomi dei getter (es. getTitle -> "title") nel modello Book.
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        
        // Assicurati che il getter in Book restituisca un tipo coerente con la colonna (es. String o Integer)
        publicationYearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        
        // 3. Disabilita il pulsante di ricerca se il campo è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        // 4. Imposta il messaggio iniziale e l'azione del bottone
        labelMessage.setText("Cerca un libro per titolo, autore o ISBN.");
        labelMessage.setStyle("-fx-text-fill: black;");
        searchButton.setOnAction(this::search); // Collega l'azione di ricerca al bottone
    }
    
    /**
     * Gestisce l'azione del pulsante di ricerca, mantenendo la logica originale.
     */
    @FXML
    public void search(ActionEvent event) {
        String query = searchField.getText().trim();
        
        // 1. Pulisce la TableView prima di cercare
        bookList.clear(); 
        labelMessage.setText("Cercando...");
        labelMessage.setStyle("-fx-text-fill: black;");

        if (query.isEmpty()) {
            labelMessage.setText("Inserisci un criterio di ricerca.");
            return;
        }

        try {
            // 2. CREA L'OGGETTO BOOK PARZIALE (Logica mantenuta)
            Book partialBook = new Book(query);
            
            // 3. Esegue la ricerca
            List<Book> results = bookManager.search(partialBook);
            
            if (results.isEmpty()) {
                labelMessage.setText("Nessun libro trovato per la query: " + query);
                labelMessage.setStyle("-fx-text-fill: red;");
                return;
            }

            // 4. Popola la TableView con tutti i risultati (NUOVA LOGICA)
            bookList.addAll(results);
            
            labelMessage.setText(results.size() + " libro(i) trovato(i).");
            labelMessage.setStyle("-fx-text-fill: green;");
            
        } catch (IllegalArgumentException e) {
            labelMessage.setText("Errore: " + e.getMessage());
            labelMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            labelMessage.setText("Errore generico durante la ricerca.");
            labelMessage.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    /**
     * Gestisce l'azione del pulsante Indietro.
     */
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

