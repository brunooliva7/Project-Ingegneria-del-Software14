/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
/**
 *
 * @author bruno
 */
public class homepageController {
    
    @FXML
    private MenuItem aggiungiUtente;
    
    @FXML
    private MenuItem modificaUtente;
    
    @FXML
    private MenuItem cancellaUtente;
    
    @FXML
    private MenuItem cercaUtente;
    
    @FXML
    private MenuItem aggiungiLibro;
    
    @FXML
    private MenuItem cancellaLibro;
    
    
    @FXML
    public void addUser() throws IOException{
        View.addUser();
    }
    
    @FXML
    public void modifyUser() throws IOException{
        View.modifyUser();
    }
    
    @FXML 
    public void deleteUser() throws IOException{
        View.deleteUser();
    }
    
    @FXML
    public void searchUser() throws IOException{
        View.searchUser();
    }
    
    @FXML
    public void viewUser() throws IOException{
        View.viewUSers();
    }
    
    @FXML
    public void addBook() throws IOException{
        View.addBook();
    }
    
    @FXML
    public void deleteBook() throws IOException{
        View.deleteBook();
    }
}
