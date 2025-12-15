/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author elisa
 */
public class deleteUserController {
    
     @FXML
    private TextField searchField;
    
     @FXML
    private Button searchButtonu;
     
     @FXML
     private Button deleteuserButton;
     
    @FXML
    private Button backButton;
    
    @FXML
    private TableView<User> userTableViewricerca;

    @FXML
    private TableColumn<User, String> nomeColumn;

    @FXML
    private TableColumn<User, String> cognomeColumn;

    @FXML
    private TableColumn<User, String> matricolaColumn;
    @FXML
    private Label labelMessage;

    private Model model;
      
    
    public void setModel(Model model) {
    this.model = model;
    }
   
     @FXML
     public void initialize(){
         searchButtonu.disableProperty().bind(searchField.textProperty().isEmpty());
         
         deleteuserButton.setDisable(true);
         
         
         // Configurazione colonne
        nomeColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getName())
        );
        cognomeColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getSurname())
        );
        matricolaColumn.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getNumberId())
        );

        // Abilita bottone di elimina solo se selezioni una riga
        userTableViewricerca.getSelectionModel() .selectedItemProperty().addListener((obs, oldSel, newSel) -> {
                    deleteuserButton.setDisable(newSel == null);
            if (newSel != null) {
                        labelMessage.setText("Utente selezionato");
                        labelMessage.setStyle("-fx-text-fill: #374151;");
                    }        
                });
     }
     
     @FXML
     public void search(){
          
        labelMessage.setText("");
         
           //assegno a input la stringa che usa il bibliotecario per cercare l'user 
         String input = searchField.getText();
        
         User userSonda = new User(input); //usando il secondo costruttore creo un nuovo utente fittizio che mi serve solo da passare al metodo search()
        
        List<User> risultati = model.getUserManagement().search(userSonda);
        //l'observable list mi serve per "aggiornare" l'interfaccia in modo live rispetto ai risultati ottenuti dalla search()
        ObservableList<User> datiTabella = FXCollections.observableArrayList(risultati);
        //aggiorno la tableView con i dati contenuti nella ObservableList 
        userTableViewricerca.setItems(datiTabella);

        // Se non trovo nulla, svuoto selezione,rimango disabilitato il bottone di elimina e faccio comparire una label che notifica l'assenza di studenti
        if (risultati.isEmpty()) {
            deleteuserButton.setDisable(true);
            userTableViewricerca.getItems().clear();
            labelMessage.setText("Nessun utente trovato");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }
       
    }
     
     @FXML
     public void delete(){
        User selectedUser = userTableViewricerca.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            labelMessage.setText("Nessun utente selezionato");
            labelMessage.setStyle("-fx-text-fill: red;");
            return;
        }
            // Pulizia eventuale degli spazi
        selectedUser.setNumberId(selectedUser.getNumberId().trim());
        

        boolean removed = model.getUserManagement().remove(selectedUser);

        if (removed) {
            userTableViewricerca.getItems().remove(selectedUser);
            deleteuserButton.setDisable(true);
            labelMessage.setText("Utente eliminato Correttamente");
            labelMessage.setStyle("-fx-text-fill: green;");
        }
        else {
              labelMessage.setText("Errore durante l'eliminazione");
            labelMessage.setStyle("-fx-text-fill: red;");
        }
         
        
     }
     
     @FXML
    public void backPage() throws IOException{
        View.Homepage();
    } 
     
}
