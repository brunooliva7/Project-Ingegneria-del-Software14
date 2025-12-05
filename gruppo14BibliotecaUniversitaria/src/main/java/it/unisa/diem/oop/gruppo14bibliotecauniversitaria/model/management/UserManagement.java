/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.Functionality;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.User;
import java.util.*;
/**
 *
 * @author elisa 
 * @class UserManagement 
 * @brief  UserManagement è una classe che si occupa essenzialmente delle funzionalità messe a disposizione per l'elenco degli utenti e di modifica dei dati dei singoli user  
 * 
 * 
 */
public class UserManagement implements Functionality<User>{
    private Set<User> list; //@brief  Lista di iscritti
    /**
     *  @brief Costruttore UserManagement 
     * 
     *  @post Il TreeSet(scelto così che potrà mantenere l'ordinamento definito da compareTo nella classe User) che conterrà la lista di utenti è correttamente inizializzato 
     */
    
    
    public UserManagement(){
        list=new TreeSet<>(); 
    }
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