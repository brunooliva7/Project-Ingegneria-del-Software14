/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;

/**
 *
 * @author maramariano
 */
public class Librarian {
    
    private String Username;
    private String Password;
    private final String filepath = "credentials.txt";

    
    public boolean checkCredentials(String usernameInput, String passwordInput){   
    }
    
    public void modifyCredentials(String usernameChanged, String passwordChanged){
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }   
}
