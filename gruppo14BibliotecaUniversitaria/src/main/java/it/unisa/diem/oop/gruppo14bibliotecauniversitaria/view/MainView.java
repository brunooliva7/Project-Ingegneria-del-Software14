package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView {

    private BorderPane mainLayout;
    private final MainController controller;

    public MainView(MainController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        MenuButton menuUtente = new MenuButton("Utente");
        MenuItem aggiungiUtente = new MenuItem("Aggiungi");
        menuUtente.getItems().addAll(
                aggiungiUtente,
                new MenuItem("Modifica"),
                new MenuItem("Cancella"),
                new MenuItem("Cerca")
        );

        MenuButton menuPrestito = new MenuButton("Prestito");
        menuPrestito.getItems().addAll(
                new MenuItem("Aggiungi"),
                new MenuItem("Modifica"),
                new MenuItem("Cancella"),
                new MenuItem("Cerca")
        );

        MenuButton menuLibro = new MenuButton("Libro");
        menuLibro.getItems().addAll(
                new MenuItem("Aggiungi"),
                new MenuItem("Modifica"),
                new MenuItem("Cancella"),
                new MenuItem("Cerca")
        );

        HBox menuBox = new HBox(15, menuUtente, menuPrestito, menuLibro);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle(
                "-fx-background-color: linear-gradient(to right, #aed6f1, #d6eaf8);" +
                "-fx-padding: 15;"
        );

        Label lblInfo = new Label("Selezionare l'operazione da compiere");
        lblInfo.setFont(new Font("Arial", 18));
        lblInfo.setTextFill(Color.LIGHTGRAY);
        lblInfo.setAlignment(Pos.CENTER);

        mainLayout = new BorderPane();
        mainLayout.setTop(menuBox);
        mainLayout.setCenter(lblInfo);
        mainLayout.setStyle("-fx-background-color: #eaf6fb;");

        Scene mainScene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Gestione Biblioteca");
        stage.setScene(mainScene);
        stage.show();

        // --- Azioni delegate al Controller ---
        aggiungiUtente.setOnAction(e -> controller.mostraFormUtente(mainLayout));
    }
}