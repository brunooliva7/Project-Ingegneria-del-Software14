/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author bruno
 */
public class addLoanController {
    
    @FXML
    private ComboBox<User> userComboBox;
    
    @FXML
    private ComboBox<Book> bookComboBox;
    
    @FXML
    private DatePicker dueDatePicker;
    
    @FXML
    private Label labelMessage;
    
    @FXML
    private Button registerButton;
    
    private UserManagement userManagement;
    private BookManagement bookManagement;
    private Book book = null;
    private User user = null;
    private LocalDate duedate = null;
    private Loan loan = null;
    private LoanManagement loanManagement = null;
    
    @FXML
    private void initialize(){
        
        this.userManagement = new UserManagement();
        this.bookManagement = new BookManagement();
        
        ObservableList<User> userList = FXCollections.observableArrayList(userManagement.getList());
        ObservableList<Book> bookList = FXCollections.observableArrayList(bookManagement.getCatalogue());
        
        userComboBox.setItems(userList);
        bookComboBox.setItems(bookList);
        
        registerButton.disableProperty().bind(userComboBox.valueProperty().isNull().or(bookComboBox.valueProperty().isNull().or(dueDatePicker.valueProperty().isNull())));
    }
    
    @FXML
    private void registerLoan(){
        
        this.loanManagement = new LoanManagement();
        
         book = bookComboBox.getValue();
         user = userComboBox.getValue();
         duedate = dueDatePicker.getValue();
         
         loan = new Loan(book,user,duedate);
         
         if(user.getBooksOnloan().size() <= 3){
             if(book.getAvailableCopies()==0){
            if(loanManagement.add(loan)){
                labelMessage.setText("Modifica Riuscita");
                labelMessage.setStyle("-fx-text-fill: green;");
                book.setAvailableCopies(book.getAvailableCopies() - 1);
            }
         
            else{
                labelMessage.setText("Modifica Riuscita");
                labelMessage.setStyle("-fx-text-fill: green;");
            }
          }
             
             else{
                  Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Numero di copie");
            alert.setContentText("Non ci sono numero di copie per libro selezionato");

            alert.showAndWait();
             }
         }
         else{
             Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Numero di Prestiti");
            alert.setContentText("Hai raggiunto il numero di prestiti massimo");

            alert.showAndWait();
         }
    }
    
    @FXML
    private void backPage() throws IOException{
        View.Homepage();
    }
    
}
