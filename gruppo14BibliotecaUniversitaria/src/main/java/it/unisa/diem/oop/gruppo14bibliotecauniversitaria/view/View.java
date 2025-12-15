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
 *
 * La classe View estende Application di JavaFX. Si occupa di inizializzare lo Stage principale,
 * istanziare il Modello (Model) e le classi di gestione (Management), e fornisce metodi statici
 * per caricare e mostrare le diverse scene (FXML) dell'applicazione, passando il modello ai relativi controller.
 */
public class View extends Application {
    
    /** @brief Riferimento allo Stage principale dell'applicazione. */
    private static Stage mainStage;
    
    // Inizializzazione STATICA dei dati
    private static UserManagement userManagement = new UserManagement();
    private static BookManagement bookManagement = new BookManagement();
    private static LoanManagement loanManagement = new LoanManagement();
    
   /** @brief Istanza condivisa del Model passata ai controller per l'accesso ai dati. */
    private static Model model = new Model(bookManagement, userManagement, loanManagement);

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     * * Carica la schermata di login iniziale e imposta le proprietà della finestra.
     * * @param stage Lo stage primario fornito dalla piattaforma JavaFX.
     * @throws IOException Se il file FXML di login non viene trovato.
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
    
    
    /**
     * @brief Carica e mostra la Homepage dell'applicazione.
     * * @throws IOException Se il file homepage.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica e mostra la schermata di Login.
     * * Utile per tornare alla schermata di accesso dalle altre viste.
     * Ripristina la visualizzazione a schermo intero.
     * * @throws IOException Se il file login.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per la modifica delle credenziali del bibliotecario.
     * * @throws IOException Se il file modifyPage.fxml non viene trovato.
     */
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
    /**
     * @brief Carica la schermata per l'aggiunta di un nuovo utente.
     * * Inietta il modello nel controller `addUserController`.
     * @throws IOException Se il file aggiungiutente.fxml non viene trovato.
     */
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
    /**
     * @brief Carica la schermata per la modifica di un utente esistente.
     * * Inietta il modello nel controller `modifyUserController`.
     * @throws IOException Se il file modificautente.fxml non viene trovato.
     */ 
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
     
    /**
     * @brief Carica la schermata per l'eliminazione di un utente.
     * * Inietta il modello nel controller `deleteUserController`.
     * @throws IOException Se il file cancellautente.fxml non viene trovato.
     */
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
     
    /**
     * @brief Carica la schermata per la ricerca degli utenti.
     * * Inietta il modello nel controller `searchUserController`.
     * @throws IOException Se il file ricercautente.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per visualizzare l'elenco completo degli utenti.
     * * Inietta il modello nel controller `viewUserController`.
     * @throws IOException Se il file visualizzaelencoutente.fxml non viene trovato.
     */
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
    /**
     * @brief Carica la schermata per aggiungere un nuovo libro.
     * * Inietta il modello nel controller `addBookController`.
     * @throws IOException Se il file aggiungilibro.fxml non viene trovato.
     */
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
     /**
     * @brief Carica la schermata per eliminare un libro.
     * * Inietta il modello nel controller `deleteBookController`.
     * @throws IOException Se il file cancellalibro.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per modificare un libro specifico.
     * * Passa i dati del libro da modificare al controller per pre-popolare i campi.
     * * @param bookToModify L'oggetto Book che si intende modificare.
     * @throws IOException Se il file modificalibro.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata di modifica libro senza un libro pre-selezionato.
     * * @throws IOException Se il file modificalibro.fxml non viene trovato.
     */
    
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
    
    /**
     * @brief Carica la schermata per visualizzare l'elenco dei libri.
     * * Inietta il modello nel controller `viewBookController`.
     * @throws IOException Se il file visualizzaelencolibro.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per la ricerca di libri.
     * * Inietta il modello nel controller `searchBookController`.
     * @throws IOException Se il file ricercalibro.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per la ricerca dei prestiti.
     * * Inietta il modello nel controller `searchLoanController`.
     * @throws IOException Se il file cercaprestito.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per l'aggiunta di un prestito.
     * * Inietta il modello nel controller `addLoanController`.
     * @throws IOException Se il file aggiungiprestito.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per l'eliminazione di un prestito.
     * * Inietta il modello nel controller `deleteLoanController`.
     * @throws IOException Se il file eliminaprestito.fxml non viene trovato.
     */
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
    
    /**
     * @brief Carica la schermata per visualizzare l'elenco dei prestiti.
     * * Inietta il modello nel controller `loanViewController`.
     * @throws IOException Se il file visualizzaelencoprestiti.fxml non viene trovato.
     */
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

    /**
     * @brief Metodo main dell'applicazione.
     * * Lancia l'applicazione JavaFX.
     * @param args Argomenti da riga di comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}