/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

/**
 * @file BookManagementTest.java
 *
 * @author bruno
 * @date 12-12-2025
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class BookManagementTest
 * @brief Classe di test per BookManagement
 *
 * Verifica il corretto funzionamento delle operazioni di gestione del catalogo:
 * aggiunta, rimozione, aggiornamento, ricerca e visualizzazione ordinata.
 *
 * @see BookManagement
 */
class BookManagementTest {

    private BookManagement bm;
    private Book b1;
    private Book b2;

    /**
     * @brief Setup eseguito prima di ogni test
     * @post Inizializza un BookManagement vuoto e due libri di esempio
     */
    @BeforeEach
    void setUp() {
        bm = new BookManagement();
        b1 = new Book("Titolo1", "Autore1", "2020", "ISBN1", 1);
        b2 = new Book("Titolo2", "Autore2", "2021", "ISBN2", 2);
    }

    /**
     * @test Verifica che il catalogo sia inizializzato vuoto
     * @post getCatalogue restituisce un Set vuoto
     */
    @Test
    void testConstructorInitializesEmptyCatalogue() {
        assertNotNull(bm.getCatalogue());
        assertTrue(bm.getCatalogue().isEmpty());
    }

    /**
     * @test Verifica aggiunta di un nuovo libro
     * @pre b != null
     * @post Libro aggiunto al catalogo
     */
    @Test
    void testAddNewBook() {
        assertTrue(bm.add(b1));
        assertTrue(bm.getCatalogue().contains(b1));
    }

    /**
     * @test Verifica aggiunta di un libro con ISBN già presente
     * @pre ISBN duplicato
     * @post Incremento copie disponibili
     */
    @Test
    void testAddDuplicateISBNIncrementsCopies() {
        bm.add(b1);
        int initialCopies = b1.getAvailableCopies();

        Book duplicate = new Book("AltroTitolo", "AltroAutore", "2022", "ISBN1", 1);
        assertTrue(bm.add(duplicate));

        // Copie incrementate
        Set<Book> catalogue = bm.getCatalogue();
        Book stored = catalogue.stream().filter(b -> b.getISBN().equals("ISBN1")).findFirst().orElse(null);
        assertNotNull(stored);
        assertEquals(initialCopies + 1, stored.getAvailableCopies());
    }

    /**
     * @test Verifica che add lanci eccezione se b == null
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bm.add(null));
    }

    /**
     * @test Verifica rimozione di un libro presente
     * @pre Libro presente nel catalogo
     * @post Libro rimosso
     */
    @Test
    void testRemoveExistingBook() {
        bm.add(b1);
        assertTrue(bm.remove(b1));
        assertFalse(bm.getCatalogue().contains(b1));
    }

    /**
     * @test Verifica rimozione di un libro non presente
     * @post Restituisce false
     */
    @Test
    void testRemoveNonExistingBook() {
        assertFalse(bm.remove(b1));
    }

    /**
     * @test Verifica che remove lanci eccezione se b == null
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testRemoveNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bm.remove(null));
    }

    /**
     * @test Verifica aggiornamento di un libro presente
     * @pre Libro con ISBN presente
     * @post Dati aggiornati
     */
    @Test
    void testUpdateExistingBook() {
        bm.add(b1);
        Book updated = new Book("NuovoTitolo", "NuovoAutore", "2025", "ISBN1", 5);

        assertTrue(bm.update(b1, updated));

        Book stored = bm.getCatalogue().stream().filter(b -> b.getISBN().equals("ISBN1")).findFirst().orElse(null);
        assertNotNull(stored);
        assertEquals("NuovoTitolo", stored.getTitle());
        assertEquals("NuovoAutore", stored.getAuthors());
        assertEquals("2025", stored.getPublicationYear());
        assertEquals(5, stored.getAvailableCopies());
    }

    /**
     * @test Verifica update con copie negative
     * @post Restituisce false
     */
    @Test
    void testUpdateWithNegativeCopies() {
        bm.add(b1);
        Book invalidUpdate = new Book("TitoloX", "AutoreX", "2025", "ISBN1", -3);
        assertFalse(bm.update(b1, invalidUpdate));
    }

    /**
     * @test Verifica update con libro non presente
     * @post Restituisce false
     */
    @Test
    void testUpdateNonExistingBook() {
        bm.add(b1);
        assertFalse(bm.update(b2, b1));
    }

    /**
     * @test Verifica ricerca per ISBN
     * @post Restituisce lista con il libro corrispondente
     */
    @Test
    void testSearchByISBN() {
        bm.add(b1);
        Book query = new Book("TitoloX", "AutoreX", "2025", "ISBN1", 1);
        List<Book> result = bm.search(query);
        assertEquals(1, result.size());
        assertEquals(b1, result.get(0));
    }

    /**
     * @test Verifica ricerca per titolo parziale
     * @post Restituisce lista con i libri corrispondenti
     */
    @Test
    void testSearchByTitle() {
        bm.add(b1);
        bm.add(b2);
        Book query = new Book("Titolo", null, null, null, 0);
        List<Book> result = bm.search(query);
        assertTrue(result.contains(b1));
        assertTrue(result.contains(b2));
    }

    /**
     * @test Verifica ricerca per autore parziale
     * @post Restituisce lista con i libri corrispondenti
     */
    @Test
    void testSearchByAuthors() {
        bm.add(b1);
        Book query = new Book(null, "Autore1", null, null, 0);
        List<Book> result = bm.search(query);
        assertEquals(1, result.size());
        assertEquals(b1, result.get(0));
    }

    /**
     * @test Verifica che search lanci eccezione se b == null
     * @post IllegalArgumentException sollevata
     */
    @Test
    void testSearchNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bm.search(null));
    }
}