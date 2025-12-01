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
 * @author bruno
 */
public class LoanManagement implements Functionality,Comparable<Loan>{
    Set <Loan> Loan;
    
    public LoanManagement(){
        Loan = new TreeSet<>();
    }
    
    public void add(Loan l){
    }
    
    public void remove(Loan l)
    {
        
    }
    
    public void viewSorted()
    {
        
    }
    public Loan search(Loan l){
        
    }
    
    @Override
    public int compareTo(Loan other){       
    }
}
