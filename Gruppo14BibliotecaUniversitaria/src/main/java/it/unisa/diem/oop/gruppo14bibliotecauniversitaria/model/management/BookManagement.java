/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Book;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author maramariano
 */
public class BookManagement implements Functionality {
    private Set <Book> catalogue;
    
    public BookManagement() {
        this.catalogue = new TreeSet<>();
    }
    
    @Override
    public boolean add(Book b) {
        
    }
    
    @Override
    public boolean remove(Book b) {
        
    }
    
    @Override
    public boolean update(Book b){
        
    }
    
    @Override
    public void viewSorted(){
        
    }
    
    @Override
    public Book search(Book b){
        
    }
    
}
