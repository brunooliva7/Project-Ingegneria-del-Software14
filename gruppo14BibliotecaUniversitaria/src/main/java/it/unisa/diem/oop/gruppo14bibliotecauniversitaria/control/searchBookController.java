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

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
 * FXML Controller class
 * 
 * @author maramariano
 */
public class searchBookController implements Initializable {
    
    // Campi di Ricerca e Controlli
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button backButton; // Aggiunto per coerenza con l'altro controller
    @FXML private Label labelMessage;
    
    // TableView e Colonne
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorsColumn;
    @FXML private TableColumn<Book, Integer> publicationYearColumn; // Integer è più appropriato per l'anno
    @FXML private TableColumn<Book, String> ISBNColumn;
    @FXML private TableColumn<Book, Integer> copiesColumn; // Integer per le copie
    
    // Logica interna
    private Model model; // Riferimento al Model 

    /**
     * @brief Imposta l'istanza del Model.
     * 
     * @param model L'istanza del Model dell'applicazione.
     */
    public void setModel(Model model) {
        this.model = model;
    }
    
    /**
     * @brief Inizializza il controller.
     * Configura le CellValueFactory per le colonne della TableView e imposta i binding
     * per disabilitare il pulsante di ricerca se il campo è vuoto.
     * 
     * @param url Posizione relativa del file FXML.
     * @param rb Risorse specifiche per la localizzazione.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Definizione delle CellValueFactory per le colonne
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        publicationYearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        
        // Disabilita il pulsante di ricerca se il campo è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        // Imposta il messaggio iniziale e l'azione del bottone
        labelMessage.setText("Cerca un libro per titolo, autore o ISBN.");
        labelMessage.setStyle("-fx-text-fill: black;");
        searchButton.setOnAction(this::search);
        
    }
    
    /**
     * @brief Gestisce l'azione del pulsante di ricerca.
     * 
     * * Crea un oggetto Book criterio utilizzando la query immessa (che replica la stringa
     * in Titolo, Autori e ISBN) e delega la ricerca unificata al Model.
     * 
     *  @param event L'evento di azione.
     */
    @FXML
    public void search(ActionEvent event) {
        // Controllo Model
        if (this.model == null) {
            labelMessage.setText("Errore di sistema: Model non iniettato.");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        String query = searchField.getText().trim();
        
        // Pulisce la TableView in modo diretto e resetta il messaggio
        bookTableView.getItems().clear(); 
        labelMessage.setText("Cercando...");
        labelMessage.setStyle("-fx-text-fill: black;");

        if (query.isEmpty()) {
            labelMessage.setText("Inserisci un criterio di ricerca.");
            return;
        }

        try {
            // Crea l'oggetto Book parziale
            Book partialBook = new Book(query);
            
            // Chiamata MVC: Esegue la ricerca tramite il Model
            List<Book> results = model.getBookManagement().search(partialBook);
            
            // Imposta l'elenco osservabile direttamente sulla TableView
            bookTableView.setItems(FXCollections.observableArrayList(results));
            
            if (results.isEmpty()) {
                labelMessage.setText("Nessun libro trovato per la query: " + query);
                labelMessage.setStyle("-fx-text-fill: red;");
                return;
            }

            labelMessage.setText(results.size() + " libro(i) trovato(i).");
            labelMessage.setStyle("-fx-text-fill: green;");
            
        } catch (IllegalArgumentException e) {
            labelMessage.setText("Errore nel formato della ricerca: " + e.getMessage());
            labelMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            labelMessage.setText("Errore generico durante la ricerca.");
            labelMessage.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    /**
     * @brief Gestisce l'azione del pulsante Indietro.
     * 
     * Carica la Home Page (View.Homepage()).
     * 
     * @param event L'evento di azione.
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

