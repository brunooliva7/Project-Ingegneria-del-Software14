
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file User.java
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDate;  
/**
 *
 * @author elisa 
 * @class User 
 * @brief  User è un utente generico che interagisce col bibliotecario e al quale dobbiamo mettere a disposizione servizi
 *la classe contiene le informazioni principali da memorizzare per un utente quindi nome,cognome,matricola,email e lista dei libri in prestito con relativa data di restituzione 
 * 
 */
public class User   implements Comparable<User>,Serializable{
    private  String name; /// < @brief Nome dell'utente
    private  String surname; ///< @brief Cognome dell'utente
    private String numberId;  ///< @brief Matricola dell'utente
    private  String email;  ///< @brief Email dell'utente 
    private  Map<Book,LocalDate> booksOnloan; ///< @brief Mappa che mantiene al proprio interno una lista di  libri in prestito  con data di restituzione associata  per utente 
    
    /**
     * @brief costruttore User 
     * @param name Nome utente
     * @param surname Cognome utente 
     * @param numberId Matricola utente 
     * @param email Email utente 
     * @post L'User è correttamente inizializzato 
     */
    
    
    public User(String name,String surname,String numberId,String email){
     this.name=name; 
     this.surname=surname;
     this.numberId=numberId;
     this.email=email;
     booksOnloan=new TreeMap<>(); //così la lista sarà ordinata in base all'ordinamento scelto nella classe Loan
    }
    /**
      * @brief Imposta il nome dell'utente
      * @param name Nuovo nome da impostare 
      * @post Il nome è correttamente aggiornato 
    */
    public void  setName(String name){
        this.name=name;
    }
    /**
      * @brief Imposta il cognome dell'utente
      * @param surname Nuovo cognome da impostare 
      * @post Il cognome è correttamente aggiornato
    */
    public void setSurname(String surname){
        this.surname=surname;
    }
    /**
     * @brief Imposta l'email dell'utente
     * @param email Nuova email da impostare 
     * @post L'email è correttamente aggiornata
    */
    
    public void setEmail(String email){
        this.email=email;
    }
    /**
     * @brief Imposta la mappa di libri-dataRestituzione dell'utente
     * @param newMap Nuova mappa da impostare
     * @post BooksOnLoan è correttamente aggiornato 
    */
     public void setBooksOnLoan (TreeMap<Book, LocalDate> newMap) {
        this.booksOnloan= newMap;
    }
     /**
      * @brief Restituisce la mappa di libri-dataRestituzione dell'utente
      * @return  Mappa libri-dataRestituzione dell'utente
    */
    public Map<Book,LocalDate> getBooksOnloan(){
        return booksOnloan;
    }
     /**
      * @brief Restituisce il nome dell'utente
      * @return  Nome dell'utente 
    */
    public String getName(){
        return name;
    }
     /**
      * @brief Restituisce il cognome dell'utente
      * @return   Cognome dell'utente 
    */
    public String getSurname(){
        return surname;
    }
    /**
     * @brief Restituisce la matricola  dell'utente
     * @return  Matricola  dell'utente 
    */
    public String getNumberId(){
        return numberId;
    }
    /** 
     * @brief Restituisce l'email  dell'utente
     * @return  Email dell'utente 
    */
    public String getEmail(){
        return email;
    }
     /**
      * @brief Servirà nell'elenco di utenti per ordinarlo in base a cognome e nome degli utenti ,dato che è un TreeSet questo metodo
               verrà chiamato in automatico nel momento in cui viene creato l'elenco
      * @param  other altro utente con cui comparare quello corrente per l'ordinamento 
      * @return  Valore negativo,valore positivo o 0 in base all'ordinamento
     */
    @Override
    public int compareTo(User other){
         
    }
    /**
      * @brief  Servirà a cercare nell'elenco definito nella classe LoanManagement i prestiti appartenenti all'utente,cercando  tramite matricola
                così da poter riempire la mappa attributo  booksOnLoan  
      * @param  l L'elenco tra cui dovrò cercare i prestiti associati all'utente
      * @param  s La matricola dell'utente di cui dovrò cercare i prestiti
      * @return Mappa dei libri-dataRestituzione riferita ai prestiti dell'utente
    */
    public Map<Book,LocalDate> findLoans(LoanManagement l,String s ){
    }
}