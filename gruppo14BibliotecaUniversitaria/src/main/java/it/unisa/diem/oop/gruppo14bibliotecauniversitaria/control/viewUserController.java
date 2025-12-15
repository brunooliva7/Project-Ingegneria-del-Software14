/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author elisa
 */
public class viewUserController {
    
 
    
    @FXML
    private TableView<User> userTableView;
   
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
    
    private Model model;
      
    
    public void setModel(Model model) {
    this.model = model;
    }
    
    @FXML
    public void initialize(){
        
        
        
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
        
        
        
        
        
        ObservableList<User> listaDati = FXCollections.observableArrayList(model.getUserManagement().getList().stream().sorted().collect(Collectors.toList()));
            
        userTableView.setItems(listaDati);
        userTableView.getSortOrder().clear(); // rimuovi eventuali ordinamenti così che la tableView rispetta l'ordina naturale della lista 
       
    }
    
      @FXML
    public void backPage() throws IOException{
        View.Homepage();
    } 

    
}
