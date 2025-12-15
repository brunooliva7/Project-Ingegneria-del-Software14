package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

/**
 * @file View.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.LoginController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.addBookController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.addLoanController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.addUserController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.deleteBookController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.deleteLoanController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.deleteUserController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.homepageController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.loanViewController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.modifyBookController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.modifyUserController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.modifycredentialController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.searchBookController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.searchLoanController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.searchUserController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.viewBookController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.viewUserController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
/**
 * JavaFX App
 */
/**
 * @class View
 * @brief Classe che rappresenta la vista dell'applicazione 
 *
 * La classe View estende `Application` di JavaFX e si occupa della costruzione
 * e visualizzazione della GUI. 
 *
 * La View è responsabile esclusivamente dell'interfaccia grafica: non contiene
 * logica applicativa, che è invece gestita dal Controller e dal Model,al suo interno abbiamo semplicemente tutti gli elementi grafici che ci serveranno per realizzare le interfacce utente
 * Segue il pattern MVC
 */
public class View extends Application {
    private static Model model; // variabile statica del modello
    private static Stage mainStage;
    /** 
     * @brief Metodo di avvio dell'applicazione JavaFX.
     *
     * Questo metodo viene invocato dal runtime JavaFX al momento della creazione
     * della GUI. Inizializza gli elementi grafici, imposta la scena e mostra lo stage.
     *
     * @param stage La finestra principale dell'applicazione JavaFX
     * @post Lo stage principale viene configurato e mostrato a schermo
     */
    
        
        
     @Override
    public void start(Stage stage) throws IOException {

        mainStage=stage;
        model=new Model(new BookManagement(),new UserManagement(),new LoanManagement());
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/login.fxml"));
          // Passaggio del modello al controller
         LoginController controller = fxmlLoader.getController();
         controller.setModel(model);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();   
        
        stage.setMaximized(true);
        
    }
    
    public static void Homepage() throws IOException{
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/homepage.fxml"));
            // Ottengo il controller per la homepage e passo il modello
        homepageController controller = fxmlLoader.getController();
        controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("HomePage!");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
    }
    
    
    public static void Modify() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modifyPage.fxml"));
             // Ottengo il controller e passo il modello
        modifycredentialController controller = fxmlLoader.getController();
        controller.setModel(model);  // Usa il modello già creato in start()
        
        
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Modifica Credenziali!");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
    }
    
   
     public static void addUser() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/aggiungiutente.fxml"));
            // Ottengo il controller  e passo il modello
           addUserController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Aggiungi utente");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
     
     public static void modifyUser() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificautente.fxml"));
        // Ottengo il controller  e passo il modello
           modifyUserController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Aggiungi utente");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
    }
     
     public static void deleteUser() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/cancellautente.fxml"));
        // Ottengo il controller  e passo il modello
           deleteUserController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("EliminaUtente");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
    }
     
     public static void searchUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/ricercautente.fxml"));
        // Ottengo il controller  e passo il modello
           searchUserController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("RicercaUtente");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show(); 
        
  }
    
    public static void viewUSers() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencoutente.fxml"));
        // Ottengo il controller  e passo il modello
           viewUserController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("VisualizzaUtenti");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
    }
    
    public static void addBook() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/aggiungilibro.fxml"));
        // Ottengo il controller  e passo il modello
           addBookController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Aggiungi Libro");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
     
    public static void deleteBook() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/cancellalibro.fxml"));
        // Ottengo il controller  e passo il modello
           deleteBookController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Cancella Libro");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void updateBook() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificalibro.fxml"));
        // Ottengo il controller  e passo il modello
           modifyBookController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Modifica Libro");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void viewBooks() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencolibro.fxml"));
        // Ottengo il controller  e passo il modello
           viewBookController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Visualizza Libri");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void searchBook() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/ricercalibro.fxml"));
        // Ottengo il controller  e passo il modello
           searchBookController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Cerca libro");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void searchLoan() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/cercaprestito.fxml"));
        // Ottengo il controller  e passo il modello
           searchLoanController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Cerca Prestito");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void addLoan() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/aggiungiprestito.fxml"));
        // Ottengo il controller  e passo il modello
           addLoanController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Aggiungi Prestito");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void deleteLoan() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/eliminaprestito.fxml"));
        // Ottengo il controller  e passo il modello
           deleteLoanController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Elimino Prestito");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void viewLoan() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencoprestiti.fxml"));
        // Ottengo il controller  e passo il modello
           loanViewController controller = fxmlLoader.getController();
           controller.setModel(model);  // Usa il modello già creato in start()
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Visualizza Prestiti");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }

    /**
     * @brief Metodo main dell'applicazione.
     *
     * Avvia l'applicazione JavaFX invocando `launch()`, che si occupa di creare
     * lo stage principale e di chiamare automaticamente il metodo `start()`.
     *
     * @param args Argomenti da riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
