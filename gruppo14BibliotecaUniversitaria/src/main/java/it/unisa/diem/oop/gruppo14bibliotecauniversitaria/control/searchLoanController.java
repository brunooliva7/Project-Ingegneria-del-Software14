/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
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
 * @class searchLoanController
 * @brief Gestisce la logica dell'interfaccia per la ricerca dei prestiti.
 * * Questa classe permette all'utente di cercare prestiti attivi filtrando per dati dell'utente
 * (matricola o cognome) o del libro (ISBN o titolo). I risultati vengono visualizzati in una
 * TableView ordinabile, con evidenziazione visiva per i prestiti scaduti.
 */

public class searchLoanController {
    
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
     private Model model;
             
     
     private List<Loan> loanList;
     
     private Loan loan = null;
     
     /**
     * @brief Collega il modello principale nel controller.
     * * @param model Il modello condiviso dell'applicazione.
     * * @pre model != null.
     * @post Le istanze di Model e LoanManagement interne sono inizializzate.
     */
    
     public void setModel(Model model) {
        this.model = model;
        this.loanManagement = model.getLoanManagement();     
    }
     
     /**
     * @brief Inizializza il controller e configura la TableView.
     * * Configura le colonne della tabella, imposta i binding per disabilitare/abilitare
     * i campi di ricerca in modo mutuamente esclusivo o sequenziale, e definisce
     * una CellFactory per la colonna delle date per evidenziare in rosso i prestiti scaduti.
     * * @pre Gli elementi FXML devono essere stati caricati correttamente.
     * @post La tabella è configurata e pronta per visualizzare i dati.
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
        
        duedateColumn.setCellFactory(column -> new TableCell<Loan, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Mostra la data
                        setText(item.toString());

                        // Controlla se è scaduta
                        if (item.isBefore(LocalDate.now())) {
                            setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red; -fx-font-weight: bold;");
                        } else {
                            setStyle("");
                        }
                    }
                }
            });
        
    }
    
    /**
     * @brief Esegue la ricerca dei prestiti in base ai filtri inseriti.
     * * Recupera i testi inseriti dall'utente, crea un oggetto Loan "filtro" e interroga
     * il LoanManagement. I risultati vengono avvolti in una SortedList per permettere
     * l'ordinamento dinamico tramite click sulle colonne della tabella.
     * * @pre loanManagement deve essere stato inizializzato tramite setModel.
     * @post La TableView viene aggiornata con i risultati della ricerca (ordinabili).
     */
    @FXML
    private void search(){
        
            userInput = userSearchField.getText();
            bookInput = bookSearchField.getText();

        // Creo oggetti User/Book parziali solo con ID o ISBN
        Loan filterLoan = new Loan(userInput, bookInput);

        loanList = loanManagement.search(filterLoan);

        if (loanList != null && !loanList.isEmpty()) {
            ObservableList<Loan> observableLoanList = FXCollections.observableArrayList(loanList);
            
            SortedList<Loan> sortedData = new SortedList<>(observableLoanList);
            
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
     * @brief Gestisce il ritorno alla pagina principale (Homepage).
     * * @throws IOException Se si verifica un errore nel caricamento della vista Homepage.
     * * @pre Nessuna.
     * @post La scena attiva viene cambiata con la Homepage.
     */
    @FXML
    private void backPage() throws IOException{
        View.Homepage();
    }
    
    
    
}
