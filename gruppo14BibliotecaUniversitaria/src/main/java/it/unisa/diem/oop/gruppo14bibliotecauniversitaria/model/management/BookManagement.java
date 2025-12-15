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
import java.util.ArrayList;
import java.util.List;

/**
 * @class BookManagement
 * @brief Questa classe si occupa della gestione dei dati del catalogo dei libri della biblioteca
 * 
 * La classe gestisce un insieme di libri attraverso le operazioni di aggiunta, rimozione, aggiornamento, ricerca e visualizazzione ordinata dei libri 
 * Implementa l'interfaccia Functionality e utilizza una struttura dati per mantenere il catalogo ordinato e senza duplicati
 * 
 * @invariant catalogue != null
 * 
 * @see Book
 * @see Functionality
 * 
 */
public class BookManagement implements Functionality<Book> {
    private Set <Book> catalogue; ///< Catalogo dei libri gestito come un insieme ordinato in cui non sono permessi duplicati
    
    private final File bookDatabase = new File("bookDatabase.dat"); ///< File database dei libri
    
    /**
    * @brief Costruttore della classe BookManagement
    * 
    * Inizializza un catologo vuoto basato su TreeSet
    * Se esiste un file di database non vuoto, tenta di caricare i dati dal file
    * 
    * @post L'attributo catalogue è inizializzato come TreeSet (vuoto o caricato da file)
    * 
    */
    public BookManagement() {
        catalogue = new TreeSet<>(); 
        
        if (bookDatabase.exists() && bookDatabase.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(bookDatabase))) {
                
                Object letto = ois.readObject();
                
                if (letto instanceof Set) {
                    this.catalogue = (Set<Book>) letto;
                    System.out.println("Caricamento riuscito: " + catalogue.size() + " libri.");
                } else {
                    System.err.println("ERRORE FILE: Il file contiene un oggetto " + letto.getClass().getName() + " invece di un Set. Il file verrà sovrascritto al prossimo salvataggio.");
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Attenzione: Impossibile caricare il database libri (verrà creato nuovo). Dettaglio: " + e.getMessage());
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
    * Se l'ISBN è già presente, incrementa il numero di copie disponibili
    * Altrimenti aggiunge il nuovo libro al catalogo
    * 
    * @param b Libro da aggiungere
    * @return true se l'aggiunta va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @post Il catalogo è aggiornato con il nuovo libro o con l'incremento delle copie
    * 
    */
    @Override
    public boolean add(Book b) {
        if (b == null){ 
            throw new IllegalArgumentException();
            // esce senza aggiungere ritornando che non è andato a buon fine l'inserimento lanciando l'eccezione che specifica che l'argomento non è valido 
         }

        // Controlla se l'ISBN è già presente (e aggiorna le copie se lo è)
        for (Book bk : catalogue) {
            if (bk.getISBN().equals(b.getISBN())) {
                // ISBN già presente, incremento solo di 1 le copie disponibili
                bk.setAvailableCopies(bk.getAvailableCopies() + 1);
                FileManager.writeToTextFileObject(catalogue, this.bookDatabase);
                return true; // aggiornamento effettuato
            }
        }

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
    * Se il libro è presente viene rimosso, altrimenti restituisce false
    * 
    * @param b Libro da rimuovere
    * @return true se la rimozione va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @post Il libro risulta eliminato se presente
    * 
    */
    @Override
    public boolean remove(Book b) {
        if (b == null) {
            throw new IllegalArgumentException();
            // esce senza rimuovere ritornando che non è andato a buon fine l'operazione, lanciando l'eccezione che specifica che l'argomento non è valido 
         } 
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
    * Cerca un libro con lo stesso ISBN di b1 e ne aggiorna titolo, autori,
    * anno di pubblicazione e copie disponibili con i dati di b2
    * L'ISBN non viene modificato
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
    * Se il catalogo è vuoto stampa un messaggio, altrimenti mostra i libri
    * ordinati alfabeticamente per titolo
    * 
    * @post I libri sono mostrati nell'ordine corretto
    * 
    */
    @Override
    public void viewSorted(){
        if (catalogue.isEmpty()) {
            System.out.println("Il catalogo è vuoto.");
        } else {
            for (Book b : catalogue) {
                System.out.println(b.toString()); //stampo per ogni libro appartenente all'elenco i corrispondenti dati 
            }
        }
    }
    
    

    /**
     * @brief Ricerca libri unificata (Titolo, Autore o ISBN), parziale e case-insensitive
     *
     * @param b Oggetto Book
     * @return Lista dei libri corrispondenti alla query
     * 
     * @pre b!=null
     */
    @Override
    public List <Book> search(Book b){
        if (b == null) throw new IllegalArgumentException("Il libro di ricerca non può essere nullo.");

        List<Book> lista = new ArrayList<>(); 

        // 1. Estrai la query (assumiamo sia stata replicata in b.getTitle() dal costruttore)
        String query;
        if (b.getTitle() != null && !b.getTitle().isEmpty()) {
            query = b.getTitle().trim().toLowerCase();
        } else {
            return lista; 
        }

        // 2. Itera e filtra con OR logico su tutti i campi
        for (Book bk : catalogue) {

            boolean matches = 
                // Cerca nel titolo
                (bk.getTitle() != null && bk.getTitle().toLowerCase().contains(query)) ||
                // Cerca negli autori
                (bk.getAuthors() != null && bk.getAuthors().toLowerCase().contains(query)) ||
                // Cerca nell'ISBN
                (bk.getISBN() != null && bk.getISBN().toLowerCase().contains(query));

            if (matches) {
                lista.add(bk);
            }
        }

        return lista;
    }

}