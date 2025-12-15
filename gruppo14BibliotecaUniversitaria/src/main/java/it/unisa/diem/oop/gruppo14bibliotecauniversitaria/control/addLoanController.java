/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
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
    private LoanManagement loanManagement;
    private Model model;
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    @FXML
    private void initialize(){
        
        this.userManagement = model.getUserManagement();
        this.bookManagement = model.getBookManagement();
        this.loanManagement = model.getLoanManagement();
        
        userComboBox.setItems(FXCollections.observableArrayList(userManagement.getList()));
        bookComboBox.setItems(FXCollections.observableArrayList(bookManagement.getCatalogue()));
        
        registerButton.disableProperty().bind(userComboBox.valueProperty().isNull().or(bookComboBox.valueProperty().isNull().or(dueDatePicker.valueProperty().isNull())));
    }
    
    @FXML
    private void registerLoan(){     
         
    user = userComboBox.getValue();
    book = bookComboBox.getValue();
    duedate = dueDatePicker.getValue();

    if (duedate.isBefore(LocalDate.now())) {
        Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("ERRORE");
                    alert.setHeaderText("Data di Restituzione");
                    alert.setContentText("La data di restituzione inserita non è corretta");

                    alert.showAndWait(); 
    }

    if (user.getBooksOnloan().size() >= 3) {
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Numero di Prestiti");
            alert.setContentText("Hai raggiunto il numero di prestiti massimo");

            alert.showAndWait();
    }

    if (book.getAvailableCopies() <= 0) {
       Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Numero di Prestiti");
            alert.setContentText("Hai raggiunto il numero di prestiti massimo");

            alert.showAndWait();
    }

    loan = new Loan(book, user, duedate);

    if (loanManagement.add(loan)) {
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        labelMessage.setText("Prestito aggiunto con successo");
        labelMessage.setStyle("-fx-text-fill: green;");
    } else {
        labelMessage.setText("Errore durante il salvataggio");
        labelMessage.setStyle("-fx-text-fill: red;");
    }
    }
    
    @FXML
    private void backPage() throws IOException{
        View.Homepage();
    }
    
}
