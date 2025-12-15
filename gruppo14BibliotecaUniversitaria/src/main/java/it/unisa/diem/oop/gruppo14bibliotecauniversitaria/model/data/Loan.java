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
 * @brief Gestisce i dati relativi al prestito di un libro ad un utente.
 *
 * La classe Loan associa un libro ad un utente e registra la data di restituzione prevista.
 * Implementa l'interfaccia Comparable per consentire l'ordinamento dei prestiti in base alla data di scadenza.
 * Implementa Serializable per la persistenza dei dati.
 * 
 * @see Comparable<Loan>
 * @see Serializable
 */
public class Loan implements Comparable<Loan>,Serializable {
    
    private Book book; ///< Libro oggetto del prestito 
    private User user; ///< Utente che ha preso in prestito il libro
    private LocalDate dueDate; ///< Data di restituzione
    
    /**
     * @brief Costruttore principale della classe Loan.
     * * @param book Libro da prestare.
     * @param user Utente che richiede il prestito.
     * @param dueDate Data di scadenza del prestito.
     * * @pre book != null && user != null && dueDate != null.
     * @post Viene creato un nuovo oggetto Loan con i dati specificati.
     */
    public Loan(Book book, User user, LocalDate dueDate) {
        this.book = book; 
        this.user = user;  
        this.dueDate = dueDate; 
    }
    
    /**
     * @brief Costruttore secondario utilizzato per i filtri di ricerca.
     * * Questo costruttore crea un oggetto Loan "parziale" o "fittizio", inizializzando
     * User o Book solo con i dati parziali (Matricola/ISBN o Nome/Titolo) ricevuti dalle barre di ricerca.
     * * @param inputRicerca1 Stringa di input per l'Utente (può essere Matricola o Cognome).
     * @param inputRicerca2 Stringa di input per il Libro (può essere ISBN o Titolo).
     * * @pre Nessuna (gli input possono essere null o vuoti).
     * @post Viene creato un oggetto Loan configurato per il matching parziale nel metodo search().
     */
    
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
     * @brief Restituisce il libro associato al prestito.
     * * @return Oggetto Book associato.
     * * @pre L'oggetto Loan deve essere istanziato.
     * @post Ritorna il riferimento al libro.
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
     * @brief Verifica l'uguaglianza logica tra due prestiti.
     * * Due prestiti sono considerati uguali se coinvolgono lo stesso Utente (identificato dalla Matricola)
     * e lo stesso Libro (identificato dall'ISBN).
     * * @param obj L'oggetto da confrontare.
     * @return true se gli oggetti sono uguali, false altrimenti.
     * * @pre Nessuna.
     * @post Restituisce true solo se Matricola e ISBN coincidono.
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
    
   /**
     * @brief Restituisce il nome dell'utente.
     * @return String Nome dell'utente o stringa vuota se null.
     * @pre Nessuna.
     * @post Ritorna una stringa sicura per la visualizzazione in tabella.
     */
    
    public String getName() {
        return user != null ? user.getName() : "";
    }  
    
    /**
     * @brief Restituisce il cognome dell'utente.
     * @return String Cognome dell'utente o stringa vuota.
     * @pre Nessuna.
     * @post Ritorna una stringa sicura.
     */
    public String getSurname() {
        return user != null ? user.getSurname() : "";
    }
    
    /**
     * @brief Restituisce la matricola dell'utente.
     * @return String Matricola o stringa vuota.
     * @pre Nessuna.
     * @post Ritorna una stringa sicura.
     */
    public String getNumberId() {
        return user != null ? user.getNumberId() : "";
    }
    
    /**
     * @brief Restituisce il titolo del libro.
     * @return String Titolo o stringa vuota.
     * @pre Nessuna.
     * @post Ritorna una stringa sicura.
     */
    
    public String getTitle() {
        return book != null ? book.getTitle() : "";
    }
    
    /**
     * @brief Restituisce gli autori del libro.
     * @return String Autori o stringa vuota.
     * @pre Nessuna.
     * @post Ritorna una stringa sicura.
     */
    public String getAuthors() {
         return book != null ? book.getAuthors() : "";
    }
    
    /**
     * @brief Restituisce l'ISBN del libro.
     * @return String ISBN o stringa vuota.
     * @pre Nessuna.
     * @post Ritorna una stringa sicura.
     */
    public String getISBN() {
        return book != null ? book.getISBN() : "";
    }


}
