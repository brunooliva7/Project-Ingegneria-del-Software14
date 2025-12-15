/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import javafx.scene.control.TableCell;
import javafx.collections.transformation.SortedList;

/**
 * @class loanViewController
 * @brief Gestisce la visualizzazione dell'elenco completo dei prestiti.
 * * Questa classe controlla la vista che mostra tutti i prestiti attivi nella biblioteca.
 * Configura la TableView per visualizzare i dettagli dei prestiti (Utente, Libro, Scadenza),
 * gestisce l'ordinamento dinamico delle colonne  e applica uno stile visivo per evidenziare
 * i prestiti scaduti.
 */
public class loanViewController {
    
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
    
    private LoanManagement loanManagement;
    private Model model;
    
    /**
     * @brief Inietta il modello e popola la tabella dei prestiti.
     * * Questo metodo recupera l'istanza di LoanManagement dal modello principale,
     * converte l'insieme dei prestiti in una ObservableList e la avvolge in una SortedList
     * per abilitare l'ordinamento tramite click sulle intestazioni delle colonne.
     * * @param model Il modello condiviso dell'applicazione.
     * * @pre model != null.
     * @post La TableView è popolata con i dati dei prestiti, ordinabili dall'utente.
     */
    
   public void setModel(Model model) {
        this.model = model;
        this.loanManagement = model.getLoanManagement();
        
        if(this.loanManagement != null){
            ObservableList<Loan> observableLoanList = FXCollections.observableArrayList(this.loanManagement.getLoan());
            SortedList<Loan> sortedData = new SortedList<>(observableLoanList);
            sortedData.comparatorProperty().bind(loanTableView.comparatorProperty());
            loanTableView.setItems(sortedData);
        }
    }
   
   /**
     * @brief Inietta il modello e popola la tabella dei prestiti.
     * * Questo metodo recupera l'istanza di LoanManagement dal modello principale,
     * converte l'insieme dei prestiti in una ObservableList e la avvolge in una SortedList
     * per abilitare l'ordinamento tramite click sulle intestazioni delle colonne.
     * * @param model Il modello condiviso dell'applicazione.
     * * @pre model != null.
     * @post La TableView è popolata con i dati dei prestiti, ordinabili dall'utente.
     */
    
    @FXML
    private void initialize(){
        
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
