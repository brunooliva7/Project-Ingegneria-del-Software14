/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author maramariano
 */
public class BookManagement implements Functionality {
    private Set <Book> catalog;
    
    public BookManagement() {
        this.catalog = new TreeSet<>();
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
