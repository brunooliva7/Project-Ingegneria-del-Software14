
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;

/**
 *
 * @author elisa 
 */
public class User {
    private  String name;
    private  String surname;
    private  String numberId; //matricola
    private  String email; 
    private  Book [] books= new Book[3];
    
    public User(String name,String surname,String numberId,String email) implements Comparable<Book>{
     }
    public void  setName(String name);
    public void setSurname(String surname);
    public void setNumberId(String number);
    public void setEmail(String email);
    public void setBooks(Book[] books);
    
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
    
}
