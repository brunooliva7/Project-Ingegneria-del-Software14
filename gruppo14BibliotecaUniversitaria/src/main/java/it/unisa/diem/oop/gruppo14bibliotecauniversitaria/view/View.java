package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

/**
 * @file View.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */

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
        mainStage.show();  
        
  
        mainStage.setMaximized(true);
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