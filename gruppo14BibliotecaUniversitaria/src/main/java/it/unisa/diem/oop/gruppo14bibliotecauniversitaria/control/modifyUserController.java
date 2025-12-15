/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author bruno
 */
public class modifyUserController {
    
    @FXML
    private AnchorPane searchPane;
    
    @FXML
    private AnchorPane modifyPane;
    
    @FXML
    private VBox mainContainer;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField surnameField;
    
     @FXML
    private TextField emailField;
     
     @FXML
    private TextField numberidField;
     
     @FXML
    private TextField searchField;
     
     @FXML
    private Button searchButton;
     
     @FXML
    private Button confirmButton;
     
     @FXML
     private Button prestititenteButton;
     
     @FXML
     private Label labelMessageModify;
     
     @FXML
     private Label labelMessageSearch;
     
     @FXML
    private TableView<User> userTableView;
   
   @FXML
    private TableColumn<User, String> nomeColumn;

    @FXML
    private TableColumn<User, String> cognomeColumn;

    @FXML
    private TableColumn<User, String> matricolaColumn;
     
     UserManagement userManagement;
     private User risultato = null;
     
     List <User> list;
     private ObservableList<User> observableList;
     
     @FXML
     public void initialize(){
         this.userManagement = new UserManagement();
         
         modifyPane.setVisible(false);
         modifyPane.setManaged(false);
         
         searchPane.setVisible(true);
         searchPane.setManaged(true);
         
         
         nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        // ObservableList persistente
        observableList = FXCollections.observableArrayList();
        userTableView.setItems(observableList);
         
        searchButton.disableProperty().bind(searchField.textProperty().isEmpty());  
         
         list = new ArrayList<>();
         
        
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,newValue) -> {
            if(newValue != null){
                gestisciSelezione(newValue);
            }
        });
        
        userTableView.getSelectionModel().clearSelection();
     }
     
     @FXML
     public void search() {
       
    
    String input = searchField.getText();
    User userSonda = new User(input);
    this.list = userManagement.search(userSonda);

    if (!list.isEmpty()) {
       
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        
        observableList= FXCollections.observableArrayList(this.list);
        
         userTableView.setItems(observableList);
    } else {
        observableList.clear();
        labelMessageSearch.setText("Utente non trovato.");
        labelMessageSearch.setStyle("-fx-text-fill: red;");
    }
}   
     private void gestisciSelezione(User userSelezionato){
         
         this.risultato = userSelezionato;
         
        nameField.setText(risultato.getName());
        surnameField.setText(risultato.getSurname());
        emailField.setText(risultato.getEmail());
        numberidField.setText(risultato.getNumberId());
        
         modifyPane.setVisible(true);
         modifyPane.setManaged(true);
         
         searchPane.setVisible(false);
         searchPane.setManaged(false);     
     }

     
     @FXML 
     public void confirm(){
          User newUser = new User(nameField.getText(),surnameField.getText(),numberidField.getText(),emailField.getText());
            
            if(userManagement.update(risultato, newUser)){
                labelMessageModify.setText("Modifica Riuscita");
                labelMessageModify.setStyle("-fx-text-fill: green;");
                  // Aggiorno la TableView in automatico
            int index = observableList.indexOf(risultato);
            if (index >= 0) {
                observableList.set(index, newUser);
            }

            risultato = newUser; // aggiorno riferimento
            }
            
            else{ labelMessageModify.setText("Modifica non riuscita");
            labelMessageModify.setStyle("-fx-text-fill: red;");
            }
     }
     
      @FXML
    public void backHomepage() throws IOException{
        View.Homepage();
    } 
    
    @FXML
    public void SearchPage() throws IOException{
        modifyPane.setVisible(false);
         modifyPane.setManaged(false);
         
         searchPane.setVisible(true);
         searchPane.setManaged(true);
    } 
}
