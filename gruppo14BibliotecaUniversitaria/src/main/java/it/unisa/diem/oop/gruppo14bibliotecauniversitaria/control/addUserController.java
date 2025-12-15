/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;

/**
 *
 * @author elisa
 */
public class addUserController {
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField surname;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField numberID;
    
    @FXML
    private Button addButton;
    
    @FXML 
    private Label labelErrore;
    
    @FXML
    private Button backButton;
    
    private Model model;
    
    public void setModel(Model model) {
    this.model = model;
    }
    
    @FXML
    public void initialize(){
      
        numberID.disableProperty().bind(email.textProperty().isEmpty().or(surname.textProperty().isEmpty().or(name.textProperty().isEmpty())));
        surname.disableProperty().bind(name.textProperty().isEmpty());
        email.disableProperty().bind(name.textProperty().isEmpty().or(surname.textProperty().isEmpty()));   
        addButton.disableProperty().bind(numberID.textProperty().isEmpty());
    }
    
    @FXML
    public void addUser(){
        String nome = name.getText();
        String cognome = surname.getText();
        String mail = email.getText();
        String matricola = numberID.getText();
        
        User u = new User(nome,cognome,matricola,mail);
      
        if(model.getUserManagement().getList().contains(u)){
            labelErrore.setText("Matricola già esistente");
            labelErrore.setStyle("-fx-text-fill: red;");
        }
        else if(model.getUserManagement().add(u)){
            labelErrore.setText("Inserimento avvenuto correttamente");
            labelErrore.setStyle("-fx-text-fill: green;");
            name.clear();
            surname.clear();
            email.clear();
            numberID.clear();
        }
        else{ labelErrore.setText("Errore nell'inserimento oppure matricola già esistente");
              labelErrore.setStyle("-fx-text-fill: red;");
        }
    }
    
    @FXML
    public void backPage() throws IOException{
        View.Homepage();
    }
    
}
