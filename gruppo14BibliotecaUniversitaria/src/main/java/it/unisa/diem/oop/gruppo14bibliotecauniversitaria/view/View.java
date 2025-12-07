package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

/**
 * @file View.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
    public void start(Stage stage) {

        Label label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
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
        launch();
    }

}