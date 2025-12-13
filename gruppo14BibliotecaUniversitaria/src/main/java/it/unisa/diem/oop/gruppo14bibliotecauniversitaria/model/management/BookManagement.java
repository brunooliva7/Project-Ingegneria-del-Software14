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
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import java.io.File;
import java.net.URL;

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
    
    private final File bookDatabase = new File("src/main/resources/bookDatabase.txt");
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

        for (Book bk : catalogue) {
        if (bk.getISBN().equals(b.getISBN())) {
            // ISBN già presente, incremento solo di 1 le copie disponibili
            bk.setAvailableCopies(bk.getAvailableCopies() + 1);
            FileManager.writeToTextFileObject(catalogue, this.bookDatabase);
            return true; // aggiornamento effettuato
        }
        // Se non esiste ancora, aggiungo il nuovo libro
        return catalogue.add(b);
    }

    // Se non esiste ancora, aggiungo il nuovo libro
    return catalogue.add(b);
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
    * @brief Modifica i dati di un libro nel catalogo
    * 
    * @param b Libro con i dati aggiornati
    * 
    * @return true se l'aggiornamneto va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @pre Il libro deve essere presente nel catalogo
    * @post I dati del libro risultano aggiornati
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
    * @brief Ricerca un libro nel catalogo
    * 
    * Questo metodo permette di cercare uno specificato libro per titolo, autore o codice identificativo
    * La ricerca si basa sul confronto tra l'oggetto passato come parametro e gli elementi presenti nel catalogo
    * 
    * @param b Libro da cercare
    * @return Il libro trovato, se la ricerca è andata a buon fine; null, altrimenti
    * 
    * @pre b != null
    * 
    * @post Restituisce il libro corrispondente se presente, altrimenti null
    * 
    */
    @Override
    public Book search(Book b){
        if (b == null) return null;

        if (b.getISBN() != null) { // ricerca per ISBN
            for (Book bk : catalogue) {
                if (bk.equals(b)) {
                    return bk;
                }
            }
        } else if (b.getTitle() != null) { // ricerca per titolo
            for (Book bk : catalogue) {
                if (bk.getTitle().equalsIgnoreCase(b.getTitle())) {
                    return bk;
                }
            }
        }
        return null;
    }
    
}