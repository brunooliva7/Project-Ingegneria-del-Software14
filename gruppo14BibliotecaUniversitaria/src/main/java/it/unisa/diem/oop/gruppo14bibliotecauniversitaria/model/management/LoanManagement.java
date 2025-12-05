/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Loan;
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
         //NEL MOMENTO IN CUI SI AGGIUNGE UN PRESTITO VA VERIFICATO IL NUMERO DI LIBRI POSSEDUTI DA UN UTENTE,LA DISPONIBILITA' DI COPIE 
    } 
    
    @Override
    public boolean remove(Loan l)
    {
       
    }
    
    public boolean update(Loan l){
         // AGGIORNARE I DATI DELL'UTENTE E DEL LIBRO NEL MOMENTO IN CUI VIENE RIMOSSO O AGGIUNTO UN PRESTITO 
    }

    @Override
    public void viewSorted(){
        
    }
    
    @Override
    public Loan search(Loan l){
       
    }

}
