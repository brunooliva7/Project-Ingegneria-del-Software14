package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

/**
 * @file View.java
 * @author bruno
 * @date 04-12-2025
 * @version 1.1
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.*;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @class View
 * @brief Gestisce l'interfaccia grafica e la navigazione tra le schermate.
 */
public class View extends Application {
    
    private static Stage mainStage;
    
    // Inizializzazione STATICA dei dati
    private static UserManagement userManagement = new UserManagement();
    private static BookManagement bookManagement = new BookManagement();
    private static LoanManagement loanManagement = new LoanManagement();
    
    // Il Modello condiviso
    private static Model model = new Model(bookManagement, userManagement, loanManagement);

    /**
     * @brief Metodo di avvio dell'applicazione.
     */
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Login");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void Homepage() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        // Ottengo il controller (se serve)
        homepageController controller = fxmlLoader.getController();
        
        mainStage.setTitle("HomePage");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    // Aggiungi questo alla classe View.java
    public static void Login() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Login");
        mainStage.setScene(scene);
        
        // Full Screen Logic: Ecco le righe che forzano l'ingrandimento quando TORNI indietro
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void Modify() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modifyPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        modifycredentialController controller = fxmlLoader.getController();
        
        mainStage.setTitle("Modifica Credenziali");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    // --- GESTIONE UTENTI ---

    public static void addUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/aggiungiutente.fxml"));
        Parent root = fxmlLoader.load();
        
        addUserController controller = fxmlLoader.getController();
        controller.setModel(model); 
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Aggiungi Utente");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
     
    public static void modifyUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificautente.fxml"));
        Parent root = fxmlLoader.load();
        
        modifyUserController controller = fxmlLoader.getController();
        controller.setModel(model); 
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Modifica Utente");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
     
    public static void deleteUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/cancellautente.fxml"));
        Parent root = fxmlLoader.load();
        
        deleteUserController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Elimina Utente");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
     
    public static void searchUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/ricercautente.fxml"));
        Parent root = fxmlLoader.load();
        
        searchUserController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Ricerca Utente");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void viewUSers() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencoutente.fxml"));
        Parent root = fxmlLoader.load();
        
        viewUserController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Visualizza Elenco Utenti");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    // --- GESTIONE LIBRI ---

    public static void addBook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/aggiungilibro.fxml"));
        Parent root = fxmlLoader.load();
        
        addBookController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Aggiungi Libro");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
     
    public static void deleteBook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/cancellalibro.fxml"));
        Parent root = fxmlLoader.load();
        
        deleteBookController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Cancella Libro");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void updateBook(Book bookToModify) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificalibro.fxml"));
        Parent root = fxmlLoader.load();

        modifyBookController controller = fxmlLoader.getController();
        if (controller != null) {
            controller.initData(bookToModify); 
        }

        Scene scene = new Scene(root);
        
        mainStage.setTitle("Modifica Libro");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void updateBook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificalibro.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Modifica Libro");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void viewBooks() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencolibro.fxml"));
        Parent root = fxmlLoader.load();
        
        viewBookController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Visualizza Elenco Libri");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void searchBook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/ricercalibro.fxml"));
        Parent root = fxmlLoader.load();
        
        searchBookController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Cerca Libro");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    // --- GESTIONE PRESTITI ---

    public static void searchLoan() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/cercaprestito.fxml"));
        Parent root = fxmlLoader.load();
        
        searchLoanController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Cerca Prestito");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void addLoan() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/aggiungiprestito.fxml"));
        Parent root = fxmlLoader.load();
        
        addLoanController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Aggiungi Prestito");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void deleteLoan() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/eliminaprestito.fxml"));
        Parent root = fxmlLoader.load();
        
        deleteLoanController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Elimina Prestito");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }
    
    public static void viewLoan() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencoprestiti.fxml"));
        Parent root = fxmlLoader.load();
        
        loanViewController controller = fxmlLoader.getController();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        
        mainStage.setTitle("Visualizza Elenco Prestiti");
        mainStage.setScene(scene);
        
        // Full Screen Logic
        mainStage.setResizable(true);
        mainStage.setMaximized(false);
        mainStage.setMaximized(true);
        
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}