/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file deleteUserController.java
 * @brief Controller per la gestione della cancellazione degli utenti.
 * 
 * La classe `deleteUserController` gestisce l'interfaccia utente per la ricerca
 * e la cancellazione degli utenti. Consente di cercare un utente per nome, cognome 
 * o matricola, visualizzando i risultati in una `TableView`. Se l'utente è selezionato, 
 * il bottone di eliminazione viene abilitato per procedere con la cancellazione.
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @class deleteUserController
 * @brief Gestisce la ricerca e la cancellazione degli utenti.
 * 
 * La classe `deleteUserController` si occupa della gestione dell'interfaccia 
 * utente per la ricerca e l'eliminazione di un utente dalla lista degli utenti.
 * Consente al bibliotecario di cercare un utente in base al nome, cognome o 
 * matricola e di eliminarlo dal sistema.
 */
public class deleteUserController {

    /**
     * Campo di testo per la ricerca dell'utente.
     */
    @FXML
    private TextField searchField;

    /**
     * Pulsante per avviare la ricerca dell'utente.
     */
    @FXML
    private Button searchButtonu;

    /**
     * Pulsante per eliminare l'utente selezionato.
     */
    @FXML
    private Button deleteuserButton;

    /**
     * Pulsante per tornare alla pagina precedente.
     */
    @FXML
    private Button backButton;

    /**
     * Tabella che visualizza i risultati della ricerca degli utenti.
     */
    @FXML
    private TableView<User> userTableViewricerca;

    /**
     * Colonna per visualizzare il nome dell'utente.
     */
    @FXML
    private TableColumn<User, String> nomeColumn;

    /**
     * Colonna per visualizzare il cognome dell'utente.
     */
    @FXML
    private TableColumn<User, String> cognomeColumn;

    /**
     * Colonna per visualizzare la matricola dell'utente.
     */
    @FXML
    private TableColumn<User, String> matricolaColumn;

    /**
     * Etichetta per visualizzare messaggi di errore o conferma.
     */
    @FXML
    private Label labelMessage;

    /**
     * Riferimento al modello dell'applicazione che gestisce i dati.
     */
    private Model model;

    /**
     * Imposta il modello dell'applicazione.
     * 
     * @param model Il modello dell'applicazione da impostare.
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Metodo chiamato all'inizializzazione del controller.
     * 
     * Configura la visibilità dei pulsanti in base allo stato della ricerca
     * e abilita il pulsante di eliminazione solo quando un utente è selezionato.
     * Configura anche le colonne della `TableView` per mostrare le informazioni dell'utente.
     */
    @FXML
    public void initialize() {
        searchButtonu.disableProperty().bind(searchField.textProperty().isEmpty());
        deleteuserButton.setDisable(true);

        // Configurazione delle colonne della TableView
        nomeColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getName())
        );
        cognomeColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getSurname())
        );
        matricolaColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNumberId())
        );

        // Abilita il pulsante di eliminazione solo se un utente è selezionato
        userTableViewricerca.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            deleteuserButton.setDisable(newSel == null);
            if (newSel != null) {
                labelMessage.setText("Utente selezionato");
                labelMessage.setStyle("-fx-text-fill: #374151;");
            }
        });
    }

    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante di ricerca.
     * 
     * Esegue la ricerca dell'utente in base al valore inserito nel campo di ricerca.
     * Se non vengono trovati risultati, visualizza un messaggio di errore.
     * 
     * @post I risultati della ricerca vengono visualizzati nella `TableView`.
     */
    @FXML
    public void search() {
        labelMessage.setText("");

        // Ottieni l'input di ricerca
        String input = searchField.getText();

        // Crea un oggetto User fittizio per la ricerca
        User userSonda = new User(input);

        // Esegui la ricerca
        List<User> risultati = model.getUserManagement().search(userSonda);
        
        // Aggiorna la TableView con i risultati
        ObservableList<User> datiTabella = FXCollections.observableArrayList(risultati);
        userTableViewricerca.setItems(datiTabella);
        userTableViewricerca.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Se non vengono trovati utenti, mostra un messaggio di errore
        if (risultati.isEmpty()) {
            deleteuserButton.setDisable(true);
            userTableViewricerca.getItems().clear();
            labelMessage.setText("Nessun utente trovato");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }
    }

    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante di eliminazione.
     * 
     * Elimina l'utente selezionato dalla `TableView` e aggiorna il modello.
     * Se l'utente non è selezionato o c'è un errore, mostra un messaggio di errore.
     * 
     * @post L'utente viene eliminato dal sistema e dalla `TableView`.
     */
    @FXML
    public void delete() {
        User selectedUser = userTableViewricerca.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            labelMessage.setText("Nessun utente selezionato");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        // Pulizia eventuale degli spazi
        selectedUser.setNumberId(selectedUser.getNumberId().trim());

        // Rimuovi l'utente dal modello
        boolean removed = model.getUserManagement().remove(selectedUser);

        if (removed) {
            // Rimuovi l'utente dalla TableView e disabilita il pulsante di eliminazione
            userTableViewricerca.getItems().remove(selectedUser);
            deleteuserButton.setDisable(true);
            labelMessage.setText("Utente eliminato correttamente");
            labelMessage.setStyle("-fx-text-fill: green;");
        } else {
            labelMessage.setText("Errore durante l'eliminazione");
            labelMessage.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante "Indietro".
     * Torna alla pagina della homepage.
     * 
     * @throws IOException Se c'è un errore nel caricamento della homepage.
     */
    @FXML
    public void backPage() throws IOException {
        View.Homepage();
    }
}