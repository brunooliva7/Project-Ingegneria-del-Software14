/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author bruno
 */
public class homepageController {
    
    @FXML  
    private Label prova;
    
    @FXML
    public void initialize() {
        // Questo parte appena si apre la Homepage
        System.out.println("Homepage caricata correttamente!");
        
        if(prova != null) {
            prova.setText("Benvenuto nella Biblioteca!");
        }
    }
}
