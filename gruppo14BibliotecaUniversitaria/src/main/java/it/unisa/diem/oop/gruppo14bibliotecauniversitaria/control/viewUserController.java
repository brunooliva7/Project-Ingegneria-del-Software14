/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file viewUserController.java
 * @brief Controller per visualizzare l'elenco degli utenti e i dettagli relativi.
 * 
 * La classe `viewUserController` gestisce l'interfaccia grafica per visualizzare l'elenco degli utenti
 * e le informazioni associate, tra cui il nome, il cognome, la matricola, l'email e i prestiti attivi.
 * I dati sono visualizzati in una `TableView`, e gli utenti sono ordinati in base al loro cognome e nome.
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @class viewUserController
 * @brief Gestisce la visualizzazione dell'elenco degli utenti.
 * 
 * La classe `viewUserController` gestisce la visualizzazione degli utenti in una `TableView`, che
 * mostra le informazioni di base degli utenti (nome, cognome, matricola, email) e i prestiti attivi.
 * Inoltre, il controller interagisce con il modello per ottenere i dati da visualizzare.
 */
public class viewUserController {
    
    /**
     * La TableView che contiene la lista degli utenti.
     */
    @FXML
    private TableView<User> userTableView;
   
    /**
     * Colonna della TableView per visualizzare il nome dell'utente.
     */
    @FXML
    private TableColumn<User, String> nomeColumn;

    /**
     * Colonna della TableView per visualizzare il cognome dell'utente.
     */
    @FXML
    private TableColumn<User, String> cognomeColumn;

    /**
     * Colonna della TableView per visualizzare la matricola dell'utente.
     */
    @FXML
    private TableColumn<User, String> matricolaColumn;

    /**
     * Colonna della TableView per visualizzare l'email dell'utente.
     */
    @FXML
    private TableColumn<User, String> emailColumn;

    /**
     * Colonna della TableView per visualizzare i prestiti attivi dell'utente.
     */
    @FXML
    private TableColumn<User, String> mapColumn;
    
    /**
     * Pulsante per tornare alla homepage.
     */
    @FXML
    private Button backButton;
    
    /**
     * Modello dell'applicazione che gestisce i dati.
     */
    private Model model;
      
    /**
     * Imposta il modello dell'applicazione.
     * 
     * @param model Il modello dell'applicazione da impostare.
     */
    public void setModel(Model model) {
        this.model = model;
        
        // Crea una lista osservabile contenente gli utenti ordinati
        ObservableList<User> listaDati = FXCollections.observableArrayList(model.getUserManagement().getList().stream().sorted().collect(Collectors.toList()));
        
        // Imposta la lista nella TableView
        userTableView.setItems(listaDati);
        userTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * Metodo chiamato all'inizializzazione del controller.
     * Configura le colonne della `TableView` per visualizzare correttamente i dati degli utenti.
     * Inoltre, imposta l'ordinamento naturale della lista di utenti.
     * 
     * @post Le colonne della `TableView` sono configurate per visualizzare le informazioni degli utenti.
     */
    @FXML
    public void initialize(){
        
        // Configura le colonne della TableView
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Visualizza la mappa dei prestiti come stringa
        mapColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            if (user.getBooksOnloan() != null && !user.getBooksOnloan().isEmpty()) {
                String prestiti = user.getBooksOnloan().entrySet().stream()
                        .map(e -> "ISBN libro:" + e.getKey().getISBN() + " -> Data scadenza:" + e.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .collect(Collectors.joining(", "));
                return new SimpleStringProperty(prestiti);
            } else {
                return new SimpleStringProperty("");
            }
        });
        
        // Rimuove eventuali ordinamenti esistenti sulla TableView
        userTableView.getSortOrder().clear(); 
    }
    
    /**
     * Metodo chiamato quando il bibliotecario clicca sul pulsante "Indietro".
     * Torna alla homepage dell'applicazione.
     * 
     * @throws IOException Se c'è un errore nel caricamento della homepage.
     */
    @FXML
    public void backPage() throws IOException{
        View.Homepage();
    }
}