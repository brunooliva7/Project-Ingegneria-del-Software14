
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;
import java.util.*;
import java.time.LocalDate;
/**
 *
 * @author elisa 
 */
public class User   implements Comparable<User> {
    private  String name;
    private  String surname;
    private  String numberId; //matricola
    private  String email; 
    private  Map<Book,LocalDate> booksOnloan; //lista dei libri in prestito  con data di restituzione per ogni utente 
    
    public User(String name,String surname,String numberId,String email){
     this.name=name; 
     this.surname=surname;
     this.numberId=numberId;
     this.email=email;
     booksOnloan=new TreeMap<>(); //così la lista sarà ordinata in base all'ordinamento scelto nella classe Loan
    }
    public void  setName(String name){
        this.name=name;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public void setNumberId(String numberId){
        this.numberId=numberId;
    }
    public void setEmail(String email){
        this.email=email;
    }
     public void setEsami(TreeMap<Book, LocalDate> newMap) {
        this.booksOnloan= newMap;
    }
    
    public Map<Book,LocalDate> getBooksOnloan(){
        return booksOnloan;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getNumberId(){
        return numberId;
    }
    public String getEmail(){
        return email;
    }
    
    @Override
    public int compareTo(User other){
         //servirà per ordinare l'elenco 
    }
    public Map<Book,LocalDate> finddLoans(LoanManagement l){
        //servirà a cercare dall'elenco definito nella classe prestito i prestiti appartenenti all'utente tramite matricola 
    }
}
