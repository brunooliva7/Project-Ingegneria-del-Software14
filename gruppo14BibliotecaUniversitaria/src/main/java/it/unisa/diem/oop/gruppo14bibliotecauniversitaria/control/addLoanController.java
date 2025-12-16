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
 * @class addLoanController
 * @brief Gestisce l'interfaccia per la registrazione di nuovi prestiti.
 * * Questa classe controlla il processo di creazione di un prestito, permettendo
 * all'operatore di selezionare un utente, un libro e una data di restituzione.
 * Verifica il rispetto dei vincoli di dominio (disponibilità copie, limite prestiti utente,
 * validità data) prima di salvare i dati.
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
    
    /**
     * @brief Inietta il modello e popola i campi di selezione.
     * * Questo metodo riceve il modello condiviso, recupera i gestori dei dati (User, Book, Loan)
     * e popola le ComboBox con le liste aggiornate di utenti e libri disponibili nel catalogo.
     * * @param model Il modello principale dell'applicazione.
     * * @pre model != null.
     * @post I gestori sono inizializzati e le ComboBox sono popolate con i dati caricati.
     */
    public void setModel(Model model) {
        this.model = model;
        
        this.userManagement = model.getUserManagement();
        this.bookManagement = model.getBookManagement();
        this.loanManagement = model.getLoanManagement();
        
        if(userManagement != null && bookManagement != null){
            userComboBox.setItems(FXCollections.observableArrayList(userManagement.getList()));
            bookComboBox.setItems(FXCollections.observableArrayList(bookManagement.getCatalogue()));
        }
    }
    
    /**
     * @brief Inizializza la logica dell'interfaccia utente.
     * * Configura i binding delle proprietà JavaFX per disabilitare il pulsante di registrazione
     * finché tutti i campi obbligatori (Utente, Libro, Data) non sono stati compilati/selezionati.
     * * @pre Gli elementi FXML devono essere caricati correttamente.
     * @post Il pulsante registerButton è abilitato solo se i campi sono valorizzati.
     */
    @FXML
    private void initialize(){
        
        registerButton.disableProperty().bind(userComboBox.valueProperty().isNull().or(bookComboBox.valueProperty().isNull().or(dueDatePicker.valueProperty().isNull())));
    }
    
    /**
     * @brief Gestisce l'evento di registrazione del prestito.
     * * Effettua controlli di validità sui dati inseriti:
     * 1. La data di restituzione non deve essere nel passato.
     * 2. L'utente non deve aver superato il limite massimo di prestiti attivi (3).
     * 3. Il libro deve avere copie disponibili (> 0).
     * * Se tutti i controlli passano, crea il prestito, lo salva, decrementa le copie del libro
     * e aggiorna il database. In caso contrario, mostra un Alert con l'errore.
     * * @pre I campi di input non devono essere null (garantito dal binding del bottone).
     * @post Se successo: Prestito aggiunto, copie libro decrementate, database aggiornati.
     * Se errore: Nessuna modifica ai dati, visualizzazione messaggio di errore.
     */
    @FXML
    private void registerLoan(){      
         
        user = userComboBox.getValue();
        book = bookComboBox.getValue();
        duedate = dueDatePicker.getValue();

        // Controllo validità data
        if (duedate.isBefore(LocalDate.now())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Data di Restituzione");
            alert.setContentText("La data di restituzione inserita non è corretta");
            alert.showAndWait(); 
            return;
        }

        // Controllo limite prestiti utente
        if (user.getBooksOnloan().size() >= 3) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("ERRORE");
            alert.setHeaderText("Numero di Prestiti");
            alert.setContentText("Hai raggiunto il numero di prestiti massimo");
            alert.showAndWait();
            return;
        }

        // Controllo disponibilità copie libro
        if (book.getAvailableCopies() <= 0) {
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("ERRORE");
           alert.setHeaderText("Numero di Prestiti");
           alert.setContentText("Non ci sono copie disponibili per il prestito");
           alert.showAndWait();
           return;
        }

        // Creazione e salvataggio
        loan = new Loan(book, user, duedate);

        if (loanManagement.add(loan)) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            
            
            
            // Aggiornamento persistente delle copie del libro
            bookManagement.update(book, book);

            labelMessage.setText("Prestito aggiunto con successo");
            labelMessage.setStyle("-fx-text-fill: green;");
        } else {
            labelMessage.setText("Errore durante il salvataggio");
            labelMessage.setStyle("-fx-text-fill: red;");
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