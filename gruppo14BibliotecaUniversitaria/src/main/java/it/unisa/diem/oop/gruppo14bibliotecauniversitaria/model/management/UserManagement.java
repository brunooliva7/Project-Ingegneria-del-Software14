/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file UserManagement.java
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import java.io.Serializable;
import java.util.*;
/**
 *
 * @class UserManagement 
 * @brief  UserManagement è una classe che si occupa essenzialmente delle funzionalità messe a disposizione per l'elenco degli utenti e di modifica dei dati dei singoli user  
 * 
 * 
 */
public class UserManagement implements Functionality<User>,Serializable{
    private Set<User> list; ///<  Lista di iscritti
    /**
     *  @brief Costruttore UserManagement 
     * 
     *  @post Il TreeSet(scelto così che potrà mantenere l'ordinamento definito da compareTo nella classe User) che conterrà la lista di utenti è correttamente inizializzato 
     */
    
    
    public UserManagement(){
        list=new TreeSet<>(); 
    }
    
     /**
     * @brief Getter dell'elenco (list )degli utenti iscritti 
     *
     * @pre esiste un elenco list
     * @return elenco degli utenti
     */
    
    public Set<User> getList() {
    return list;}
    
    
    
    /**
     *  @brief Metodo che aggiunge un user all'elenco 
     *  @param u L'utente da aggiungere all'elenco 
     *  @pre u != null
     *  @post L'user è correttamente inserito nel TreeSet
     */
    
    @Override
    public void add(User u) {
         //metodo per inserire user all'interno della nostra lista 
    }
    
    /**
     *  @brief Metodo che rimuove un user dall'elenco 
     *  @param u L'utente da eliminare dall'elenco 
     *  @pre u != null
     *  @post L'user è correttamente eliminato dal TreeSet
     */
    @Override
    public void remove(User u ) {
       
    }
    /**
     *  @brief Metodo che permette di aggiornare i dati di un utente
     *  @param u L'utente di cui bisogna modificare i dati 
     *  @pre u != null
     *  @post I dati dell'utente u sono correttamente aggiornati 
     */
    @Override
    public void update(User u ){
       
    }
    /**
     *  @brief Metodo che permette di visualizzare il TreeSet contenente l'elenco ordinato in base al cognome e  nome degli utenti  
     *  @post Lelenco opportunamente ordinato è correttamente visualizzato 
     */
    
    @Override
    public void viewSorted(){
         
    }
    /**
     *  @brief Metodo che permette di cercare un utente nell'elenco tramite cognome o matricola
     *  @param s  Dato utilizzato dal bibliotecario per cercare l'utente 
     *  @pre s != null
     *  @post L'utente viene mostrato 
     *  @return L'utente trovato
     */
    @Override
    public User search(String s){
     
    }

}