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
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import java.util.Set;
import java.util.TreeSet;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;

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
    
    /**
    * @brief Costruttore della classe BookManagement
    * 
    * Inizializza un catologo vuoto basato su TreeSet
    * 
    * @post L'attributo catalogue è inizializzato come TreeSet vuoto
    * 
    */
    public BookManagement() {
        this.catalogue = new TreeSet<>(); 
    }
    
    /**
    * @brief Aggiunge un libro al catalogo
    * 
    * @param[in] b Libro da aggiungere
    * 
    * @return true se l'aggiunta va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @post Il libro risulta aggiunto al catalogo
    * 
    */
    @Override
    public boolean add(Book b) {
        
    }
    
    /**
    * @brief Elimina un libro dal catalogo
    * 
    * @param[in] b Libro da rimuovere
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
        
    }
    
    /**
    * @brief Modifica i dati di un libro nel catalogo
    * 
    * @param[in] b Libro con i dati aggiornati
    * 
    * @return true se l'aggiornamneto va a buon fine, false atrimenti
    * 
    * @pre b != null
    * @pre Il libro deve essere presente nel catalogo
    * @post I dati del libro risultano aggiornati
    * 
    */
    @Override
    public boolean update(Book b){
        
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
        
    }
    
    
    /**
    * @brief Ricerca un libro nel catalogo
    * 
    * Questo metodo permette di cercare uno specificato libro per titolo, autore o codice identificativo
    * La ricerca si basa sul confronto tra l'oggetto passato come parametro e gli elementi presenti nel catalogo
    * 
    * @param[in] b Libro da cercare
    * @return Il libro trovato, se la ricerca è andata a buon fine; null, altrimenti
    * 
    * @pre b != null
    * 
    * @post Restituisce il libro corrispondente se presente, altrimenti null
    * 
    */
    @Override
    public Book search(Book b){
        
    }
    
}