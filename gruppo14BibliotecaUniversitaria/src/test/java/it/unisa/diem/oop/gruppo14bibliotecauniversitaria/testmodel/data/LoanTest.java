/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file LoanTest.java
 *
 * @author maramariano
 * @date 15-12-2025
 */

package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

/**
 * @file LoanTest.java
 *
 * @author maramariano
 * @date 15-12-2025
 */

package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


/**
 * @class LoanTest.java
 *
 * @brief Classe di test per la classe Loan
 * Questa classe contiene i test unitari per verificare la corretta implementazione
 * della logica di uguaglianza (equals), ordinamento (compareTo) e dei costruttori della classe Loan
 * */
public class LoanTest {

    private User user1;
    private User user2;
    private Book bookA;
    private Book bookB;
    private LocalDate dueDateEarly;
    private LocalDate dueDateLate;

    /**
     * @brief Imposta l'ambiente di test (fixture) prima di ogni test
     *
     * Inizializza gli oggetti Book, User e LocalDate necessari per i test.
     */
    @BeforeEach
    void setUp() {
        // Oggetti Utente 
        user1 = new User("Mario", "Rossi", "0612718900", "m.rossi@unisa.it"); ///< Utende di test 1
        user2 = new User("Luigi", "Bianchi", "0612708970", "l.bianchi@unisa.it"); /// Utente 2

        // Oggetti Libro
        bookA = new Book("Il Codice Segreto", "Autore Alfa", "2020", "ISBN-111", 5); ///< Libro di test 1
        bookB = new Book("La Chiave Manca", "Autore Beta", "2021", "ISBN-222", 1); ///< Libro 2

        // Date di Scadenza
        dueDateEarly = LocalDate.now().plusDays(7); ///< Data di test 1
        dueDateLate = LocalDate.now().plusDays(14); ///< Data 2
    }

    /**
     * @brief Testa il costruttore principale della classe Loan.
     *
     * Verifica che i campi vengano inizializzati correttamente.
     */
    @Test
    void testMainConstructor() {
        Loan loan = new Loan(bookA, user1, dueDateLate);

        assertNotNull(loan);
        assertEquals(bookA, loan.getBook(), "Il libro non è stato inizializzato correttamente.");
        assertEquals(user1, loan.getUser(), "L'utente non è stato inizializzato correttamente.");
        assertEquals(dueDateLate, loan.getDueDate(), "La data di scadenza non è stata inizializzata correttamente.");
    }

    /**
     * @brief Testa il costruttore di ricerca con input numerici.
     *
     * Verifica che gli oggetti User e Book vengano creati parzialmente con i campi corretti.
     */
    @Test
    void testSearchConstructor_NumericInput() {
        String matricola = "0612709000"; // Simula l'input di una Matricola (solo numeri)
        String isbn = "9788812345678"; // Simula l'input di un ISBN (solo numeri nel test)
        
        Loan searchLoan = new Loan(matricola, isbn); 

        assertNotNull(searchLoan.getUser(), "L'utente parziale non dovrebbe essere nullo.");
        // Verifica che la matricola/ID sia stata assegnata
        assertEquals(matricola, searchLoan.getUser().getNumberId(), "L'utente parziale non ha la Matricola corretta."); 
        
        assertNotNull(searchLoan.getBook(), "Il libro parziale non dovrebbe essere nullo.");
        // Verifica che l'ISBN sia stato assegnato
        assertEquals(isbn, searchLoan.getBook().getISBN(), "Il libro parziale non ha l'ISBN corretto."); 
        
        assertNull(searchLoan.getDueDate(), "La data di scadenza deve essere null per i prestiti di ricerca.");
    }

    /**
     * @brief Testa il costruttore di ricerca con input alfanumerici (dovrebbero essere Cognome/Titolo).
     *
     * Verifica che gli oggetti User e Book vengano creati parzialmente con i campi corretti.
     */
    @Test
    void testSearchConstructor_TextualInput() {
        String cognome = "Rossi";
        String titolo = "Codice";

        // Simulo l'input come se l'utente avesse digitato testo in entrambi gli input
        Loan searchLoan = new Loan(cognome, titolo); 

        assertNotNull(searchLoan.getUser(), "L'utente parziale non dovrebbe essere nullo.");

        assertEquals(cognome, searchLoan.getUser().getSurname(), "L'utente parziale non ha il campo cognome corretto."); 

        assertNotNull(searchLoan.getBook(), "Il libro parziale non dovrebbe essere nullo.");
        // Assumiamo che il costruttore parziale di Book assegni la query testuale a Title
        assertEquals(titolo, searchLoan.getBook().getTitle(), "Il libro parziale non ha il Titolo corretto."); 
}

    /**
     * @brief Testa il costruttore di ricerca con input null o vuoti.
     *
     * Verifica che gli oggetti Loan vengano creati ma con i riferimenti interni nulli se l'input è vuoto.
     */
    @Test
    void testSearchConstructor_NullOrEmptyInput() {
        // Test con primo input null
        Loan searchLoan = new Loan(null, "Test"); 
        assertNull(searchLoan.getUser(), "L'utente deve essere null se l'input 1 è null.");
        
        // Test con secondo input null
        searchLoan = new Loan("Valido", null);
        assertNotNull(searchLoan.getUser(), "L'utente non deve essere null se l'input 1 è valido.");
        assertNull(searchLoan.getBook(), "Il libro deve essere null se l'input 2 è null.");
    }
    
    /**
     * @brief Testa l'uguaglianza logica basata su Utente (Matricola) e Libro (ISBN).
     */
    @Test
    void testEquals_SameLoan() {
        Loan loan1 = new Loan(bookA, user1, dueDateEarly);
        Loan loan2 = new Loan(bookA, user1, dueDateLate); // Data diversa

        assertTrue(loan1.equals(loan2), "Prestiti con stesso Utente/Libro ma data diversa dovrebbero essere uguali.");
    }

    /**
     * @brief Testa l'uguaglianza con Utente diverso.
     */
    @Test
    void testEquals_DifferentUser() {
        Loan loan1 = new Loan(bookA, user1, dueDateEarly);
        Loan loan3 = new Loan(bookA, user2, dueDateEarly); 

        assertFalse(loan1.equals(loan3), "Prestiti con utente diverso dovrebbero essere diversi (Matricola diversa).");
    }

    /**
     * @brief Testa l'uguaglianza con Libro diverso.
     */
    @Test
    void testEquals_DifferentBook() {
        Loan loan1 = new Loan(bookA, user1, dueDateEarly);
        Loan loan4 = new Loan(bookB, user1, dueDateEarly);

        assertFalse(loan1.equals(loan4), "Prestiti con libro diverso dovrebbero essere diversi (ISBN diverso).");
    }
    
    /**
     * @brief Testa che l'ordinamento sia basato sulla data di scadenza (dueDate).
     */
    @Test
    void testCompareTo_ByDueDate() {
        Loan loanEarly = new Loan(bookA, user1, dueDateEarly);
        Loan loanLate = new Loan(bookB, user2, dueDateLate); 
        
        // loanEarly vs loanLate (Precedente vs Successivo)
        assertTrue(loanEarly.compareTo(loanLate) < 0, "Il prestito con data precedente dovrebbe essere minore (< 0).");
        
        // loanLate vs loanEarly (Successivo vs Precedente)
        assertTrue(loanLate.compareTo(loanEarly) > 0, "Il prestito con data successiva dovrebbe essere maggiore (> 0).");

        // Stessa data di scadenza
        Loan loanSameDate = new Loan(bookB, user1, dueDateEarly);
        assertTrue(loanEarly.compareTo(loanSameDate) == 0, "Prestiti con la stessa data dovrebbero essere uguali (0).");
    }

    /**
     * @brief Testa la gestione di argomenti null in compareTo.
     *
     * Verifica che venga lanciata IllegalArgumentException.
     */
    @Test
    void testCompareTo_NullArgumentThrowsException() {
        Loan loan = new Loan(bookA, user1, dueDateEarly);

        assertThrows(IllegalArgumentException.class, () -> {
            loan.compareTo(null);
        }, "compareTo dovrebbe lanciare IllegalArgumentException se l'argomento è null.");
    }
    
}