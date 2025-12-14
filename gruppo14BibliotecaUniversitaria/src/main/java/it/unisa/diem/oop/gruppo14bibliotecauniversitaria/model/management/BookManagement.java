/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file BookManagement.java
 * 
 * @author maramariano
 * @date 04-12-2025
 * @version 1.0
 *  
 */

package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import java.util.Set;
import java.util.TreeSet;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.List;

/**
 * @class BookManagement
 * @brief Questa classe si occupa della gestione dei dati del catolo dei libri della biblioteca
 * 
 * La classe gestisce un insieme di libri attraverso le operazioni di aggiunta, rimozione, aggiornamento, ricerca e visualizazzione ordinata libro: titolo, autori, anno di pubblicazione, codice ISBN e numero di copie disponibili; implementa l'interfaccia Functionality
 * Utilizza una struttura dati per mantenere il catalogo ordinato
 * 
 * @invariant catalogue != null
 * 
 * @see Book
 * @see Functionality
 * 
 */
public class BookManagement implements Functionality<Book> {
    private Set <Book> catalogue; ///< Catalogo dei libri gestito come un insieme ordinato in cui non sono permessi duplicati
    
    private final File bookDatabase = new File("bookDatabase.dat");
    /**
    * @brief Costruttore della classe BookManagement
    * 
    * Inizializza un catologo vuoto basato su TreeSet
    * 
    * @post L'attributo catalogue è inizializzato come TreeSet vuoto
    * 
    */
    public BookManagement() {
        catalogue = new TreeSet<>(); 
        
        if (bookDatabase.exists() && bookDatabase.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(bookDatabase))) {
                
                Object letto = ois.readObject();
                
                if (letto instanceof Set) {
                    this.catalogue = (Set<Book>) letto;
                    System.out.println("Caricamento riuscito: " + catalogue.size() + " utenti.");
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
    * @brief Getter del catalogo dei libri
    * 
    * @pre catalogue != null
    * @return insieme dei libri presenti
    */
    public Set<Book> getCatalogue() {
        return catalogue;
    }
    
    /**
    * @brief Aggiunge un libro al catalogo
    * 
    * @param b Libro da aggiungere
    * 
    * @return true se l'aggiunta va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @post Il libro risulta aggiunto al catalogo
    * 
    */
    @Override
    public boolean add(Book b) {
        if (b == null) return false;

        // 1. Controlla se l'ISBN è già presente (e aggiorna le copie se lo è)
        for (Book bk : catalogue) {
            if (bk.getISBN().equals(b.getISBN())) {
                // ISBN già presente, incremento solo di 1 le copie disponibili
                bk.setAvailableCopies(bk.getAvailableCopies() + 1);
                FileManager.writeToTextFileObject(catalogue, this.bookDatabase);
                return true; // aggiornamento effettuato
            }
        }

        // 2. Se il ciclo è terminato e l'ISBN non è stato trovato, aggiunge il nuovo libro
        boolean added = catalogue.add(b);
        if (added) {
             // Se l'aggiunta ha successo, salva il file
             FileManager.writeToTextFileObject(catalogue, this.bookDatabase);
        }
        return added;
    }
    
    /**
    * @brief Elimina un libro dal catalogo
    * 
    * @param b Libro da rimuovere
    * 
    * @return true se la rimozione va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @pre Il libro deve essere presente nel catalogo
    * @post Il libro risulta correttamente eliminato
    * 
    */
    @Override
    public boolean remove(Book b) {
        if (b == null) return false;
        for (Book bk : catalogue) {
            if (bk.equals(b)) { 
                catalogue.remove(bk);
                FileManager.updateFileObject(catalogue, this.bookDatabase);
                return true;
            }
        }
        return false;
    }
    
    /**
    * @brief Aggiorna i dati di un libro presente nel catalogo
    * 
    * Questo metodo cerca un libro nel catalogo con lo stesso ISBN di @p b1
    * e ne aggiorna i dati utilizzando le informazioni contenute in @p b2.
    * L'aggiornamento include titolo, autori, anno di pubblicazione e numero
    * di copie disponibili (solo se non negativo).
    * 
    * @param b1 Libro da identificare nel catalogo (tramite ISBN)
    * @param b2 Libro contenente i dati aggiornati
    * 
    * @return true se l'aggiornamento va a buon fine, false altrimenti
    * 
    * @pre b1 != null
    * @pre b2 != null
    * @pre Il libro identificato da b1 deve essere presente nel catalogo
    * @post I dati del libro nel catalogo risultano aggiornati con quelli di b2
    *   
    */
    @Override
    public boolean update(Book b1, Book b2) {
        if (b1 == null || b2 == null) return false;

        for (Book bk : catalogue) {
            if (bk.getISBN().equals(b1.getISBN())) {
                // Aggiorno i dati con quelli di b2
                bk.setTitle(b2.getTitle());
                bk.setAuthors(b2.getAuthors());
                bk.setPublicationYear(b2.getPublicationYear());

                // Controllo sulle copie disponibili
                if (b2.getAvailableCopies() >= 0) {
                    bk.setAvailableCopies(b2.getAvailableCopies());
                } else {
                    System.out.println("Errore: il numero di copie disponibili non può essere negativo.");
                    return false; // aggiornamento non valido
                }
                
                FileManager.updateFileObject(catalogue, this.bookDatabase);
                return true; // aggiornamento effettuato
            }
        }
        return false; // libro non trovato
    }
    
    
    /**
    * @brief Visualizza in ordine i libri del catalogo
    * 
    * Questo metodo permette la visualizzazione del catalogo dei libri ordinato alfabeticamente in base al loro titolo
    * 
    * @pre Il catalogo non deve essere vuoto
    * @post I libri sono mostrati nell'ordine corretto
    * 
    */
    @Override
    public void viewSorted(){
        if (catalogue.isEmpty()) {
            System.out.println("Il catalogo è vuoto.");
        } else {
            for (Book b : catalogue) {
                System.out.println(b);
            }
        }
    }

    /**
     * @brief Ricerca libri per query (Titolo, Autore o ISBN).
     * Questo metodo permette di cercare libri nel catalogo che contengano 
     * la stringa 'query' nel titolo, negli autori o nel codice ISBN.
     * La ricerca è parziale (non richiede la corrispondenza esatta).
     * @param query Stringa di ricerca (parziale).
     * @return Lista dei libri corrispondenti alla query.
     */
    public List<Book> searchBooks (String query) {
        // Se la query è vuota o null, restituisce tutti i libri.
        if (query == null || query.trim().isEmpty()) {
            return new java.util.ArrayList<>(catalogue);
        }

        // Normalizza la query in minuscolo per una ricerca case-insensitive
        String normalizedQuery = query.trim().toLowerCase();

        List<Book> foundBooks = new java.util.ArrayList<>();

        // Scorre l'insieme dei libri (catalogue)
        for (Book bk : catalogue) {
            // Controlla se la query è contenuta (ricerca parziale) in uno dei tre campi:
            if (bk.getTitle().toLowerCase().contains(normalizedQuery) || 
                bk.getAuthors().toLowerCase().contains(normalizedQuery) ||
                bk.getISBN().toLowerCase().contains(normalizedQuery)) 
            {
                foundBooks.add(bk);
            }
        }
        return foundBooks;
    }

    @Override
    public Book search(Book entity1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}