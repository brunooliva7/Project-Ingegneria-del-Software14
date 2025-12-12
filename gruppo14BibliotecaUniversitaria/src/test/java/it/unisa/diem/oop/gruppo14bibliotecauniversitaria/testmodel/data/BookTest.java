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
 * @version 1.0
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class BookTest
 * @brief Classe di test per la classe Book utilizzando JUnit 5.
 */
class BookTest {

    private Book book1;
    private Book book2;
    private Book book3;

    // Data di riferimento per l'anno di pubblicazione
    private static final LocalDate DATE_1997 = LocalDate.of(1997, 10, 26);
    private static final LocalDate DATE_2000 = LocalDate.of(2000, 1, 1);

    /**
     * @brief Configurazione iniziale eseguita prima di ogni metodo di test.
     */
    @BeforeEach
    void setUp() {
        book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", DATE_1997, "978-0618260214", 5);
        book2 = new Book("A Different Title", "Another Author", DATE_2000, "978-0618260214", 10); // L'ISBN deve essere identico qui

        // Oggetto completamente diverso
        book3 = new Book("1984", "George Orwell", DATE_2000, "978-0451524935", 2);
    }

    /**
     * @brief Test del costruttore e dei metodi getter.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals("The Lord of the Rings", book1.getTitle());
        assertEquals("J.R.R. Tolkien", book1.getAuthors());
        assertEquals(DATE_1997, book1.getPublicationYear());
        assertEquals("978-0618260214", book1.getISBN());
        assertEquals(5, book1.getAvailableCopies());
    }

    /**
     * @brief Test di tutti i metodi setter.
     */
    @Test
    void testSetters() {
        LocalDate newDate = LocalDate.of(2023, 5, 15);

        book1.setTitle("The Hobbit");
        book1.setAuthors("Tolkien J.R.R.");
        book1.setPublicationYear(newDate);
        book1.setISBN("978-0007487295");
        book1.setAvailableCopies(12);

        assertEquals("The Hobbit", book1.getTitle());
        assertEquals("Tolkien J.R.R.", book1.getAuthors());
        assertEquals(newDate, book1.getPublicationYear());
        assertEquals("978-0007487295", book1.getISBN());
        assertEquals(12, book1.getAvailableCopies());
    }

    /**
     * @brief Test del metodo compareTo.
     */
    @Test
    void testCompareTo() {
        // 1. Titoli diversi (book3 "1984" vs book1 "The Lord...") -> book3 precede book1
        assertTrue(book3.compareTo(book1) < 0, "Dovrebbe essere < 0 se i titoli sono diversi e in ordine alfabetico");

        // 2. Titoli uguali (case-insensitive)
        Book bookA = new Book("Test Title", "Author A", DATE_2000, "ISBN-A", 1);
        Book bookB = new Book("test title", "Author B", DATE_2000, "ISBN-B", 1); // Titolo con case diverso
        
        // Confronto per autori: Author A vs Author B -> A precede B
        assertTrue(bookA.compareTo(bookB) < 0, "Dovrebbe essere < 0 se i titoli sono uguali ma Author A precede Author B");
        
        // 3. Oggetti uguali (stesso titolo e autore)
        Book bookC = new Book("Test Title", "Author A", DATE_2000, "ISBN-C", 1);
        assertTrue(bookA.compareTo(bookC) == 0, "Dovrebbe essere 0 se titolo e autore sono uguali");
    }

    /**
     * @brief Test del metodo equals.
     */
    @Test
    void testEquals() {
        // 1. Riflessività: Un oggetto è uguale a se stesso.
        assertTrue(book1.equals(book1));

        // 2. Uguaglianza per ISBN: book1 e book2 hanno lo stesso ISBN.
        assertTrue(book1.equals(book2), "book1 e book2 dovrebbero essere uguali perché hanno lo stesso ISBN");

        // 3. Non Uguaglianza per ISBN: book1 e book3 hanno ISBN diversi.
        assertFalse(book1.equals(book3), "book1 e book3 non dovrebbero essere uguali perché hanno ISBN diversi");

        // 4. Confronto con null
        assertFalse(book1.equals(null), "Il confronto con null deve restituire false");

        // 5. Confronto con un oggetto di classe diversa
        assertFalse(book1.equals("Stringa di prova"), "Il confronto con una classe diversa deve restituire false");

        // 6. Test con ISBN null (dovrebbe essere gestito, anche se idealmente non dovrebbe accadere)
        Book bookNullIsbn = new Book("Title", "Author", DATE_2000, null, 1);
        Book bookNullIsbn2 = new Book("Title", "Author", DATE_2000, null, 1);
        assertFalse(book1.equals(bookNullIsbn), "Book con ISBN diverso da null non è uguale a Book con ISBN null");
        assertFalse(bookNullIsbn.equals(bookNullIsbn2), "Due Book con ISBN null non dovrebbero essere considerati uguali");
    }

    /**
     * @brief Test del metodo hashCode.
     */
    @Test
    void testHashCode() {
        // 1. Coerenza con equals: Oggetti uguali devono avere lo stesso hash code.
        assertEquals(book1.hashCode(), book2.hashCode(), "L'hash code dovrebbe essere lo stesso se l'ISBN è lo stesso");

        // 2. Hash code diverso per oggetti non uguali.
        assertNotEquals(book1.hashCode(), book3.hashCode(), "L'hash code dovrebbe essere diverso se l'ISBN è diverso");

        // 3. Test con ISBN null
        Book bookNullIsbn = new Book("Title", "Author", DATE_2000, null, 1);
        assertEquals(0, bookNullIsbn.hashCode(), "L'hash code dovrebbe essere 0 se l'ISBN è null");
    }

    /**
     * @brief Test del metodo toString.
     */
    @Test
    void testToString() {
        String expectedToString = "Book{title='The Lord of the Rings', authors='J.R.R. Tolkien', publicationYear='1997-10-26', ISBN='978-0618260214', availableCopies=5}";
        assertEquals(expectedToString, book1.toString());
    }

    /**
     * @brief Test dell'invariante (availableCopies >= 0).
     */
    @Test
    void testInvariant() {
        // L'invariante è verificato indirettamente tramite i setter
        book1.setAvailableCopies(0);
        assertEquals(0, book1.getAvailableCopies());

        // Test di un valore positivo
        book1.setAvailableCopies(100);
        assertEquals(100, book1.getAvailableCopies());

        /*
         * NOTA: La classe Book non impedisce l'impostazione di un valore negativo
         * tramite i setter. In un sistema reale, setAvailableCopies() dovrebbe
         * lanciare una eccezione (es. IllegalArgumentException) se il valore
         * è negativo.
         */
    }
}
