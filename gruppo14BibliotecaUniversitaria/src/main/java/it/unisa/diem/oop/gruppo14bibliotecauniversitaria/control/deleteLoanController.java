/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import javafx.scene.control.TableCell;
import javafx.collections.transformation.SortedList;

/**
 * @class deleteLoanController
 * @brief Gestisce l'eliminazione (restituzione) dei prestiti.
 * * Questa classe controlla l'interfaccia che permette di cercare un prestito attivo
 * e di "restituirlo" (eliminarlo) tramite doppio click. Gestisce l'aggiornamento
 * delle copie disponibili del libro e mantiene la tabella ordinabile e filtrata.
 */
public class deleteLoanController {
    
    @FXML
    private TextField userSearchField;
    
    @FXML
    private TextField bookSearchField;
    
    @FXML
    private TableView<Loan> loanTableView;
    
    @FXML
    private TableColumn<Loan,String> nomeColumn;
    
    @FXML
    private TableColumn<Loan,String> cognomeColumn;
    
    @FXML
    private TableColumn<Loan,String> matricolaColumn;
    
    @FXML
    private TableColumn<Loan,String> autoriColumn;
    
    @FXML
    private TableColumn<Loan,String> titoloColumn;
    
    @FXML
    private TableColumn<Loan,String> isbnColumn;
    
    @FXML
    private TableColumn<Loan,LocalDate> duedateColumn;
    
    @FXML
    private Label labelMessage;
    
    @FXML
    private Button confirmButton;
    
     
     private String userInput;
     private String bookInput;
     
     private LoanManagement loanManagement;
     private BookManagement bookManagement;
     
     
     private List<Loan> loanList;
     
     private Loan loanSelezionato = null;
     
     private Model model;
    
    /**
     * @brief Inietta il modello nel controller.
     * * Recupera le istanze di BookManagement e LoanManagement dal modello principale
     * per garantire che le operazioni vengano effettuate sui dati condivisi.
     * * @param model Il modello principale dell'applicazione.
     * * @pre model != null.
     * @post I gestori dei libri e dei prestiti sono inizializzati.
     */
    public void setModel(Model model) {
        this.model = model;
        
        this.bookManagement = model.getBookManagement();
        this.loanManagement = model.getLoanManagement();
    }
    
    
    /**
     * @brief Inizializza il controller e gestisce l'evento di restituzione.
     * * Configura le colonne della tabella, i binding dei campi di ricerca e la logica
     * del doppio click per la restituzione.
     * Quando un prestito viene restituito, viene aggiornato il numero di copie del libro
     * e la tabella viene rinfrescata mantenendo l'ordinamento corrente.
     * * @pre FXML caricato correttamente.
     * @post La tabella è configurata e pronta a ricevere interazioni.
     */
    @FXML
    private void initialize(){
        
        confirmButton.disableProperty().bind(bookSearchField.textProperty().isEmpty());
        bookSearchField.disableProperty().bind(userSearchField.textProperty().isEmpty());
        
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        autoriColumn.setCellValueFactory(new PropertyValueFactory<>("authors"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        duedateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        
        // Gestione Doppio Click per Eliminazione
        loanTableView.setOnMouseClicked(event -> {
        
            if (event.getClickCount() == 2 && loanTableView.getSelectionModel().getSelectedItem() != null) {
                
                loanSelezionato = loanTableView.getSelectionModel().getSelectedItem();
                
                // 1. Rimuovi il prestito dal database
                if(loanManagement.remove(loanSelezionato)){
                   
                   // 2. Incrementa le copie disponibili del libro
                   loanSelezionato.getBook().setAvailableCopies(loanSelezionato.getBook().getAvailableCopies() + 1);
                   
                   // 3. Salva l'aggiornamento del libro (Usa l'istanza condivisa!)
                   if (this.bookManagement != null) {
                       this.bookManagement.update(loanSelezionato.getBook(), loanSelezionato.getBook());
                   }
                   
                   // 4. Aggiorna la vista richiamando search()
                   // Questo mantiene i filtri attivi e l'ordinamento della tabella
                   search();
                   
                   labelMessage.setText("Prestito rimosso e copie aggiornate");
                   labelMessage.setStyle("-fx-text-fill: green;");
                } else{
                   labelMessage.setText("Errore: Prestito non eliminato");
                   labelMessage.setStyle("-fx-text-fill: red;");
                }
            }
        });
        
        // Configurazione celle per date scadute
        duedateColumn.setCellFactory(column -> new TableCell<Loan, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item.toString());
                        if (item.isBefore(LocalDate.now())) {
                            setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red; -fx-font-weight: bold;");
                        } else {
                            setStyle("");
                        }
                    }
                }
            });
        
        loanTableView.getSelectionModel().clearSelection();
    }
    
    /**
     * @brief Cerca i prestiti e aggiorna la tabella.
     * * Utilizza i dati inseriti nei campi di testo per filtrare i prestiti.
     * I risultati vengono avvolti in una SortedList  per permettere l'ordinamento dinamico
     * delle colonne (es. per data o cognome).
     * * @pre loanManagement inizializzato.
     * @post La tabella visualizza i prestiti trovati, ordinati secondo le preferenze utente.
     */
    @FXML
    private void search(){
        
        if (this.loanManagement == null) return;
        
        userInput = userSearchField.getText();
        bookInput = bookSearchField.getText();

        // Creo oggetti filtro
        Loan filterLoan = new Loan(userInput, bookInput);

        loanList = loanManagement.search(filterLoan);

        if (loanList != null && !loanList.isEmpty()) {
            ObservableList<Loan> observableLoanList = FXCollections.observableArrayList(loanList);
            
            // Creazione SortedList per ordinamento
            SortedList<Loan> sortedData = new SortedList<>(observableLoanList);
            
            // Binding del comparatore
            sortedData.comparatorProperty().bind(loanTableView.comparatorProperty());
            
            loanTableView.setItems(sortedData);
            
            labelMessage.setText("Trovati " + loanList.size() + " prestiti.");
            labelMessage.setStyle("-fx-text-fill: green;");
        } else {
            labelMessage.setText("Nessun prestito trovato.");
            labelMessage.setStyle("-fx-text-fill: red;");
            loanTableView.setItems(FXCollections.observableArrayList());
        }        
    }
    
    /**
     * @brief Torna alla Homepage.
     * * @throws IOException Errore caricamento FXML.
     */
    @FXML
    private void backPage() throws IOException{
        View.Homepage();
    }
}