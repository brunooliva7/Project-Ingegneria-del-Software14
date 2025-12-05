/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.User;
import java.util.*;
/**
 *
 * @author maramariano
 */
public class UserManagement implements Functionality<User>{
    private Set<User> list; //la mia lista di iscritti
    public UserManagement(){
        list=new TreeSet<>(); //uso il treeSet così in automatico mel ordina con l'ordine definito da compareTo()
    }
    
    @Override
    public void add(User u) {
         //metodo per inserire user all'interno della nostra lista 
    }
    
    @Override
    public void remove(User u ) {
        //metodo per rimuovere un user dal nostro elenco 
    }
    
    @Override
    public void update(User u ){
        //metodo per modificare i dati dell'utente 
    }
    
    @Override
    public void viewSorted(){
         //servirà a stampare la nostra lista ordinata 
    }
    
    @Override
    public User search(String s){
        //metodo per cercare un utente tra la lista in base a se la stringa passata è il cognome o matricola 
    }

}
