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
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data;

import java.io.Serializable;
        
/**
 * @class Book
 * @brief Gestisce i dati dei libri della biblioteca
 * 
 * La classe Book gestisce i dati principali di un libro: titolo, autori, anno di pubblicazione, codice ISBN e numero di copie disponibili
 * Implementa l'interfaccia Comparable per consentire l'ordinamento per titolo
 * 
 * @invariant availableCopies >= 0
 * 
 * @see Comparable
 * 
 */
public class Book implements Comparable<Book>,Serializable {
    
    private String title; ///< Titolo del libro
    private String authors; ///< Autore/i
    private String publicationYear; ///< Anno di pubblicazione (formato AAAA)
    private String ISBN; ///< Codice identificativo univoco
    private int availableCopies; ///< Numero di copie disponibili
    
    /**
    * @brief Costruttore della classe Book per l'inizializzazione completa dei dati
    * 
    * @param title Titolo del libro
    * @param authors Autori del libro
    * @param publicationYear Anno di pubblicazione del libro
    * @param ISBN Codice ISBN
    * @param availableCopies Numero di copie disponibili
    * 
    * @throws IllegalArgumentException se availableCopies è negativo o se publicationYear non è una stringa di 4 cifre
    * 
    * @pre availableCopies >= 0
    * @post L'oggetto Book è correttamente inizializzato
    * 
    */
    public Book(String title, String authors, String publicationYear, String ISBN, int availableCopies) {
        if (availableCopies < 0) {
            throw new IllegalArgumentException("Il numero iniziale di copie disponibili non può essere negativo: " + availableCopies);
        }
        
        if (publicationYear != null && !publicationYear.isEmpty()) {
            if (!publicationYear.matches("\\d{4}")) { // Forziamo 4 cifre per l'anno
                throw new IllegalArgumentException("L'anno di pubblicazione deve essere una stringa contenente esattamente 4 cifre");
            }
        }
        
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.availableCopies = availableCopies;
    }
    
    /**
     * @brief Costruttore della classe Book per la ricerca parziale
     * 
     * Inizializza l'oggetto Book compilando solo il campo necessario 
     * Gli altri campi rimangono nulli
     * 
     * @param inputRicerca Stringa di input fornita dall'utente
     * 
     * @post L'oggetto Book è inizializzato con i campi necessari alla ricerca parziale
     * 
     */
    public Book (String inputRicerca ){  //overload del costruttore,sarà utilizzato solo ai fini della ricerca dell'utente
        
     // Inizializziamo tutto a null di default
        this.title = null;
        this.authors = null;
        this.publicationYear = null;
        this.ISBN = null;
        this.availableCopies = 0;

    // Protezione anti-crash: se la stringa è null o vuota, ci fermiamo qui
         if (inputRicerca == null || inputRicerca.trim().isEmpty()) {
            return; 
        }

        String cleanInput = inputRicerca.trim();

        // Pattern per ISBN: numeri, trattini e opzionalmente 'X' alla fine
        // Usiamo una REGEX interna per non dipendere da variabili esterne
        final String isbnPattern = "^[0-9]{9,17}([Xx])?$";
        
        // Logica di Discriminazione:
        // Se l'input sembra un codice ISBN (solo numeri, trattini, e forse X finale)
        if (cleanInput.matches(isbnPattern)) {
            // Se è un ISBN, inizializza SOLO il campo ISBN
            this.ISBN = cleanInput;

        } else if (cleanInput.contains(" ")) {
            // Se contiene spazi, è più probabile che sia un Titolo (o Autori)
            // Inizializza SOLO il campo Titolo
            this.title = cleanInput;

        } else {
            // Non è un ISBN e non ha spazi. Trattalo come Titolo (parziale) o Autore
            this.title = cleanInput;
        }
    }

    // --- SETTER ---
    
    /**
    * @brief Imposta il valore del titolo
    * 
    * @param title Nuovo titolo da assegnare
    * 
    * @post Il titolo è aggiornato
    * 
    */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * @brief Imposta gli autori del libro
    * 
    * @param authors Nuovi autori da assegnare
    * 
    * @post Gli autori del libro sono aggiornati
    * 
    */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
    * @brief Imposta l'anno di pubblicazione del libro
    * 
    * @param publicationYear Nuovo anno da assegnare
    * 
    * @post La data di pubblicazione è aggiornata
    * 
    */
    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
    * @brief Imposta il codice ISBN del libro
    * 
    * @param ISBN Nuovo codice da assegnare
    * 
    * @post Il codice identificativo è aggiornato
    * 
    */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
    * @brief Imposta il numero di copie disponibili
    * 
    * @param availableCopies Nuovo numero di copie disponibili
    * 
    * @throws IllegalArgumentException se availableCopies è negativo
    * 
    * @pre availableCopies >= 0
    * @post Il numero di copie disponibili è aggiornato
    * 
    */
    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0) {
            // Lancia un'eccezione se il valore è negativo
            throw new IllegalArgumentException("Il numero di copie disponibili non può essere negativo: " + availableCopies);
        }
        this.availableCopies = availableCopies;
    }

    // --- GETTER ---
    
    /**
    * @brief Restituisce il valore di title
    * 
    * @return Titolo del libro (stringa)
    * 
    */
    public String getTitle() {
        return title;
    }

    /**
    * @brief Restituisce il valore di authors
    * 
    * @return Autore/i del libro (stringa)
    * 
    */
    public String getAuthors() {
        return authors;
    }

    /**
    * @brief Restituisce il valore di publicationYear
    * 
    * @return Anno di pubblicazione del libro (String)
    * 
    */
    public String getPublicationYear() {
        return publicationYear;
    }

    /**
    * @brief Restituisce il valore di ISBN
    * 
    * @return Codice identificativo del libro (stringa)
    * 
    */
    public String getISBN() {
        return ISBN;
    }

    /**
    * @brief Restituisce il valore di availableCopies
    * 
    * @return Disponibilità di un libro nel catalogo (int)
    * 
    */
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    /**
    * @brief Confronta due libri per ordinamento alfabetico dei titoli
    * 
    * L'ordinamento avviene in base al titolo (case-insensitive)
    * Se i titoli sono uguali, il confronto viene effettuato sugli autori (case-insensitive)
    * 
    * @param other Altro libro da confrontare con quello corrente
    * @return Un valore intero: negativo se il titolo di other precede quello corrente; zero, se sono uguali; positivo, se il titolo di other segue quello dell'oggetto corrente
    * 
    * @pre other != null
    * @post Restituisce un valore coerente con l'ordinamento alfabetico dei titoli e degli autori
    * 
    * @see Comparable
    * 
    */
    @Override
    public int compareTo(Book other){
        int comparing = this.title.compareToIgnoreCase(other.title); 
        // confronto case-insensitive basato sul titolo
        if(comparing != 0){  
            // se i titoli sono diversi ritorno il risultato del confronto
            return comparing;
        }
        else {
            // se i titoli sono uguali confronto per autori
            return this.authors.compareToIgnoreCase(other.authors);
        }
    }
    /**
    * @brief Confronta l'oggetto corrente con un altro per verificarne l'uguaglianza
    * 
    * Due oggetti Book sono considerati uguali se hanno lo stesso codice ISBN
    * 
    * @param obj Oggetto da confrontare con quello corrente
    * 
    * @return true se i due libri hanno lo stesso codice identificativo, false altrimenti
    * 
    * @pre obj può essere null
    * @post Restituisce un valore booleano coerente con l'identità del libro
    * 
    */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return ISBN != null && ISBN.equals(other.ISBN);
    }
    
    /**
    * @brief Restituisce il codice hash dell'oggetto Book
    * 
    * Il valore restituito è calcolato in base al codice ISBN
    * 
    * @return Valore intero che rappresenta l'hash del libro
    * 
    * @post Restituisce 0 se ISBN è null, altrimenti l'hashCode della stringa ISBN
    */
    @Override
    public int hashCode(){
        return ISBN != null ? ISBN.hashCode() : 0;
    }
    
    /**
    * @brief Restituisce una rappresentazione testuale dell'oggetto Book
    * 
    * La stringa generata include i campi principali che caratterizzano il libro
    * 
    * @return Stringa che descrive l'oggetto Book
    * 
    * @post Restituisce una stringa non null contenente i dati del libro
    */
    @Override 
    public String toString(){
        StringBuffer sb = new StringBuffer();  //creo uno StringBuffer per facilitare la creazione dell'output 
        sb.append("Book{");
        sb.append("title='").append(title).append('\'');
        sb.append(", authors='").append(authors).append('\'');
        sb.append(", publicationYear='").append(publicationYear).append('\'');
        sb.append(", ISBN='").append(ISBN).append('\'');
        sb.append(", availableCopies=").append(availableCopies).append('}');
       return sb.toString();
    }
}

