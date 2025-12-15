/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
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
    private MenuItem modificaLibro;
    
    @FXML
    private MenuItem cercaLibro;
    
    @FXML
    private MenuItem aggiungiPrestito;
    
    @FXML
    private MenuItem cancellaPrestito;
    
    @FXML
    private MenuItem visualizzaElencoPrestito;
    
    @FXML
    private MenuItem cercaPrestito;
    
    private Model model;
    
    
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
    
    @FXML
    public void updateBook() throws IOException{
        View.updateBook();
    }
    
    @FXML
    public void searchBook() throws IOException{
        View.searchBook();
    }
    
    @FXML
    public void viewBooks() throws IOException{
        View.viewBooks();
    }
    
    @FXML
    public void addLoan() throws IOException{
        View.addLoan();
    }
    
    @FXML
    public void searchLoan() throws IOException{
        View.searchLoan();
    }
    
    @FXML
    public void deleteLoan() throws IOException{
        View.deleteLoan();
    }
    
    @FXML
    public void viewLoan() throws IOException{
        View.viewLoan();
    }
}
