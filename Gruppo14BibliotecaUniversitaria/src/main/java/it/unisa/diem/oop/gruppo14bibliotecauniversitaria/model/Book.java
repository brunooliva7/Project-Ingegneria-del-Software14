/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file Book.java
 * 
 * @author maramariano
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model;
import java.time.LocalDate;
        
/**
 * @class Book
 * @brief Questa classe si occupa della gestione dei dati dei libri della biblioteca
 * 
 * La classe gestisce i dati principali di un libro: titolo, autori, anno di pubblicazione, codice ISBN e numero di copie disponibili; implementa l'interfaccia Comparable per l'ordinamento
 * 
 * @invariant availableCopies >= 0
 * 
 * @see Comparable
 * 
 */
public class Book implements Comparable<Book> {
    
    private String title; /// Titolo del libro
    private String authors; /// Autore/i
    private LocalDate publicationYear; /// Anno di pubblicazione
    private String ISBN; /// Codice identificativo univoco
    private int availableCopies; /// Numero di copie disponibili
    
    /**
    * @brief Costruttore della classe Book
    * @param[in] title Titolo del libro
    * @param[in] authors Autori del libro
    * @param[in] publicationYear Anno di pubblicazione del libro
    * @param[in] ISBN Codice ISBN
    * @param[in] availableCopies Numero di copie disponibili
    * @pre availableCopies >= 0
    * @post L'oggetto Book è correttamente inizializzato
    * 
    */
    public Book(String title, String authors, LocalDate publicationYear, String ISBN, int availableCopies) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.availableCopies = availableCopies;
    }

    /**
    * @brief Imposta il valore del titolo
    * @param[in] title Nuovo titolo da assegnare
    * @post Il titolo è aggiornato
    * 
    */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * @brief Imposta gli autori del libro
    * @param[in] authors Nuovi autori da assegnare
    * @post Gli autori del libro sono aggiornati
    * 
    */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
    * @brief Imposta l'anno di pubblicazione del libro
    * @param[in] publicationYear Nuovo anno da assegnare
    * @post La data di pubblicazione è aggiornata
    * 
    */
    public void setPublicationYear(LocalDate publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
    * @brief Imposta il codice ISBN del libro
    * @param[in] ISBN Nuovo codice da assegnare
    * @post Il codice identificativo è aggiornato
    * 
    */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
    * @brief Imposta il numero di copie disponibili
    * @param[in] availableCopies Nuovo numero di copie disponibili
    * @pre availableCopies >= 0
    * @post Il numero di copie disponibili è aggiornato
    * 
    */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
    * @brief Restituisce il valore di title
    * @return Titolo del libro (stringa)
    * 
    */
    public String getTitle() {
        return title;
    }

    /**
    * @brief Restituisce il valore di authors
    * @return Autore/i del libro (stringa)
    * 
    */
    public String getAuthors() {
        return authors;
    }

    /**
    * @brief Restituisce il valore di publicationYear
    * @return Anno di pubblicazione del libro (LocalDate)
    * 
    */
    public LocalDate getPublicationYear() {
        return publicationYear;
    }

    /**
    * @brief Restituisce il valore di ISBN
    * @return Codice identificativo del libro (stringa)
    * 
    */
    public String getISBN() {
        return ISBN;
    }

    /**
    * @brief Restituisce il valore di availableCopies
    * @return Disponibilità di un libro nel catalogo (int)
    * 
    */
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    /**
    * @brief Confronta i libri per titolo
    * 
    * Questa funzione permette di ordinare i libri alfabeticamente in base al loro titolo, ignorando maiuscole e minuscole
    * 
    * @param[in] other Altro libro da confrontare con quello corrente
    * @return Un valore intero: negativo se il titolo di other precede quello corrente; zero, se sono uguali; positivo, se il titolo di other segue quello dell'oggetto corrente
    * 
    * @pre other != null
    * @post Restituisce un valore coerente con l'ordinamento alfabetico dei titoli
    * 
    * @see Comparable
    * 
    */
    @Override
    public int compareTo(Book other) {
    }
    
}