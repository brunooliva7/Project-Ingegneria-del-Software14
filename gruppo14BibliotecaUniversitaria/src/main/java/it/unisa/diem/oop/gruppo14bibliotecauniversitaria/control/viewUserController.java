/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

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
    
    UserManagement userManagemente;
    
    @FXML
    private TableView<User> userTableView;
   
   @FXML
    private TableColumn<User, String> nomeColumn;

    @FXML
    private TableColumn<User, String> cognomeColumn;

    @FXML
    private TableColumn<User, String> matricolaColumn;
    
    @FXML
    private Button backButton;
    
    @FXML
    public void initialize(){
        
        this.userManagemente = new UserManagement();
        
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("numberId"));
        
        ObservableList<User> listaDati = FXCollections.observableArrayList(this.userManagemente.getList());
        
        userTableView.setItems(listaDati);
       
    }
    
      @FXML
    public void backPage() throws IOException{
        View.Homepage();
    } 

    
}
