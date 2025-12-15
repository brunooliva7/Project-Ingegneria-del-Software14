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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * @class LoanTest
 * @brief Classe di test per la classe Loan
 *
 * Verifica il corretto funzionamento dei costruttori, getter/setter,
 * metodi di confronto (compareTo, equals) e toString.
 *
 * @see Loan
 */
class LoanTest {

    /**
     * @test Verifica che il costruttore con parametri validi inizializzi correttamente i campi
     * @pre book != null, user != null, dueDate != null
     * @post Oggetto Loan inizializzato con i valori attesi
     */
    @Test
    void testConstructorValidData() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        User u = new User("Mario Rossi");
        LocalDate d = LocalDate.of(2025, 12, 31);

        Loan l = new Loan(b, u, d);

        assertEquals(b, l.getBook());
        assertEquals(u, l.getUser());
        assertEquals(d, l.getDueDate());
    }

    /**
     * @test Verifica costruttore di ricerca con input numerico (matricola)
     * @pre inputRicerca1 e inputRicerca2 contengono solo numeri
     * @post Campi user e book inizializzati
     */
    @Test
    void testSearchConstructorWithNumericInputs() {
        Loan l = new Loan("12345", "9876543210");
        assertNotNull(l.getUser());
        assertNotNull(l.getBook());
    }

    /**
     * @test Verifica costruttore di ricerca con input testuale (cognome/titolo)
     * @pre inputRicerca1 e inputRicerca2 contengono lettere
     * @post Campi user e book inizializzati
     */
    @Test
    void testSearchConstructorWithTextInputs() {
        Loan l = new Loan("Rossi", "Divina Commedia");
        assertNotNull(l.getUser());
        assertNotNull(l.getBook());
    }

    /**
     * @test Verifica costruttore di ricerca con input null o vuoto
     * @pre inputRicerca1 == null || inputRicerca1.trim().isEmpty()
     * @post Nessun campo inizializzato
     */
    @Test
    void testSearchConstructorWithNullOrEmptyInput() {
        Loan l1 = new Loan(null, "Titolo");
        assertNull(l1.getUser());

        Loan l2 = new Loan("", "");
        assertNull(l2.getUser());
        assertNull(l2.getBook());
    }

    /**
     * @test Verifica compareTo con date diverse
     * @pre dueDate di l1 < dueDate di l2
     * @post compareTo restituisce valore negativo
     */
    @Test
    void testCompareToDifferentDates() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        User u = new User("Mario Rossi");

        Loan l1 = new Loan(b, u, LocalDate.of(2025, 1, 1));
        Loan l2 = new Loan(b, u, LocalDate.of(2025, 12, 31));

        assertTrue(l1.compareTo(l2) < 0);
    }

    /**
     * @test Verifica compareTo con date uguali
     * @pre dueDate di l1 == dueDate di l2
     * @post compareTo restituisce 0
     */
    @Test
    void testCompareToSameDates() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        User u = new User("Mario Rossi");

        LocalDate d = LocalDate.of(2025, 12, 31);
        Loan l1 = new Loan(b, u, d);
        Loan l2 = new Loan(b, u, d);

        assertEquals(0, l1.compareTo(l2));
    }

    /**
     * @test Verifica compareTo con parametro null
     * @pre other == null
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testCompareToWithNullThrowsException() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        User u = new User("Mario Rossi");
        Loan l = new Loan(b, u, LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> l.compareTo(null));
    }

    /**
     * @test Verifica equals con stesso utente e stesso libro
     * @pre user e book coincidono
     * @post equals restituisce true
     */
    @Test
    void testEqualsSameUserAndBook() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        User u = new User("Mario Rossi");

        Loan l1 = new Loan(b, u, LocalDate.now());
        Loan l2 = new Loan(b, u, LocalDate.now().plusDays(1));

        assertTrue(l1.equals(l2));
    }

    /**
     * @test Verifica equals con utente diverso
     * @pre user differente
     * @post equals restituisce false
     */
    @Test
    void testEqualsDifferentUser() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        Loan l1 = new Loan(b, new User("Mario Rossi"), LocalDate.now());
        Loan l2 = new Loan(b, new User("Luigi Bianchi"), LocalDate.now());

        assertFalse(l1.equals(l2));
    }

    /**
     * @test Verifica equals con libro diverso
     * @pre book differente
     * @post equals restituisce false
     */
    @Test
    void testEqualsDifferentBook() {
        User u = new User("Mario Rossi");
        Loan l1 = new Loan(new Book("Titolo1", "Autore", "2020", "123", 1), u, LocalDate.now());
        Loan l2 = new Loan(new Book("Titolo2", "Autore", "2020", "456", 1), u, LocalDate.now());

        assertFalse(l1.equals(l2));
    }

    /**
     * @test Verifica toString: la stringa deve contenere data, user e book
     * @post La rappresentazione testuale include i dati principali
     */
    @Test
    void testToStringContainsAllFields() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        User u = new User("Mario Rossi");
        Loan l = new Loan(b, u, LocalDate.of(2025, 12, 31));

        String s = l.toString();
        assertTrue(s.contains("Data di restituzione"));
        assertTrue(s.contains("User"));
        assertTrue(s.contains("Libro"));
    }
}