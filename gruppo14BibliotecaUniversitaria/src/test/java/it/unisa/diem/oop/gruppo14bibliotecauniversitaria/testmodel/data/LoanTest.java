/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

/**
 * @file LoanTest.java
 *
 * @author maramariano
 * @date 12-12-2025
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class LoanTest
 * @brief Classe di test per la classe Loan.
 * Si assumono le classi Book e User reali siano correttamente implementate nel package 'model.data'.
 */
class LoanTest {

    // Oggetti di supporto
    private Book bookA;
    private Book bookB;
    private User user1;
    private User user2;

    // Date di riferimento
    private static final LocalDate DUE_DATE_FUTURE = LocalDate.of(2026, 1, 15);
    private static final LocalDate DUE_DATE_SOON = LocalDate.of(2025, 12, 20);

    private Loan loan1;
    private Loan loan2;
    private Loan loan3;

    @BeforeEach
    void setUp() {
        // Creazione di Book (Costruttore: title, authors, year, ISBN, copies)
        bookA = new Book("Design Patterns", "Gamma et al.", LocalDate.of(1994, 1, 1), "978-0201633610", 3);
        bookB = new Book("The Pragmatic Programmer", "Hunt, Thomas", LocalDate.of(1999, 1, 1), "978-0201616224", 1);
        
        // Creazione di User (Costruttore: name, surname, numberId, email)
        user1 = new User("Mario", "Rossi", "S123456", "mario.rossi@unisa.it"); // User 1
        user2 = new User("Paolo", "Bianchi", "D987654", "paolo.bianchi@unisa.it"); // User 2
        
        // Inizializzazione degli oggetti Loan
        loan1 = new Loan(bookA, user1, DUE_DATE_FUTURE); // Prestito con scadenza futura
        loan2 = new Loan(bookA, user2, DUE_DATE_SOON);    // Prestito con scadenza vicina
        loan3 = new Loan(bookB, user1, DUE_DATE_FUTURE); // Stesso utente di loan1, libro diverso
    }

    /**
     * @brief Test del costruttore e dei metodi getter.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(bookA, loan1.getBook());
        assertEquals(user1, loan1.getUser());
        assertEquals(DUE_DATE_FUTURE, loan1.getDueDate());
    }

    /**
     * @brief Test dei metodi setter.
     */
    @Test
    void testSetters() {
        loan1.setBook(bookB);
        loan1.setUser(user2);
        loan1.setDueDate(DUE_DATE_SOON);

        assertEquals(bookB, loan1.getBook());
        assertEquals(user2, loan1.getUser());
        assertEquals(DUE_DATE_SOON, loan1.getDueDate());
    }

    /**
     * @brief Test del metodo compareTo (ordinamento per data di scadenza).
     */
    @Test
    void testCompareTo() {
        // loan2 (20/12/2025) vs loan1 (15/01/2026) -> loan2 precede loan1 (risultato < 0)
        assertTrue(loan2.compareTo(loan1) < 0, "loan2 dovrebbe precedere loan1 (scadenza più vicina)");

        // loan1 (15/01/2026) vs loan2 (20/12/2025) -> loan1 segue loan2 (risultato > 0)
        assertTrue(loan1.compareTo(loan2) > 0, "loan1 dovrebbe seguire loan2 (scadenza più lontana)");

        // Confronto con la stessa data (risultato == 0)
        Loan loanSameDate = new Loan(bookB, user2, DUE_DATE_FUTURE);
        assertTrue(loan1.compareTo(loanSameDate) == 0, "I prestiti con la stessa data di scadenza dovrebbero essere uguali");
        
        // Test per la gestione dell'eccezione (pre-condizione other != null in Loan.compareTo)
        assertThrows(IllegalArgumentException.class, () -> {
            loan1.compareTo(null); // other == null
        }, "Dovrebbe lanciare IllegalArgumentException se l'argomento è null");
    }

    /**
     * @brief Test del metodo equals (uguaglianza logica basata su User, Book e DueDate).
     */
    @Test
    void testEquals() {
        // Oggetto identico (Riflessività)
        assertTrue(loan1.equals(loan1));

        // Loan equivalente (stesso User, Book e DueDate)
        Loan loanEquivalent = new Loan(bookA, user1, DUE_DATE_FUTURE);
        assertTrue(loan1.equals(loanEquivalent), "I prestiti con stessi User, Book e DueDate dovrebbero essere uguali");
        
        // loan1 vs loan2: Stesso Book, diverso User, diversa DueDate -> Non uguali
        assertFalse(loan1.equals(loan2), "User o DueDate diversi: non uguali");
        
        // loan1 vs loan3: Stesso User, diverso Book, stessa DueDate -> Non uguali
        assertFalse(loan1.equals(loan3), "Book diverso: non uguali");

        // Confronto con null
        assertFalse(loan1.equals(null));
        
        // Confronto con oggetto di classe diversa
        assertFalse(loan1.equals("Stringa di prova"));
    }

    /**
     * @brief Test del metodo toString.
     */
    @Test
    void testToString() {
        // Acquisisco i toString() reali delle classi Book e User
        String expectedBookString = bookA.toString();
        String expectedUserString = user1.toString();
        
        // Costruzione della stringa attesa, basata sul formato di Loan.toString()
        String expectedToString = new StringBuilder()
            .append("Data di restituzione: ").append(DUE_DATE_FUTURE)
            .append("\n Dati User : ").append(expectedUserString)
            .append("\n Dati Libro : ").append(expectedBookString)
            .toString();

        assertEquals(expectedToString, loan1.toString());
    }
}