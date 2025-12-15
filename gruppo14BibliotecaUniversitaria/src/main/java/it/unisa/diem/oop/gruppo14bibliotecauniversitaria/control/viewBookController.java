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
import java.net.URL;
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
    // Controlli FXML
    @FXML private Button backButton;
    @FXML private TableView<Book> bookTableView;
    
    // Colonne
    @FXML private TableColumn<Book, String> titoloColumn;
    @FXML private TableColumn<Book, String> autoriColumn;
    @FXML private TableColumn<Book, String> annoPubblicazioneColumn; 
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Integer> copieDisponibiliColumn; 

    // Logica interna
    private Model model; 

   /**
     * @brief Imposta l'istanza del Model.
     * 
     * @param model L'istanza del Model dell'applicazione.
     */
    public void setModel(Model model) {
        this.model = model;
        // Chiamata per caricare i dati dopo che il Model è disponibile
        loadAllBooks();
    }

    /**
     * @brief Metodo di inizializzazione del Controller.
     * 
     * Configura il binding delle colonne della TableView e imposta il listener
     * per il doppio click sulla riga.
     * 
     * @param url Posizione relativa del file FXML.
     * @param rb Risorse specifiche per la localizzazione.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Configurazione delle colonne (Binding ai campi della classe Book)
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title")); 
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors")); 
        
        annoPubblicazioneColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN")); 
        copieDisponibiliColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies")); 

        // Listener per il doppio click
        bookTableView.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                
                Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                
                if (selectedBook != null) {
                    try {
                        // Passa il libro selezionato alla View per caricare la schermata di modifica
                        View.updateBook(selectedBook); 
                    } catch (IOException e) {
                        System.err.println("Errore: Impossibile caricare la pagina di modifica libro.");
                        e.printStackTrace();
                    }
                }
            }
        });
    } 
    
   /**
     * @brief Carica tutti i libri dal gestore del Model e popola la TableView.
     * 
     * Esegue un ordinamento preliminare della lista prima di popolare l'ObservableList.
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
     * @brief Gestisce il ritorno alla Home Page.
     * 
     * Carica la Home Page (View.Homepage()).
     * 
     * @param event L'evento di azione del pulsante.
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
