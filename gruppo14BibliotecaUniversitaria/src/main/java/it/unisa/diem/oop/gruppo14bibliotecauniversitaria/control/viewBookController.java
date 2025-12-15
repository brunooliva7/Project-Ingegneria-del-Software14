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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author maramariano
 */
public class viewBookController implements Initializable {

    @FXML private Button backButton;
    @FXML private TableView<Book> bookTableView;
    
    // Colonne (corrispondono ai campi della classe Book)
    @FXML private TableColumn<Book, String> titoloColumn;
    @FXML private TableColumn<Book, String> autoriColumn;
    // Tipo String, come stabilito nella classe Book
    @FXML private TableColumn<Book, String> annoPubblicazioneColumn; 
    @FXML private TableColumn<Book, String> isbnColumn;
    // Tipo Integer, ma mostrato in una colonna String nell'FXML (usiamo Integer nel codice)
    @FXML private TableColumn<Book, Integer> copieDisponibiliColumn; 

    // Logica interna
    private ObservableList<Book> bookList;
    private final BookManagement bookManager = new BookManagement(); 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // 1. Configurazione delle colonne (Binding ai campi della classe Book)
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title")); 
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); 
        
        // Usa "publicationYear" che è ora una String
        annoPubblicazioneColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN")); 
        copieDisponibiliColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies")); 

        // 2. Carica i dati all'avvio
        loadAllBooks();
        
        bookTableView.setOnMouseClicked((MouseEvent event) -> {
        // Controlla se è stato un doppio click del tasto primario del mouse
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            
            // Ottiene il libro selezionato
            Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
            
            if (selectedBook != null) {
                try {
                    // Chiamata per la navigazione
                    View.updateBook(); 
                    
                } catch (IOException e) {
                    System.err.println("Errore: Impossibile caricare la pagina di modifica libro.");
                    e.printStackTrace();
                }
            }
        }
    });
    }
    
    /**
     * Carica tutti i libri dal BookManagement e popola la TableView.
     */
    private void loadAllBooks() {
        // Usa getCatalogue() se UserManagement ha un Set<Book>
        bookList = FXCollections.observableArrayList(bookManager.getCatalogue()); 
        
        bookTableView.setItems(bookList);
        
        if (bookList.isEmpty()) {
            System.out.println("Nessun libro trovato nel catalogo.");
            // Potresti aggiungere un messaggio Label per feedback utente
        }
    }
    
    
    /**
     * Gestisce il ritorno alla Home Page.
     * Metodo richiesto dall'onAction="#backPage" nel file FXML.
     */
    @FXML
    public void backPage(ActionEvent event) {
        try {
            View.Homepage();
        } catch (IOException e) {
            System.err.println("Impossibile caricare la Home Page.");
            // Gestione alert per l'utente in caso di errore
        }
    }
}
