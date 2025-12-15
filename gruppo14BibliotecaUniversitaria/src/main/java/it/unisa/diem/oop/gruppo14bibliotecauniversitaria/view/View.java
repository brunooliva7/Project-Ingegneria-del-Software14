package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

/**
 * @file View.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control.modifyBookController;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
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
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/login.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();   
        
        stage.setMaximized(true);
        
    }
    
    public static void Homepage() throws IOException{
        
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/homepage.fxml"));
        
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
        
        Scene scene = new Scene(fxmlLoader.load());
        
        mainStage.setTitle("Cancella Libro");
        mainStage.setScene(scene);
        
        mainStage.setResizable(true);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
        
        mainStage.show();  
        
    }
    
    public static void updateBook(Book bookToModify) throws IOException {
    
        // 1. Crea il loader e carica il file FXML
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificalibro.fxml"));
        Parent root = fxmlLoader.load(); // Carichiamo il Parent per poter accedere al controller

        // 2. Ottieni il controller DOPO che l'FXML è stato caricato
        modifyBookController controller = fxmlLoader.getController();

        // 3. INIEZIONE DEL DATO: Chiama il metodo initData del controller
        if (controller != null) {
            controller.initData(bookToModify); 
        }

        // 4. Crea la Scene con il Parent caricato
        Scene scene = new Scene(root);

        // 5. Configurazione dello Stage (la tua logica originale)
        mainStage.setTitle("Modifica Libro");
        mainStage.setScene(scene);

        // NOTA: Questa riga è ridondante se l'ultima è true
        // mainStage.setResizable(true); 
        // mainStage.setMaximized(false); 
        mainStage.setMaximized(true); // Mantiene la finestra massimizzata (come da tuo codice)

        mainStage.show();
    }
        public static void updateBook() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/modificalibro.fxml"));
        Parent root = fxmlLoader.load();

        // Non otteniamo il controller né chiamiamo initData(), perché non abbiamo dati da iniettare.

        Scene scene = new Scene(root);

        // (Ripristina la tua logica di visualizzazione originale)
        mainStage.setTitle("Modifica Libro");
        mainStage.setScene(scene);
        mainStage.setMaximized(true); 
        mainStage.show();
}
    
    public static void viewBooks() throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/visualizzaelencolibro.fxml"));
        
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
