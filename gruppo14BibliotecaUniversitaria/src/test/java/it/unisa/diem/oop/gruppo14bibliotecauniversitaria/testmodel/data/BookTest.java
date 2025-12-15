/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

/**
 * @file BookTest.java
 *
 * @author maramariano
 * @date 12-12-2025
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @class BookTest
 * @brief Classe di test per la classe Book
 *
 * Verifica il corretto funzionamento dei costruttori, metodi getter/setter,
 * invarianti, metodi di confronto (compareTo, equals, hashCode) e toString.
 *
 * @see Book
 */
class BookTest {

    /**
     * @test Verifica che il costruttore con parametri validi inizializzi correttamente i campi
     * @pre availableCopies >= 0, publicationYear formato AAAA
     * @post Oggetto Book inizializzato con i valori attesi
     */
    @Test
    void testConstructorValidData() {
        Book b = new Book("Titolo", "Autore", "2020", "1234567890123", 5);
        assertEquals("Titolo", b.getTitle());
        assertEquals("Autore", b.getAuthors());
        assertEquals("2020", b.getPublicationYear());
        assertEquals("1234567890123", b.getISBN());
        assertEquals(5, b.getAvailableCopies());
    }

    /**
     * @test Verifica che il costruttore lanci un'eccezione se availableCopies < 0
     * @pre availableCopies < 0
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testConstructorNegativeCopiesThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("Titolo", "Autore", "2020", "1234567890123", -1));
    }

    /**
     * @test Verifica che il costruttore lanci un'eccezione se publicationYear non è di 4 cifre
     * @pre publicationYear non rispetta regex \\d{4}
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testConstructorInvalidYearThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("Titolo", "Autore", "20AB", "1234567890123", 1));
    }

    /**
     * @test Verifica che il costruttore accetti anno null o vuoto
     * @pre publicationYear == null || publicationYear == ""
     * @post Oggetto Book creato senza eccezioni
     */
    @Test
    void testConstructorYearNullOrEmptyAccepted() {
        Book b1 = new Book("Titolo", "Autore", null, "1234567890123", 1);
        assertNull(b1.getPublicationYear());

        Book b2 = new Book("Titolo", "Autore", "", "1234567890123", 1);
        assertEquals("", b2.getPublicationYear());
    }

    /**
     * @test Verifica il costruttore di ricerca con input ISBN valido
     * @pre inputRicerca rispetta regex ISBN
     * @post Campo ISBN inizializzato
     */
    @Test
    void testSearchConstructorWithISBN() {
        Book b = new Book("123456789X");
        assertEquals("123456789X", b.getISBN());
        assertNull(b.getTitle());
    }

    /**
     * @test Verifica il costruttore di ricerca con input contenente spazi (titolo o autori)
     * @pre inputRicerca contiene spazi
     * @post Campo title inizializzato
     */
    @Test
    void testSearchConstructorWithTitleContainingSpaces() {
        Book b = new Book("Il Signore degli Anelli");
        assertEquals("Il Signore degli Anelli", b.getTitle());
        assertNull(b.getISBN());
    }

    /**
     * @test Verifica il costruttore di ricerca con input singola parola
     * @pre inputRicerca non contiene spazi e non è ISBN
     * @post Campo title inizializzato
     */
    @Test
    void testSearchConstructorWithSingleWord() {
        Book b = new Book("Dante");
        assertEquals("Dante", b.getTitle());
        assertNull(b.getISBN());
    }

    /**
     * @test Verifica il costruttore di ricerca con input null o vuoto
     * @pre inputRicerca == null || inputRicerca.trim().isEmpty()
     * @post Nessun campo inizializzato
     */
    @Test
    void testSearchConstructorWithNullOrEmptyInput() {
        Book b1 = new Book((String) null);
        assertNull(b1.getTitle());
        assertNull(b1.getISBN());

        Book b2 = new Book("");
        assertNull(b2.getTitle());
        assertNull(b2.getISBN());
    }

    /**
     * @test Verifica che i setter aggiornino correttamente i valori
     * @post Campi aggiornati con i nuovi valori
     */
    @Test
    void testSettersUpdateValues() {
        Book b = new Book("Titolo", "Autore", "2020", "1234567890123", 1);
        b.setTitle("Nuovo Titolo");
        b.setAuthors("Nuovo Autore");
        b.setPublicationYear("2021");
        b.setISBN("9876543210987");
        b.setAvailableCopies(10);

        assertEquals("Nuovo Titolo", b.getTitle());
        assertEquals("Nuovo Autore", b.getAuthors());
        assertEquals("2021", b.getPublicationYear());
        assertEquals("9876543210987", b.getISBN());
        assertEquals(10, b.getAvailableCopies());
    }

    /**
     * @test Verifica che setAvailableCopies lanci eccezione se valore negativo
     * @pre availableCopies < 0
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testSetAvailableCopiesNegativeThrowsException() {
        Book b = new Book("Titolo", "Autore", "2020", "1234567890123", 1);
        assertThrows(IllegalArgumentException.class, () -> b.setAvailableCopies(-5));
    }

    /**
     * @test Verifica compareTo con titoli diversi
     * @post Risultato < 0 se titolo corrente precede alfabeticamente
     */
    @Test
    void testCompareToDifferentTitles() {
        Book b1 = new Book("A", "Autore1", "2020", "111", 1);
        Book b2 = new Book("B", "Autore2", "2020", "222", 1);
        assertTrue(b1.compareTo(b2) < 0);
    }

    /**
     * @test Verifica compareTo con titoli uguali e autori diversi
     * @post Risultato < 0 se autore corrente precede alfabeticamente
     */
    @Test
    void testCompareToSameTitleDifferentAuthors() {
        Book b1 = new Book("Titolo", "AutoreA", "2020", "111", 1);
        Book b2 = new Book("Titolo", "AutoreB", "2020", "222", 1);
        assertTrue(b1.compareTo(b2) < 0);
    }

    /**
     * @test Verifica compareTo con titoli e autori uguali
     * @post Risultato == 0
     */
    @Test
    void testCompareToSameTitleAndAuthors() {
        Book b1 = new Book("Titolo", "Autore", "2020", "111", 1);
        Book b2 = new Book("Titolo", "Autore", "2020", "222", 1);
        assertEquals(0, b1.compareTo(b2));
    }

    /**
     * @test Verifica equals con ISBN uguale
     * @post true se ISBN coincide
     */
    @Test
    void testEqualsSameISBN() {
        Book b1 = new Book("Titolo", "Autore", "2020", "123", 1);
        Book b2 = new Book("Altro", "Autore2", "2021", "123", 2);
        assertTrue(b1.equals(b2));
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    /**
     * @test Verifica equals con ISBN diverso
     * @post false se ISBN differente
     */
    @Test
    void testEqualsDifferentISBN() {
        Book b1 = new Book("Titolo", "Autore", "2020", "123", 1);
        Book b2 = new Book("Titolo", "Autore", "2020", "456", 1);
        assertFalse(b1.equals(b2));
    }

   /**
     * @test Verifica equals con null o classe diversa
     * @pre obj == null oppure obj non è istanza di Book
     * @post Restituisce false
     */
    @Test
    void testEqualsNullOrDifferentClass() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        assertFalse(b.equals(null));
        assertFalse(b.equals("NonBook"));
    }

    /**
     * @test Verifica hashCode con ISBN null
     * @pre ISBN == null
     * @post Restituisce 0
     */
    @Test
    void testHashCodeWithNullISBN() {
        Book b = new Book("Titolo", "Autore", "2020", null, 1);
        assertEquals(0, b.hashCode());
    }

    /**
     * @test Verifica toString: la stringa deve contenere tutti i campi principali
     * @post La rappresentazione testuale include titolo, autori, anno, ISBN e copie disponibili
     */
    @Test
    void testToStringContainsAllFields() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        String s = b.toString();
        assertTrue(s.contains("Titolo"));
        assertTrue(s.contains("Autore"));
        assertTrue(s.contains("2020"));
        assertTrue(s.contains("123"));
        assertTrue(s.contains("1"));
    }
}