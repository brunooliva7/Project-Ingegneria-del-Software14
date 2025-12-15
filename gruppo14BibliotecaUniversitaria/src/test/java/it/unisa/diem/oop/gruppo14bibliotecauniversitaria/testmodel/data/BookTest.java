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
 * @brief Classe di test per la classe Book utilizzando JUnit 5.
 */
class BookTest {

    // --- COSTRUTTORE COMPLETO ---
    @Test
    void testConstructorValidData() {
        Book b = new Book("Titolo", "Autore", "2020", "1234567890123", 5);
        assertEquals("Titolo", b.getTitle());
        assertEquals("Autore", b.getAuthors());
        assertEquals("2020", b.getPublicationYear());
        assertEquals("1234567890123", b.getISBN());
        assertEquals(5, b.getAvailableCopies());
    }

    @Test
    void testConstructorNegativeCopiesThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("Titolo", "Autore", "2020", "1234567890123", -1));
    }

    @Test
    void testConstructorInvalidYearThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("Titolo", "Autore", "20AB", "1234567890123", 1));
    }

    @Test
    void testConstructorYearNullOrEmptyAccepted() {
        Book b1 = new Book("Titolo", "Autore", null, "1234567890123", 1);
        assertNull(b1.getPublicationYear());

        Book b2 = new Book("Titolo", "Autore", "", "1234567890123", 1);
        assertEquals("", b2.getPublicationYear());
    }

    // --- COSTRUTTORE DI RICERCA ---
    @Test
    void testSearchConstructorWithISBN() {
        Book b = new Book("123456789X");
        assertEquals("123456789X", b.getISBN());
        assertNull(b.getTitle());
    }

    @Test
    void testSearchConstructorWithTitleContainingSpaces() {
        Book b = new Book("Il Signore degli Anelli");
        assertEquals("Il Signore degli Anelli", b.getTitle());
        assertNull(b.getISBN());
    }

    @Test
    void testSearchConstructorWithSingleWord() {
        Book b = new Book("Dante");
        assertEquals("Dante", b.getTitle());
        assertNull(b.getISBN());
    }

    @Test
    void testSearchConstructorWithNullOrEmptyInput() {
        Book b1 = new Book((String) null);
        assertNull(b1.getTitle());
        assertNull(b1.getISBN());

        Book b2 = new Book("");
        assertNull(b2.getTitle());
        assertNull(b2.getISBN());
    }

    // --- SETTER ---
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

    @Test
    void testSetAvailableCopiesNegativeThrowsException() {
        Book b = new Book("Titolo", "Autore", "2020", "1234567890123", 1);
        assertThrows(IllegalArgumentException.class, () -> b.setAvailableCopies(-5));
    }

    // --- COMPARETO ---
    @Test
    void testCompareToDifferentTitles() {
        Book b1 = new Book("A", "Autore1", "2020", "111", 1);
        Book b2 = new Book("B", "Autore2", "2020", "222", 1);
        assertTrue(b1.compareTo(b2) < 0);
    }

    @Test
    void testCompareToSameTitleDifferentAuthors() {
        Book b1 = new Book("Titolo", "AutoreA", "2020", "111", 1);
        Book b2 = new Book("Titolo", "AutoreB", "2020", "222", 1);
        assertTrue(b1.compareTo(b2) < 0);
    }

    @Test
    void testCompareToSameTitleAndAuthors() {
        Book b1 = new Book("Titolo", "Autore", "2020", "111", 1);
        Book b2 = new Book("Titolo", "Autore", "2020", "222", 1);
        assertEquals(0, b1.compareTo(b2));
    }

    // --- EQUALS & HASHCODE ---
    @Test
    void testEqualsSameISBN() {
        Book b1 = new Book("Titolo", "Autore", "2020", "123", 1);
        Book b2 = new Book("Altro", "Autore2", "2021", "123", 2);
        assertTrue(b1.equals(b2));
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    void testEqualsDifferentISBN() {
        Book b1 = new Book("Titolo", "Autore", "2020", "123", 1);
        Book b2 = new Book("Titolo", "Autore", "2020", "456", 1);
        assertFalse(b1.equals(b2));
    }

    @Test
    void testEqualsNullOrDifferentClass() {
        Book b = new Book("Titolo", "Autore", "2020", "123", 1);
        assertFalse(b.equals(null));
        assertFalse(b.equals("NonBook"));
    }

    @Test
    void testHashCodeWithNullISBN() {
        Book b = new Book("Titolo", "Autore", "2020", null, 1);
        assertEquals(0, b.hashCode());
    }

    // --- TO STRING ---
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

