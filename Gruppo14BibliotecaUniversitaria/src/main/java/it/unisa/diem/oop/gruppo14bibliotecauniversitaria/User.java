
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;
import java.util.*;
/**
 *
 * @author elisa 
 */
public class User   implements Comparable<User> {
    private  String name;
    private  String surname;
    private  String numberId; //matricola
    private  String email; 
    private  Book [] books;
    
    public User(String name,String surname,String numberId,String email){
     this.name=name; 
     this.surname=surname;
     this.numberId=numberId;
     this.email=email;
     this.books=new Book[3];
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
    public void setBooks(Book[] books){
        if(books!=null){  //controllo specifico su null,per gestire meglio 
            this.books=books;
        }
        else {
            this.books=null;
        }
    }
    
    public Book[] getBooks(){
        return books;
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
    
}
