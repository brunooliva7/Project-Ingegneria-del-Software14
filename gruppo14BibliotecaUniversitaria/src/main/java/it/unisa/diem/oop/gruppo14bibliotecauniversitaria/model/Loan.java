/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Book;
import java.time.LocalDate;

/**
 * @class Loan
 * @brief Rappresenta un prestito di un libro da parte di un utente.
 *
 * La classe Loan associa un libro ad un utente e ne registra la data di restituzione.
 * Implementa l'interfaccia Comparable per consentire l'ordinamento dei prestiti
 * secondo un criterio definito (ad esempio per data di scadenza).
 */
public class Loan implements Comparable<Loan>{
    
    private Book book; ///<Libro oggetto del prestito 
    private User user; ///<Utente che ha preso in prestito il libro
    private LocalDate dueDate; ///<data di restituzione
    
    /**
     * @brief Costruttore della classe Loan.
     * @param  book Libro prestato
     * @param user Utente che ha preso il libro
     * @param dueDate Data di scadenza del prestito
     * 
     * @pre i diversi attributi devono essere diversi da zero 
     * @post loan è inizializzato
     */
    public Loan(Book book, User user, LocalDate dueDate) {
        this.book = book; 
        this.user = user;  
        this.dueDate = dueDate; 
    }
    
    /**
     * @brief Restituisce il libro associato al prestito.
     * @return Oggetto Book
     * 
     * @pre l'oggetto loan deve essere stato inizializzato 
     * @post restituisce il riferimento al libro associato
     */
    public Book getBook() {
        return book;
    }
    /**
     * @brief Restituisce l'utente associato al prestito.
     * @return Oggetto User
     * 
     * @pre l'oggetto loan deve essere stato inizializzato
     * @post restituisce il riferimento all'user associato
     */

    public User getUser() {
        return user;
    }
    /**
     * @brief Restituisce l'utente associato al prestito.
     * @return Oggetto User
     * 
     * @pre l'oggetto loan deve essere stao inizializzato
     * @post resituisce la data di scadenza
     */

    public LocalDate getDueDate() {
        return dueDate;
    }
    /**
     * @brief Confronta il prestito corrente con un altro prestito.
     * @param other Altro prestito da confrontare
     * @return Valore intero per l'ordinamento
     * 
     * @pre other != null
     * @post restituisce un valore <0,=0 o >0
     */

    @Override
    public int compareTo(Loan other){       
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
     * @brief Imposta l'utente associato al prestito.
     * @param user Nuovo utente (non deve essere null)
     *
     * @pre user != null
     * @post  user è aggiornato con il nuovo valore
     */

    public void setUser(User user) {
        this.user = user;
    }
    
     /**
     * @brief Imposta la data di scadenza del prestito.
     * @param dueDate Nuova data di scadenza (non deve essere null e >= data corrente)
     *
     * @pre dueDate != null
     * @post dueDate è aggiornato con il nuovo valore
     */
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
