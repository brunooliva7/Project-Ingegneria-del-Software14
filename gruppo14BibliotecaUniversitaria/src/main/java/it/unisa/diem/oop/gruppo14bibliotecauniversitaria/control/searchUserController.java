/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author elisa
 */
public class searchUserController {
    
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButtonu;
    @FXML
    private Label labelMessage;
    @FXML
    private TableView<User> userTableViewricerca;
    @FXML
    private TableColumn<User, String> nomeColumn;
    @FXML
    private TableColumn<User, String> cognomeColumn;
    @FXML
    private TableColumn<User, String> matricolaColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> mapColumn;
    @FXML
    private Button backButton;

    private UserManagement userManagement;
    private ObservableList<User> userList;
    
    @FXML
    public void initialize(){
        this.userManagement=new UserManagement();
        
        searchButtonu.disableProperty().bind(searchField.textProperty().isEmpty());
        
       // imposto le colonne della TableView usando i nomi delle proprietà come stringa 
         nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
         cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
         matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
         emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
         // visualizzare la mappa dei prestiti come stringa
        mapColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            if (user.getBooksOnloan() != null && !user.getBooksOnloan().isEmpty()) {
                String prestiti = user.getBooksOnloan().entrySet().stream() //scorro tutte le coppie libro->data della collezione di prestiti dell'User
                        .map(e -> "ISBN libro:"+e.getKey().getISBN() + " -> Data scadenza:" + e.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .collect(Collectors.joining(", "));
                return new SimpleStringProperty(prestiti);
            } else {
                return new SimpleStringProperty("");
            }
        });
       
       userList=FXCollections.observableArrayList(); //lista iniziale vuota
       //la tableView è"live" e aggiornata quando cambia la lista
       userTableViewricerca.setItems(userList);
        // Azione pulsante ricerca
        searchButtonu.setOnAction(e -> search());
    }
    
    @FXML
    public void search(){
          labelMessage.setText("");

    String input = searchField.getText().trim();

    if (input.isEmpty()) {
        userTableViewricerca.getItems().clear();
        return;
    }

    User userSonda = new User(input); // costruttore per ricerca
    List<User> risultati = userManagement.search(userSonda);

    ObservableList<User> datiTabella = FXCollections.observableArrayList(risultati);
    userTableViewricerca.setItems(datiTabella);

    if (risultati.isEmpty()) {
        labelMessage.setText("Nessun utente trovato");
        labelMessage.setStyle("-fx-text-fill: red;");
    } else {
        labelMessage.setText(""); // pulisco messaggio se ci sono risultati
    }
        }
    }