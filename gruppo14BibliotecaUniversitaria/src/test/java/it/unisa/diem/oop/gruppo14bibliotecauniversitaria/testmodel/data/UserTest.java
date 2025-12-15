/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

/**
 * @file UserTest.java
 *
 * @author maramariano
 * @date 12-12-2025
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * @class UserTest
 * @brief Classe di test per la classe User
 *
 * Verifica il corretto funzionamento dei costruttori, getter/setter,
 * metodi di confronto (compareTo, equals, hashCode), gestione prestiti (findLoans)
 * e toString.
 *
 * @see User
 */
class UserTest {

    /**
     * @test Verifica costruttore completo con parametri validi
     * @pre name, surname, numberId, email != null
     * @post Oggetto User inizializzato correttamente
     */
    @Test
    void testConstructorValidData() {
        User u = new User("Mario", "Rossi", "12345", "mario.rossi@example.com");
        assertEquals("Mario", u.getName());
        assertEquals("Rossi", u.getSurname());
        assertEquals("12345", u.getNumberId());
        assertEquals("mario.rossi@example.com", u.getEmail());
        assertNotNull(u.getBooksOnloan());
        assertTrue(u.getBooksOnloan().isEmpty());
    }

    /**
     * @test Verifica costruttore di ricerca con input numerico (matricola)
     * @pre inputRicerca contiene solo numeri
     * @post Campo numberId inizializzato
     */
    @Test
    void testSearchConstructorWithNumericInput() {
        User u = new User("98765");
        assertEquals("98765", u.getNumberId());
        assertNull(u.getSurname());
    }

    /**
     * @test Verifica costruttore di ricerca con input testuale (cognome)
     * @pre inputRicerca contiene lettere
     * @post Campo surname inizializzato
     */
    @Test
    void testSearchConstructorWithTextInput() {
        User u = new User("Bianchi");
        assertEquals("Bianchi", u.getSurname());
        assertNull(u.getNumberId());
    }

    /**
     * @test Verifica costruttore di ricerca con input null o vuoto
     * @pre inputRicerca == null || inputRicerca.trim().isEmpty()
     * @post Nessun campo inizializzato
     */
    @Test
    void testSearchConstructorWithNullOrEmptyInput() {
        User u1 = new User((String) null);
        assertNull(u1.getSurname());
        assertNull(u1.getNumberId());

        User u2 = new User("");
        assertNull(u2.getSurname());
        assertNull(u2.getNumberId());
    }

    /**
     * @test Verifica setter aggiornano correttamente i valori
     * @post Campi aggiornati con i nuovi valori
     */
    @Test
    void testSettersUpdateValues() {
        User u = new User("Mario", "Rossi", "12345", "mario.rossi@example.com");
        u.setName("Luigi");
        u.setSurname("Bianchi");
        u.setNumberId("54321");
        u.setEmail("luigi.bianchi@example.com");

        assertEquals("Luigi", u.getName());
        assertEquals("Bianchi", u.getSurname());
        assertEquals("54321", u.getNumberId());
        assertEquals("luigi.bianchi@example.com", u.getEmail());
    }

    /**
     * @test Verifica setBooksOnLoan aggiorna la mappa
     * @post booksOnLoan contiene la nuova mappa
     */
    @Test
    void testSetBooksOnLoan() {
        User u = new User("Mario", "Rossi", "12345", "mario.rossi@example.com");
        TreeMap<Book, LocalDate> newMap = new TreeMap<>();
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        newMap.put(b, LocalDate.of(2025, 12, 31));

        u.setBooksOnLoan(newMap);
        assertEquals(1, u.getBooksOnloan().size());
        assertTrue(u.getBooksOnloan().containsKey(b));
    }

    /**
     * @test Verifica findLoans aggiunge un libro con data di restituzione
     * @pre b != null && data != null
     * @post booksOnLoan contiene la coppia (b, data)
     */
    @Test
    void testFindLoans() {
        User u = new User("Mario", "Rossi", "12345", "mario.rossi@example.com");
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        LocalDate d = LocalDate.of(2025, 12, 31);

        u.findLoans(b, d);
        Map<Book, LocalDate> loans = u.getBooksOnloan();
        assertEquals(1, loans.size());
        assertEquals(d, loans.get(b));
    }

    /**
     * @test Verifica compareTo con cognomi diversi
     * @post Risultato < 0 se cognome corrente precede alfabeticamente
     */
    @Test
    void testCompareToDifferentSurnames() {
        User u1 = new User("Mario", "Bianchi", "123", "a@example.com");
        User u2 = new User("Mario", "Rossi", "456", "b@example.com");
        assertTrue(u1.compareTo(u2) < 0);
    }

    /**
     * @test Verifica compareTo con stesso cognome ma nomi diversi
     * @post Risultato < 0 se nome corrente precede alfabeticamente
     */
    @Test
    void testCompareToSameSurnameDifferentNames() {
        User u1 = new User("Anna", "Rossi", "123", "a@example.com");
        User u2 = new User("Mario", "Rossi", "456", "b@example.com");
        assertTrue(u1.compareTo(u2) < 0);
    }

    /**
     * @test Verifica equals con stesso numberId
     * @post equals restituisce true
     */
    @Test
    void testEqualsSameNumberId() {
        User u1 = new User("Mario", "Rossi", "12345", "a@example.com");
        User u2 = new User("Luigi", "Bianchi", "12345", "b@example.com");
        assertTrue(u1.equals(u2));
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    /**
     * @test Verifica equals con numberId diverso
     * @post equals restituisce false
     */
    @Test
    void testEqualsDifferentNumberId() {
        User u1 = new User("Mario", "Rossi", "12345", "a@example.com");
        User u2 = new User("Mario", "Rossi", "54321", "b@example.com");
        assertFalse(u1.equals(u2));
    }

    /**
     * @test Verifica equals con null o classe diversa
     * @post equals restituisce false
     */
    @Test
    void testEqualsNullOrDifferentClass() {
        User u = new User("Mario", "Rossi", "12345", "a@example.com");
        assertFalse(u.equals(null));
        assertFalse(u.equals("NonUser"));
    }

    /**
     * @test Verifica hashCode con numberId null
     * @post Restituisce 0
     */
    @Test
    void testHashCodeWithNullNumberId() {
        User u = new User("Mario", "Rossi", null, "a@example.com");
        assertEquals(0, u.hashCode());
    }

    /**
     * @test Verifica toString contiene tutti i campi principali
     * @post Stringa include name, surname, numberId, email
     */
    @Test
    void testToStringContainsAllFields() {
        User u = new User("Mario", "Rossi", "12345", "a@example.com");
        String s = u.toString();
        assertTrue(s.contains("Mario"));
        assertTrue(s.contains("Rossi"));
        assertTrue(s.contains("12345"));
        assertTrue(s.contains("a@example.com"));
    }
}