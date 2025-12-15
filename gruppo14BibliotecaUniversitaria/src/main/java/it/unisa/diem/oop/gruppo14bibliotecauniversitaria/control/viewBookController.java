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
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
    private Model model; 

    // Metodo per l'iniezione del Model
    public void setModel(Model model) {
        this.model = model;
        // Chiamata per caricare i dati dopo che il Model è disponibile
        loadAllBooks();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // 1. Configurazione delle colonne (Binding ai campi della classe Book)
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title")); 
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); 
        
        // Usa "publicationYear" che è ora una String
        annoPubblicazioneColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN")); 
        copieDisponibiliColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies")); 

        // 2. Listener per il doppio click (Nessun cambiamento)
        bookTableView.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                
                if (selectedBook != null) {
                    try {
                        View.updateBook(selectedBook); 
                    } catch (IOException e) {
                        System.err.println("Errore: Impossibile caricare la pagina di modifica libro.");
                        e.printStackTrace();
                    }
                }
            }
        });
} // Chiusura di initialize
    
    /**
     * Carica tutti i libri dal BookManagement e popola la TableView.
     */
    private void loadAllBooks() {
        if (model == null) {
            System.err.println("Errore: Impossibile caricare i dati. Model non è stato iniettato.");
            return;
        }

        ObservableList<Book> listaDati = FXCollections.observableArrayList(model.getBookManagement().getCatalogue().stream().sorted().collect(Collectors.toList()));
            
        bookTableView.setItems(listaDati);
        bookTableView.getSortOrder().clear();
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
