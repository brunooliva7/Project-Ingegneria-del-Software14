/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;
import java.util.*;
/**
 *
 * @author maramariano
 */
public class UserManagement {
    private Set <User> list; //la mia lista di iscritti
    public UserManagement(){
        this.list=new TreeSet<>(); //uso il tree set così in automatico mel ordina con l'ordine definito da compareTo()
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
    public User search(User u ){
        //metodo per cercare un utente tra la lista 
    }
    
}
