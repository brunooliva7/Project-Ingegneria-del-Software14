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
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
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
 * secondo il criterio temporale definito nella classe Loan.
 */

public class LoanManagement implements Functionality<Loan>,Serializable{
   private Set <Loan> loan; ///< insieme dei prestiti da gestire 
   
   private final File loanDatabase = new File("loan_Database.dat"); //<file database dei prestiti
  
   /**
     * @brief Costruttore della classe LoanManagement.
     * * Inizializza la struttura dati. Se il file database esiste e non è vuoto,
     * carica i prestiti salvati, altrimenti inizializza un insieme vuoto.
     *
     * @pre Nessuna.
     * @post L'insieme dei prestiti è inizializzato (vuoto o con dati caricati).
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
     * @brief Restituisce l'insieme dei prestiti gestiti.
     *
     * @return Set<Loan> L'insieme dei prestiti correnti.
     * * @pre L'oggetto LoanManagement deve essere istanziato.
     * @post Viene restituito il riferimento all'insieme dei prestiti.
     */
    
    public Set<Loan> getLoan() {
    return loan;}

    
    
    /**
     * @brief Aggiunge un nuovo prestito all'insieme.
     * * @param l Il prestito da aggiungere.
     * @return true se l'aggiunta ha successo, false se il prestito esiste già.
     *
     * @pre l != null.
     * @post Se l non è presente, viene aggiunto all'insieme e il database viene aggiornato.
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
     * * @param l Il prestito da rimuovere.
     * @return true se la rimozione ha successo, false se il prestito non viene trovato.
     *
     * @pre l != null.
     * @post Se l è presente, viene rimosso dall'insieme e il database viene aggiornato.
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
     * @brief Aggiorna i dati di un prestito esistente.
     * * @param newL Il nuovo oggetto prestito con i dati aggiornati.
     * @param oldL Il vecchio oggetto prestito da sostituire.
     * @return true se l'aggiornamento ha successo, false se oldL non viene trovato.
     *
     * @pre newL != null && oldL != null.
     * @post Se oldL esiste, viene sostituito da newL e il database viene aggiornato.
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
     * @brief Visualizza i prestiti in ordine.
     * * Costruisce una stringa contenente la rappresentazione di tutti i prestiti,
     * ordinati secondo il criterio definito in Loan (data di scadenza).
     *
     * @pre L'insieme dei prestiti deve essere inizializzato.
     * @post Viene generata (ma attualmente non stampata/ritornata nel codice originale) la lista ordinata.
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
     * @brief Cerca prestiti nell'insieme in base a criteri parziali.
     * * La ricerca avviene verificando se la matricola dell'utente o l'ISBN del libro
     * contengono le stringhe specificate nel prestito filtro.
     * * @param l Oggetto Loan usato come filtro (contiene matricola e/o ISBN da cercare).
     * @return List<Loan> Una lista contenente tutti i prestiti che soddisfano i criteri.
     *
     * @pre l != null.
     * @post Restituisce una lista (eventualmente vuota) di prestiti trovati, senza modificare l'archivio.
     */

   @Override
    public List<Loan> search(Loan l) {
        if (l == null) throw new IllegalArgumentException("Filtro null");

        List<Loan> result = new ArrayList<>();

        // Estrazione Filtri
        User filterUser = l.getUser();
        Book filterBook = l.getBook();

        for (Loan current : this.loan) {
            boolean matchUser = true;
            boolean matchBook = true;

            // --- 1. MATCH UTENTE ---
            if (filterUser != null) {
                String fId = filterUser.getNumberId();
                String fSurname = filterUser.getSurname(); // Qui finisce l'input testuale del costruttore User
                
                // Capiamo se c'è effettivamente un filtro attivo
                boolean hasIdFilter = (fId != null && !fId.isEmpty());
                boolean hasTextFilter = (fSurname != null && !fSurname.isEmpty());
                
                if (hasIdFilter || hasTextFilter) {
                    boolean found = false;
                    User target = current.getUser();
                    
                    // A. Controllo ID (Matricola)
                    if (hasIdFilter) {
                        if (target.getNumberId() != null && target.getNumberId().contains(fId)) {
                            found = true;
                        }
                    }
                    
                    // B. Controllo Testuale (Nome O Cognome)
                    // Se non abbiamo già trovato tramite ID, controlliamo il testo
                    if (!found && hasTextFilter) {
                        String query = fSurname.toLowerCase(); // Il costruttore mette la query in surname
                        boolean inName = (target.getName() != null && target.getName().toLowerCase().contains(query));
                        boolean inSurname = (target.getSurname() != null && target.getSurname().toLowerCase().contains(query));
                        
                        if (inName || inSurname) found = true;
                    }
                    
                    // Se c'era un filtro ma non abbiamo trovato nulla -> Match Fallito
                    if (!found) matchUser = false;
                }
            }

            // --- 2. MATCH LIBRO ---
            if (filterBook != null) {
                // Il costruttore Book mette la query in: ISBN, Title, Authors.
                // Ne prendiamo uno qualsiasi (es. ISBN) come "query string".
                String query = filterBook.getISBN(); 
                
                if (query != null && !query.isEmpty()) {
                    Book target = current.getBook();
                    String qLower = query.toLowerCase();
                    
                    boolean inIsbn = (target.getISBN() != null && target.getISBN().toLowerCase().contains(qLower));
                    boolean inTitle = (target.getTitle() != null && target.getTitle().toLowerCase().contains(qLower));
                    
                    // Se non è né nell'ISBN né nel Titolo -> Match Fallito
                    if (!inIsbn && !inTitle) {
                        matchBook = false;
                    }
                }
            }

            // Se entrambi i criteri sono soddisfatti, aggiungi
            if (matchUser && matchBook) {
                result.add(current);
            }
        }
        return result;
    }

}

