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
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author maramariano
 */
public class modifyBookController {
    
    // Contenitori per alternare le viste
    @FXML private AnchorPane searchPane;
    @FXML private AnchorPane modifyPane;
    @FXML private VBox mainContainer;
    
    // Campi del Form di Modifica
    @FXML private TextField titleField;
    @FXML private TextField authorsField;
    @FXML private TextField publicationYearField; 
    @FXML private TextField ISBNField;
    @FXML private TextField availableCopiesField; 
    
    // Campi di Ricerca
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button confirmButton;
    
    // Messaggi e Tabella
    @FXML private Label labelMessageModify;
    @FXML private Label labelMessageSearch;
    @FXML private TableView<Book> bookTableView;
    
    // Colonne della Tabella
    @FXML private TableColumn<Book, String> titoloColumn;
    @FXML private TableColumn<Book, String> autoriColumn;
    @FXML private TableColumn<Book, String> annoPubblicazioneColumn; // String
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, String> copieDisponibiliColumn; // String (ma mostra int)
    
    // Logica interna
    private BookManagement bookManagement;
    private Book originalBook = null; // Memorizza la copia originale del libro selezionato
    private List <Book> list; // Risultati della ricerca

    
    @FXML
    public void initialize(){
        this.bookManagement = new BookManagement(); 
        
        // Nasconde la sezione di modifica all'avvio
        modifyPane.setVisible(false);
        modifyPane.setManaged(false);
        
        // Mostra la sezione di ricerca all'avvio
        searchPane.setVisible(true);
        searchPane.setManaged(true);
        
        // Configurazione delle colonne (usa i nomi dei campi di Book.java)
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        // Usa "publicationYear" (String)
        annoPubblicazioneColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear")); 
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        copieDisponibiliColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        
        // Disabilita il pulsante di ricerca se il campo è vuoto
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty()); 
        
        list = new LinkedList<>();
        
        // Ascolta la selezione della riga: quando un libro è selezionato, passa alla modifica
        bookTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,newValue) -> {
            if(newValue != null){
                gestisciSelezione(newValue);
            }
        });
        
        bookTableView.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void search() {
        String input = searchField.getText();
        
        // 1. Usa il costruttore parziale Book(String) per creare l'oggetto sonda
        Book bookSonda = new Book(input); 
        
        // 2. Esegue la ricerca tramite BookManagement.search(Book b)
        this.list = bookManagement.search(bookSonda);

        // 3. Popola la TableView
        ObservableList<Book> listaDati = FXCollections.observableArrayList(this.list);
        bookTableView.setItems(listaDati);

        // 4. Gestione messaggi
        if (this.list.isEmpty()) {
            labelMessageSearch.setText("Libro non trovato.");
            labelMessageSearch.setStyle("-fx-text-fill: red;");
        } else {
             labelMessageSearch.setText("Trovati " + this.list.size() + " libri. Selezionane uno per la modifica.");
             labelMessageSearch.setStyle("-fx-text-fill: green;");
        }
        
        // Pulisce l'oggetto originale in caso di nuova ricerca
        this.originalBook = null;
    } 
    
    private void gestisciSelezione(Book bookSelezionato){
        this.originalBook = bookSelezionato; // Memorizza l'oggetto Book originale
        
        // Popola i campi del form con i dati del libro selezionato
        titleField.setText(originalBook.getTitle());
        authorsField.setText(originalBook.getAuthors());
        publicationYearField.setText(originalBook.getPublicationYear()); // String
        ISBNField.setText(originalBook.getISBN());
        
        // CORREZIONE: Conversione INT -> STRING
        availableCopiesField.setText(String.valueOf(originalBook.getAvailableCopies())); 
        
        labelMessageModify.setText("Modifica i campi e premi Conferma.");
        
        // Cambia vista da Ricerca a Modifica
        modifyPane.setVisible(true);
        modifyPane.setManaged(true);
        searchPane.setVisible(false);
        searchPane.setManaged(false);    
    }

    
    @FXML 
    public void confirm(){
        String newTitle = titleField.getText();
        String newAuthors = authorsField.getText();
        String newPublicationYear = publicationYearField.getText();
        String originalISBN = ISBNField.getText();
        int newAvailableCopies;

        if (originalBook == null) {
            showAlert("Errore", "Nessun libro selezionato per la modifica.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // VALIDAZIONE 1: Verifica anno (4 cifre)
            if (!newPublicationYear.matches("\\d{4}")) { 
                showAlert("Errore Input", "L'anno di pubblicazione deve essere composto da 4 cifre.", Alert.AlertType.ERROR);
                return;
            }
            
            // VALIDAZIONE 2: Conversione delle copie (da String a int)
            newAvailableCopies = Integer.parseInt(availableCopiesField.getText());
            
        } catch (NumberFormatException e) {
            labelMessageModify.setText("Errore: Il campo Copie deve essere un numero intero.");
            labelMessageModify.setStyle("-fx-text-fill: red;");
            return;
        }

        // Creazione dell'oggetto Book con i nuovi dati
        Book newBook = new Book(
            newTitle, 
            newAuthors, 
            newPublicationYear, 
            originalISBN, 
            newAvailableCopies
        );
            
        // Aggiornamento (usa l'originalBook come riferimento per la ricerca)
        if(bookManagement.update(originalBook, newBook)){
            labelMessageModify.setText("Modifica Riuscita");
            labelMessageModify.setStyle("-fx-text-fill: green;");
            
            SearchPage(); // Torna alla pagina di ricerca
            
        } else { 
            labelMessageModify.setText("Modifica non riuscita (Errore Manager).");
            labelMessageModify.setStyle("-fx-text-fill: red;");
        }
    }
    
    /**
     * Torna alla Home Page.
     */
    @FXML
    public void backHomepage(ActionEvent event) { // Aggiunto ActionEvent per coerenza
        try {
            View.Homepage();
        } catch (IOException e) {
             showAlert("Errore di Caricamento", "Impossibile caricare la pagina iniziale.", Alert.AlertType.ERROR);
        }
    } 
    
    /**
     * Torna alla schermata di ricerca (dalla schermata di modifica).
     */
    @FXML
    public void SearchPage() {
        modifyPane.setVisible(false);
        modifyPane.setManaged(false);
        
        searchPane.setVisible(true);
        searchPane.setManaged(true);
        
        // Reset della vista di ricerca
        this.originalBook = null;
        this.list.clear();
        bookTableView.getItems().clear();
        searchField.clear();
        labelMessageSearch.setText("");
        labelMessageModify.setText("");
    } 
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}