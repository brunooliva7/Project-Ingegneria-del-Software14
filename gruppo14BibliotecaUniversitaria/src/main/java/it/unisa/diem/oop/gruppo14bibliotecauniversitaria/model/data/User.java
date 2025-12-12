
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
    private  String numberId;  ///< @brief Matricola dell'utente
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
     * @brief costruttore User 
     * @param s Stringa usata dal bibliotecario per ricercare l'utente corrispondente 
     * @post L'User è correttamente inizializzato per quello che ci serve
     */
    public User (String s ){  //overload del costruttore,sarà utilizzato solo ai fini della ricerca dell'utente
        if(Character.isDigit(s.charAt(0))){ //se la stringa passata inizia con un numero è una matricola
            this.name=null;
            this.surname=null;
            this.numberId=s;
            this.email=null;
            this.booksOnloan=null;
        }
        else { //se la stringa passata non inizia con un numero la ricerca è stata effettuala per cognome 
            this.name=null;
            this.surname=s;
            this.numberId=null;
            this.email=null;
             this.booksOnloan=null;
            
        }
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
     * @brief Imposta il numberId(matricola)dell'utente
     * @param numberId Nuovo numberId da impostare 
     * @post Il numberId  è correttamente aggiornato
    */
    
    public void setNumberId(String numberId){
        this.numberId=numberId;
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
      * 
     */
    @Override
    public int compareTo(User other){
         int comparing=this.surname.compareTo(other.surname); //faccio un compare basato sul cognome
         if(comparing!=0){  //se il comparing è diverso da 0 significa che effettivamente il confronto è stato possibile dato che i cognomi erano diversi
             return comparing;
         }
         else return this.name.compareTo(other.name); //se il compare sul nome ha prodotto 0 significa che hanno lo stesso cognome e il confronto deve essere fatto sulla base del nome
    }
    
    /**
    * @brief  Aggiorna la mappa dei libri attualmente in prestito all'utente,
    *         associando un libro alla sua data di restituzione.
    *
    * @param  b   Il libro da registrare come prestito
    * @param  data La data di restituzione prevista per il libro
    *
    * @pre    b != null && data != null
    * @post   La mappa booksOnLoan viene aggiornata con la coppia (libro, data di restituzione)
    *
    */
    public void findLoans(Book b,LocalDate data ){
      booksOnloan.put(b,data);
     //aggiorno la lista dei libri-datadirestituzione dell'utente 
    }
     /**
      * @brief  Metodo per controllare se due oggetti della classe User sono uguali sulla base del numeberId
      * @param  obj altro oggetto da confrontare con quello attuale 
      * @return Valore boolean che è true se l'oggetto attuale e quello passato come parametro sono uguali,altrimenti dà false
      * 
     */
    
    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(this==obj) return true; //hanno lo stesso riferimento sono sicuramente uguali
        if(obj.getClass()!= User.class) return false; //se non hanno la stessa classe sono sicuramente diversi 
        User other=(User) obj; //casting esplicito così da poter vedere se hanno il campo numberID uguale (univoco)
        return this.numberId!= null && this.numberId.equals(other.numberId); //ritorno se sono uguali o no facendo anche un controllo su null così da evitare la NullPointerException
    }
    
    /**
      * @brief  Metodo che ritorna un numero intero sulla base del numberId coerentemente con il metodo equals
      * @return Valore intero che è uguale all'hashCode del numberId se questo è diverso da null altrimenti ritorna ovviamente 0 
      * 
     */
    
    @Override
    public int hashCode(){
         return numberId != null ? numberId.hashCode() : 0; 

    }
    /**
      * @brief  Metodo per stampare a video un oggetto della classe User
      * @return Stringa che include tutti i campi della classe User
      * 
     */
    @Override 
    public String toString(){
        StringBuffer sb = new StringBuffer();  //creo uno StringBuffer per facilitare la creazione dell'output 
        sb.append("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", numberId='").append(numberId).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", booksOnLoan={");

        if (booksOnloan != null && !booksOnloan.isEmpty()) {   //se la mia mappa non è nulla o vuota stampo ogni sua coppia libro-data 
            booksOnloan.forEach((book, date) -> sb.append("\n").append(book).append(" -> ").append(date));
        }

        sb.append("}}");
        return sb.toString();
    }
}