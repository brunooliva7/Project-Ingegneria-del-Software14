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
    private Model model; // Riferimento al Model iniettato


    public void setModel(Model model) {
        this.model = model;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. NON INIZIALIZZIAMO ObservableList, la TableView sarà vuota.
        
        // 2. Definizione delle CellValueFactory per le colonne (omesse, restano invariate)
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        publicationYearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        
        // 3. Disabilita il pulsante di ricerca se il campo è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());
        
        // 4. Imposta il messaggio iniziale e l'azione del bottone
        labelMessage.setText("Cerca un libro per titolo, autore o ISBN.");
        labelMessage.setStyle("-fx-text-fill: black;");
        searchButton.setOnAction(this::search);
        
    }
    
    /**
     * Gestisce l'azione del pulsante di ricerca, mantenendo la logica originale.
     */
    @FXML
    public void search(ActionEvent event) {
        // 1. Controllo Model
        if (this.model == null) {
            labelMessage.setText("Errore di sistema: Model non iniettato.");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        String query = searchField.getText().trim();
        
        // Pulisce la TableView in modo diretto
        bookTableView.getItems().clear(); 
        labelMessage.setText("Cercando...");
        labelMessage.setStyle("-fx-text-fill: black;");

        if (query.isEmpty()) {
            labelMessage.setText("Inserisci un criterio di ricerca.");
            return;
        }

        try {
            // CREA L'OGGETTO BOOK PARZIALE
            Book partialBook = new Book(query);
            
            // 🚀 CHIAMATA MVC: Esegue la ricerca tramite il Model
            List<Book> results = model.getBookManagement().search(partialBook);
            
            // 2. Imposta l'elenco osservabile DIRETTAMENTE sulla TableView
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

