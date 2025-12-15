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

/**
 *
 * @author bruno
 */
public class searchLoanController {
    
    @FXML
    private TextField userSearchField;
    
    @FXML
    private TextField bookSearchField;
    
    @FXML
    private TableView loanTableView;
    
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
     
     private List<Loan> loanList;
     
     private Loan loan = null;
    
   
    
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
        
    }
    
    @FXML
    private void search(){
        
        this.loanManagement = new LoanManagement();
        
         userInput = userSearchField.getText();
         bookInput = bookSearchField.getText();
         
         loan = new Loan(userInput,bookInput);
         
         loanList = loanManagement.search(loan);
         
         if(loanList != null){
              ObservableList<Loan> observableLoanList = FXCollections.observableArrayList(this.loanList);
            loanTableView.setItems(observableLoanList);
         }
         
         else{
                labelMessage.setText("Modifica non riuscita");
                labelMessage.setStyle("-fx-text-fill: red;");
         }    
    }
    
    @FXML
    private void backPage() throws IOException{
        View.Homepage();
    }
    
    
    
}
