/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file LoanManagement.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */

package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
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

public class LoanManagement implements Functionality<Loan>,Serializable{
   private Set <Loan> loan; ///< insieme dei prestiti da gestire 2
   
   private final File loanDatabase = new File("loan_Database.dat"); //<file database dei prestiti
   
   
   
    
    
    /**
     * @brief Costruttore della classe LoanManagement.
     *
     * @pre Nessuna
     * @post L'insieme dei prestiti è inizializzato come TreeSet vuoto
     */

    public LoanManagement(){
        loan = new HashSet<>();
        
        if(loanDatabase.exists() && loanDatabase.length() > 0){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loanDatabase))) {
                
                Object letto = ois.readObject();
                
                if (letto instanceof Set) {
                    this.loan = (Set<Loan>) letto;
                    System.out.println("Caricamento riuscito: " + loan.size() + " prestiti.");
                } else {
                    System.err.println("ERRORE FILE: Il file contiene un oggetto " + letto.getClass().getName() + " invece di un Set. Il file verrà sovrascritto al prossimo salvataggio.");
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Attenzione: Impossibile caricare il database utenti (verrà creato nuovo). Dettaglio: " + e.getMessage());
            }
        } else {
            System.out.println("File database non trovato o vuoto. Avvio con lista vuota.");
        }
    }
      /**
     * @brief Getter dell'elenco loan 
     *
     * @pre esiste un elenco loan
     * @return elenco dei presiti 
     */
    
    public Set<Loan> getLoan() {
    return loan;}

    
    
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
        if( l == null ) throw new IllegalArgumentException();
        
        if(loan.add(l)) {
            FileManager.writeToTextFileObject(loan, this.loanDatabase);
            return true;
        }
        else return false;
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
        
       if (l == null) throw new IllegalArgumentException();

    Iterator<Loan> iterator = loan.iterator();
    
    while (iterator.hasNext()) {
        Loan current = iterator.next();
        
        // Qui forziamo l'uso del TUO metodo equals (User + Book)
        // ignorando la logica del compareTo (Data)
        if (current.equals(l)) {
            iterator.remove(); // Rimuove in modo sicuro
            FileManager.updateFileObject(loan, this.loanDatabase);
            return true;
        }
    }
    
    return false;
    }
    
    /**
     * @brief Aggiorna i dati di un prestito.
     * @param newL Prestito aggiornato
     * @param oldL prestito da aggiornare
     * 
     * @return true se l'aggiornamento è avvenuto con successo, false altrimenti
     *
     * @pre oldL != null && newL != null
     * @post I dati dell'utente e del libro associati al prestito sono aggiornati
     */

   @Override
    public boolean update(Loan newL, Loan oldL){
       if (newL == null || oldL == null) throw new IllegalArgumentException();

            Iterator<Loan> iterator = loan.iterator();
            boolean removed = false;
            
            while (iterator.hasNext()) {
            Loan current = iterator.next();
        
        if (current.equals(oldL)) {
            
            iterator.remove();
            removed = true;
            break; // Usciamo dal ciclo, il lavoro di rimozione è fatto
        }
    }
         if (removed) {
        loan.add(newL); // Aggiungiamo il nuovo prestito
        FileManager.updateFileObject(loan, this.loanDatabase);
        return true;
            }

     return false;
    }
    
    /**
     * @brief Visualizza i prestiti ordinati.
     *
     * @pre L'insieme dei prestiti deve essere stato inizializzato
     * @post I prestiti vengono mostrati secondo l'ordinamento definito in Loan
     */

    @Override
    public void viewSorted(){
        StringBuilder sb = new StringBuilder();
        for(Loan l : loan){
             sb.append(l.toString());
             sb.append("\n");
        }
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
    public List<Loan> search(Loan l){
        
       if (l == null) throw new IllegalArgumentException();

    List<Loan> result = new ArrayList<>();
    
    String searchUserId = (l.getUser() != null) ? l.getUser().getNumberId() : null;
    String searchBookISBN = (l.getBook() != null) ? l.getBook().getISBN() : null;

    for (Loan l1 : this.loan) { // loanList = tutti i prestiti caricati
        String currentUserId = (l1.getUser() != null) ? l1.getUser().getNumberId() : null;
        String currentISBN = (l1.getBook() != null) ? l1.getBook().getISBN() : null;

        boolean matchUser = searchUserId == null || searchUserId.isEmpty() ||
                            (currentUserId != null && currentUserId.contains(searchUserId));
        boolean matchBook = searchBookISBN == null || searchBookISBN.isEmpty() ||
                            (currentISBN != null && currentISBN.contains(searchBookISBN));

        if (matchUser && matchBook) {
            result.add(l1);
        }
    }
    return result;

    }

}

