/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file Loan.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @class Loan
 * @brief Gestisce i dati relativi al prestito di un libro ad un utente
 *
 * La classe Loan associa un libro ad un utente e registra la data di restituzione.
 * Implementa l'interfaccia Comparable per consentire l'ordinamento dei prestiti per data di scadenza
 * 
 * @see Comparable
 * 
 */
public class Loan implements Comparable<Loan>,Serializable {
    
    private Book book; ///< Libro oggetto del prestito 
    private User user; ///< Utente che ha preso in prestito il libro
    private LocalDate dueDate; ///< Data di restituzione
    
    /**
     * @brief Costruttore della classe Loan
     * @param book Libro prestato
     * @param user Utente che ha preso il libro
     * @param dueDate Data di scadenza del prestito
     * 
     * @pre book != null && user != null && dueDate != null
     * @post L'oggetto Loan è inizializzato
     */
    public Loan(Book book, User user, LocalDate dueDate) {
        this.book = book; 
        this.user = user;  
        this.dueDate = dueDate; 
    }
    
    public Loan(String inputRicerca1, String inputRicerca2) {
         // 1. Inizializziamo tutto a null di default
        this.book= null;
        this.user = null;
        this.dueDate = null;
        

    // 2. Protezione anti-crash: se la stringa è null o vuota, ci fermiamo qui.
         if (inputRicerca1 == null || inputRicerca1.trim().isEmpty()) {
            return; 
        }

        String cleanInput = inputRicerca1.trim();

    // 3. Logica robusta: Usiamo le REGEX per capire se sono SOLO numeri
    // "\\d+" significa "uno o più numeri da 0 a 9"
        if (cleanInput.matches("\\d+")) {
        // È sicuramente una matricola (perché contiene solo numeri)
        this.user = new User(inputRicerca1);
        } else {
        // Contiene lettere o simboli, quindi è sicuramente un cognome
        this.user = new User(inputRicerca1);
        }
        
        if (inputRicerca2 == null || inputRicerca2.trim().isEmpty()) {
            return; 
        }

        String cleanInput1 = inputRicerca2.trim();

    // 3. Logica robusta: Usiamo le REGEX per capire se sono SOLO numeri
    // "\\d+" significa "uno o più numeri da 0 a 9"
        if (cleanInput1.matches("\\d+")) {
        // È sicuramente una matricola (perché contiene solo numeri)
        this.book = new Book(inputRicerca2);
        } else {
        // Contiene lettere o simboli, quindi è sicuramente un cognome
        this.book = new Book(inputRicerca2);
        }
    }
    
    /**
     * @brief Restituisce il libro associato al prestito
     * @return Oggetto Book
     * 
     * @pre L'oggetto loan deve essere stato inizializzato 
     * @post Restituisce il riferimento al libro associato
     */
    public Book getBook() {
        return book;
    }
    /**
     * @brief Restituisce l'utente associato al prestito.
     * @return Oggetto User
     * 
     * @pre L'oggetto loan deve essere stato inizializzato
     * @post Restituisce il riferimento all'user associato
     */

    public User getUser() {
        return user;
    }
    /**
     * @brief Restituisce l'utente associato al prestito.
     * @return Oggetto User
     * 
     * @pre L'oggetto loan deve essere stato inizializzato
     * @post Resituisce la data di scadenza
     */

    public LocalDate getDueDate() {
        return dueDate;
    }
    /**
     * @brief Confronta il prestito corrente con un altro prestito
     * @param other Altro prestito da confrontare
     * @return Valore intero: negativo se il titolo di other precede quello corrente; zero, se sono uguali; positivo, se il titolo di other segue quello dell'oggetto corrente
     * 
     * @pre other != null
     * @post Restituisce un valore <0, =0 o >0
     */

    @Override
    public int compareTo(Loan other){
        if(other == null || other.dueDate == null) throw new IllegalArgumentException();
        return this.dueDate.compareTo(other.dueDate);
    }
    /**
     * @brief Imposta il libro associato al prestito.
     * @param book Nuovo libro
     * 
     * @pre book != da null
     * @post book è aggionato con il nuovo valore 
     */

    public void setBook(Book book) {
        this.book = book;
    }
    
     /**
     * @brief Imposta l'utente associato al prestito
     * @param user Nuovo utente (non deve essere null)
     *
     * @pre user != null
     * @post  user è aggiornato con il nuovo valore
     */

    public void setUser(User user) {
        this.user = user;
    }
    
     /**
     * @brief Imposta la data di scadenza del prestito
     * @param dueDate Nuova data di scadenza (non deve essere null e >= data corrente)
     *
     * @pre dueDate != null
     * @post dueDate è aggiornato con il nuovo valore
     */
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    /**
    * Restituisce una rappresentazione in formato stringa completa
    * dell'oggetto Prestito, inclusi la data di restituzione, 
    * e i dati dettagliati dell'utente e del libro coinvolti.
    *
    * Il formato della stringa è strutturato su più righe per chiarezza.
    * * @return Una stringa contenente la data di restituzione e le 
    * rappresentazioni in stringa degli oggetti User e Book.
    */
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Data di restituzione: " + this.dueDate);
        sb.append("\n Dati User : " + user.toString());
        sb.append("\n Dati Libro : " + book.toString());
        
        return sb.toString();
    }
    
    /**
    * @brief Definisce l'uguaglianza logica tra due oggetti Loan (Prestito).
    * * Due oggetti Loan sono considerati logicamente uguali se fanno riferimento 
    * allo stesso Utente (User) e allo stesso Libro (Book).
    * 
    * * @param obj L'oggetto da confrontare con l'oggetto corrente.
    * 
    * @return true se l'oggetto specificato è logicamente uguale a questo oggetto Loan, 
    * false altrimenti.
    */
    @Override
   public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Loan other = (Loan) obj;

    // Confronto user.numberId senza chiamare User.equals()
    String thisUserId = (this.user != null) ? this.user.getNumberId() : null;
    String otherUserId = (other.user != null) ? other.user.getNumberId() : null;
    if (!Objects.equals(thisUserId, otherUserId)) return false;

    // Confronto book.ISBN senza chiamare Book.equals()
    String thisISBN = (this.book != null) ? this.book.getISBN() : null;
    String otherISBN = (other.book != null) ? other.book.getISBN() : null;
    return Objects.equals(thisISBN, otherISBN);
}
    
            public String getName() {
            return user != null ? user.getName() : "";
        }

        public String getSurname() {
            return user != null ? user.getSurname() : "";
        }

        public String getNumberId() {
            return user != null ? user.getNumberId() : "";
        }

        public String getTitle() {
            return book != null ? book.getTitle() : "";
        }

        public String getAuthors() {
            return book != null ? book.getAuthors() : "";
        }

        public String getISBN() {
            return book != null ? book.getISBN() : "";
        }


}
