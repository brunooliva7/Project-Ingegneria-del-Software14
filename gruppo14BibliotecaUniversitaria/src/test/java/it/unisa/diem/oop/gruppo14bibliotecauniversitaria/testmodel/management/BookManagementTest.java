/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data.Book;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bruno
 */
public class BookManagementTest {
    
    private BookManagement bookManagement;
    private Book book1;
    private Book book2;
    private Book bookNotPresent;

    @BeforeEach
    void setUp() {
        bookManagement = new BookManagement();
        
        // Pulisco il catalogo prima di ogni test per evitare interferenze col file
        bookManagement.getCatalogue().clear();

        // Ipotizzo il costruttore: Titolo, Autore, Anno, ISBN, Copie
        book1 = new Book("Il Signore degli Anelli", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), "9788845294", 5);
        book2 = new Book("1984", "George Orwell", LocalDate.of(1949, 6, 8), "9780451524", 3);
        
        // Libro non presente nel catalogo
        bookNotPresent = new Book("Divina Commedia", "Dante Alighieri", LocalDate.of(1320, 1, 1), "1112223334", 1);
    }

    // --- TEST ADD ---

    @Test
    void testAddNewBook() {
        boolean result = bookManagement.add(book1);
        
        assertTrue(result, "Dovrebbe restituire true all'aggiunta di un nuovo libro");
        assertEquals(1, bookManagement.getCatalogue().size(), "Il catalogo deve contenere 1 libro");
    }

    @Test
    void testAddExistingBookIncrementsCopies() {
        // Aggiungo il libro la prima volta (copie iniziali: 5)
        bookManagement.add(book1);
        
        // Creo un oggetto con lo STESSO ISBN ma istanza diversa
        Book bookDuplicate = new Book("Il Signore degli Anelli", "J.R.R. Tolkien", LocalDate.of(1954, 7, 29), "9788845294", 1);
        
        // Provo ad aggiungere di nuovo (il tuo codice dovrebbe incrementare le copie)
        boolean result = bookManagement.add(bookDuplicate);
        
        assertTrue(result, "Dovrebbe restituire true (aggiornamento copie avvenuto)");
        assertEquals(1, bookManagement.getCatalogue().size(), "Non deve aggiungere un duplicato al Set");
        
        // Recupero il libro e controllo le copie
        Book storedBook = bookManagement.search(book1);
        assertEquals(6, storedBook.getAvailableCopies(), "Le copie dovrebbero essere incrementate (5 + 1)");
    }

    @Test
    void testAddNull() {
        assertFalse(bookManagement.add(null), "Dovrebbe restituire false se si passa null");
    }

    // --- TEST REMOVE ---

    @Test
    void testRemoveExistingBook() {
        bookManagement.add(book1);
        boolean result = bookManagement.remove(book1);
        
        assertTrue(result, "Dovrebbe restituire true se rimuove correttamente");
        assertEquals(0, bookManagement.getCatalogue().size(), "Il catalogo deve essere vuoto");
    }

    @Test
    void testRemoveNonExistingBook() {
        bookManagement.add(book1);
        boolean result = bookManagement.remove(bookNotPresent);
        
        assertFalse(result, "Dovrebbe restituire false se il libro non c'è");
        assertEquals(1, bookManagement.getCatalogue().size());
    }

    @Test
    void testRemoveNull() {
        assertFalse(bookManagement.remove(null));
    }

    // --- TEST UPDATE ---

    @Test
    void testUpdateBookDetails() {
        bookManagement.add(book1);
        
        // Creo un libro con lo stesso ISBN di book1, ma dati diversi
        // Titolo cambiato, copie cambiate a 10
        Book updatedInfo = new Book("Lord of the Rings", "Tolkien", LocalDate.of(1954, 7, 29), "9788845294", 10);
        
        boolean result = bookManagement.update(book1, updatedInfo);
        
        assertTrue(result, "Update dovrebbe restituire true");
        
        Book retrieved = bookManagement.search(book1);
        assertEquals("Lord of the Rings", retrieved.getTitle(), "Il titolo dovrebbe essere aggiornato");
        assertEquals(10, retrieved.getAvailableCopies(), "Le copie dovrebbero essere aggiornate");
    }

    @Test
    void testUpdateWithNegativeCopies() {
        bookManagement.add(book1);
        
        Book invalidUpdate = new Book("Title", "Auth", LocalDate.now(), "9788845294", -5);
        
        boolean result = bookManagement.update(book1, invalidUpdate);
        
        assertFalse(result, "Update dovrebbe fallire se le copie sono negative");
        
        // Verifico che le copie non siano cambiate
        Book retrieved = bookManagement.search(book1);
        assertEquals(5, retrieved.getAvailableCopies());
    }

    @Test
    void testUpdateNonExistingBook() {
        boolean result = bookManagement.update(bookNotPresent, book1);
        assertFalse(result, "Update dovrebbe fallire se il libro da modificare non esiste");
    }

    // --- TEST SEARCH ---

    @Test
    void testSearchByISBN() {
        bookManagement.add(book1);
        
        // Cerca usando un oggetto dummy con solo l'ISBN corretto
        // Assumo che equals() in Book guardi l'ISBN
        Book searchKey = new Book(null, null, null, "9788845294", 0);
        
        Book found = bookManagement.search(searchKey);
        
        assertNotNull(found, "Dovrebbe trovare il libro tramite ISBN");
        assertEquals("Il Signore degli Anelli", found.getTitle());
    }

    @Test
    void testSearchByTitle() {
        bookManagement.add(book2); // Titolo: "1984"
        
        // IMPORTANTE: Per testare la ricerca per titolo, l'ISBN DEVE essere null
        // altrimenti il tuo codice entra nell'if(getISBN != null) e ignora il titolo.
        Book searchKey = new Book("1984", null, null, null, 0);
        
        Book found = bookManagement.search(searchKey);
        
        // Nota: Se questo test fallisce o lancia eccezione, verifica che il costruttore Book accetti ISBN null
        assertNotNull(found, "Dovrebbe trovare il libro tramite Titolo");
        assertEquals("9780451524", found.getISBN());
    }

    @Test
    void testSearchNotFound() {
        bookManagement.add(book1);
        Book found = bookManagement.search(bookNotPresent);
        assertNull(found, "Dovrebbe restituire null se non trova nulla");
    }
}
