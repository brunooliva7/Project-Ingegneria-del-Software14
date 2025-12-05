/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Loan;
import java.util.*;
import java.time.LocalDate;
/**
 * @class LoanManagement
 * @brief Gestisce l'insieme dei prestiti della biblioteca.
 *
 * La classe LoanManagement implementa l'interfaccia Functionality per i prestiti,
 * consentendo operazioni di aggiunta, rimozione, aggiornamento, ricerca e visualizzazione
 * ordinata. Utilizza una struttura Set (TreeSet) per mantenere i prestiti ordinati
 * secondo il criterio definito nella classe Loan.
 */

public class LoanManagement implements Functionality<Loan>{
    Set <Loan> Loan; // insieme dei prestiti da gestire 2
    
    
    /**
     * @brief Costruttore della classe LoanManagement.
     *
     * @pre Nessuna
     * @post L'insieme dei prestiti è inizializzato come TreeSet vuoto
     */

    public LoanManagement(){
        Loan = new TreeSet<>();
    }
    
    
    /**
     * @brief Aggiunge un prestito all'insieme.
     * @param l Prestito da aggiungere 
     * @return true se il prestito è stato aggiunto, false altrimenti
     *
     * @pre l != null
     * @post Il prestito è aggiunto all'insieme se non già presente e se le condizioni
     *       di disponibilità dei libri e del numero massimo consentito per utente sono rispettate
     */

    @Override
    public boolean add(Loan l){
    } 
       
     /**
     * @brief Rimuove un prestito dall'insieme.
     * @param l Prestito da rimuovere 
     * @return true se il prestito è stato rimosso, false altrimenti
     *
     * @pre l != null
     * @post Il prestito è rimosso dall'insieme se presente
     */

    @Override
    public boolean remove(Loan l)
    {
       
    }
    
    /**
     * @brief Aggiorna i dati di un prestito.
     * @param l Prestito da aggiornare 
     * @return true se l'aggiornamento è avvenuto con successo, false altrimenti
     *
     * @pre l != null
     * @post I dati dell'utente e del libro associati al prestito sono aggiornati
     */

    public boolean update(Loan l){
         // AGGIORNARE I DATI DELL'UTENTE E DEL LIBRO NEL MOMENTO IN CUI VIENE RIMOSSO O AGGIUNTO UN PRESTITO 
    }
    
    /**
     * @brief Visualizza i prestiti ordinati.
     *
     * @pre L'insieme dei prestiti deve essere stato inizializzato
     * @post I prestiti vengono mostrati secondo l'ordinamento definito in Loan
     */

    @Override
    public void viewSorted(){
        
    }
    
        /**
     * @brief Cerca un prestito nell'insieme.
     * @param l Prestito da cercare (non deve essere null)
     * @return Il prestito trovato, oppure null se non presente
     *
     * @pre l != null
     * @post Restituisce il prestito corrispondente se presente, altrimenti null
     */

    @Override
    public Loan search(Loan l){
       
    }

}

