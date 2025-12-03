/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Functionality;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Loan;
import java.util.*;
import java.time.LocalDate;
/**
 *
 * @author bruno
 */
public class LoanManagement implements Functionality<Loan>{
    Set <Loan> Loan;
    
    public LoanManagement(){
        Loan = new TreeSet<>();
    }
    
    @Override
    public boolean add(Loan l){
        
    }
    
    @Override
    public boolean remove(Loan l)
    {
       
    }
    
    public boolean update(Loan l){
        
    }

    @Override
    public void viewSorted(){
        
    }
    
    @Override
    public Loan search(Loan l){
       
    }

}
