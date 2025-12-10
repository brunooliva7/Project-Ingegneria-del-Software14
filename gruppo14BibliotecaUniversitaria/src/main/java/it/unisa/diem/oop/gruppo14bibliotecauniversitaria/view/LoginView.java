package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private final MainController controller;

    public LoginView(MainController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        Label lblUser = new Label("Username:");
        TextField txtUser = new TextField();
        txtUser.setPromptText("Inserisci Username");

        Label lblPass = new Label("Password:");
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Inserisci Password");

        Button btnLogin = new Button("Conferma");
        Button btnModifica = new Button("Modifica Credenziali");

        HBox buttonBox = new HBox(20, btnLogin, btnModifica);
        buttonBox.setAlignment(Pos.CENTER);

        VBox loginLayout = new VBox(15, lblUser, txtUser, lblPass, txtPass, buttonBox);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle(
                "-fx-padding: 30;" +
                "-fx-background-color: linear-gradient(to bottom, #e0f7ff, #d6eaf8);" +
                "-fx-font-size: 14px;"
        );

        Scene loginScene = new Scene(loginLayout, 350, 250);
        stage.setTitle("Autenticazione");
        stage.setScene(loginScene);
        stage.show();

        // --- Azioni delegate al Controller ---
        btnLogin.setOnAction(e -> controller.handleLogin(stage));
        btnModifica.setOnAction(e -> controller.handleModificaCredenziali());
    }
}