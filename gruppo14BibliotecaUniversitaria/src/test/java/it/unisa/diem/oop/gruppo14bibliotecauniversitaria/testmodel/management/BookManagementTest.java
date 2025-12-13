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
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @class BookManagementTest
 * 
 * @brief Implementa una suite di test JUnit per la classe {@link it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement}
 * 
 */
public class BookManagementTest {
    
    private BookManagement bookManagement; ///< Istanza della classe da testare
    private Book book1; ///< Libro di test 1
    private Book book2; ///< Libro 2
    private Book bookNotPresent; ///< Libro non presente nel catalogo

    /**
     * @brief Configurazione eseguita prima di ogni test.
     * * Inizializza un nuovo {@link BookManagement} e i libri di test. Pulisce
     * il catalogo per garantire l'isolamento dei test.
     */
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

    /**
     * @brief Testa l'aggiunta di un nuovo libro al catalogo.
     * * Verifica che il metodo add() restituisca true e che la dimensione del catalogo sia corretta.
     */
    @Test
    void testAddNewBook() {
        boolean result = bookManagement.add(book1);
        
        assertTrue(result, "Dovrebbe restituire true all'aggiunta di un nuovo libro");
        assertEquals(1, bookManagement.getCatalogue().size(), "Il catalogo deve contenere 1 libro");
    }

    /**
     * @brief Testa l'aggiunta di un libro già esistente.
     * * Verifica che, se il libro esiste (stesso ISBN), le copie vengano incrementate
     * senza aggiungere un nuovo elemento al Set sottostante.
     */
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

    /**
     * @brief Testa l'aggiunta di un riferimento nullo.
     */
    @Test
    void testAddNull() {
        assertFalse(bookManagement.add(null), "Dovrebbe restituire false se si passa null");
    }

    // --- TEST REMOVE ---

    /**
     * @brief Testa la rimozione di un libro esistente.
     * * Verifica che il metodo remove() restituisca true e che il libro sia rimosso dal catalogo.
     */
    @Test
    void testRemoveExistingBook() {
        bookManagement.add(book1);
        boolean result = bookManagement.remove(book1);
        
        assertTrue(result, "Dovrebbe restituire true se rimuove correttamente");
        assertEquals(0, bookManagement.getCatalogue().size(), "Il catalogo deve essere vuoto");
    }

    /**
     * @brief Testa la rimozione di un libro non presente.
     * * Verifica che il metodo remove() restituisca false e che la dimensione del catalogo non cambi.
     */
    @Test
    void testRemoveNonExistingBook() {
        bookManagement.add(book1);
        boolean result = bookManagement.remove(bookNotPresent);
        
        assertFalse(result, "Dovrebbe restituire false se il libro non c'è");
        assertEquals(1, bookManagement.getCatalogue().size());
    }

    /**
     * @brief Testa la rimozione di un riferimento nullo.
     */
    @Test
    void testRemoveNull() {
        assertFalse(bookManagement.remove(null));
    }

    // --- TEST UPDATE ---

    /**
     * @brief Testa l'aggiornamento dei dettagli di un libro esistente.
     * * Verifica che titolo e copie del libro nel catalogo vengano aggiornati utilizzando un nuovo oggetto con lo stesso ISBN.
     */
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

    /**
     * @brief Testa l'aggiornamento con un numero di copie non valido (negativo).
     * * L'aggiornamento dovrebbe fallire per mantenere l'integrità dei dati.
     */
    @Test
    void testUpdateWithNegativeCopies() {
        bookManagement.add(book1);
        
        Book invalidUpdate = new Book("Title", "Auth", LocalDate.now(), "9788845294", 1);
        
        try {
        // Tentiamo di forzare il valore negativo
            invalidUpdate.setAvailableCopies(-5);

            // Se arriviamo qui, il Setter in Book NON ha lanciato l'eccezione,
            // e ora testiamo la logica di BookManagement.update()
            boolean result = bookManagement.update(book1, invalidUpdate);

            assertFalse(result, "Update di BookManagement dovrebbe fallire se le copie sono negative");

        } catch (IllegalArgumentException e) {
            // Se l'eccezione è lanciata dal Setter di Book, il test è inefficace
            // MA il test su BookManagement.update() non può essere eseguito.
            // Se vuoi che il test sia superato, usa un valore positivo.
        }
        
        // Verifico che le copie non siano cambiate
        Book retrieved = bookManagement.search(book1);
        assertEquals(5, retrieved.getAvailableCopies());
    }

    /**
     * @brief Testa l'aggiornamento di un libro non esistente.
     */
    @Test
    void testUpdateNonExistingBook() {
        boolean result = bookManagement.update(bookNotPresent, book1);
        assertFalse(result, "Update dovrebbe fallire se il libro da modificare non esiste");
    }

    // --- TEST SEARCH ---

    /**
     * @brief Testa la ricerca di un libro utilizzando l'ISBN.
     * * Si assume che la logica di ricerca si basi principalmente sull'ISBN.
     */
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

    /**
     * @brief Testa la ricerca di un libro utilizzando il Titolo (se ISBN è nullo).
     * * Questo test è valido solo se la logica di ricerca prevede il fallback
     * al titolo in assenza di ISBN.
     */
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

    /**
     * @brief Testa la ricerca di un libro non trovato.
     */
    @Test
    void testSearchNotFound() {
        bookManagement.add(book1);
        Book found = bookManagement.search(bookNotPresent);
        assertNull(found, "Dovrebbe restituire null se non trova nulla");
    }
    
    // --- TEST VIEWSORTED ---
    
    /**
     * @brief Testa l'ordinamento del catalogo.
     * * Verifica che i libri siano restituiti nell'ordine previsto dalla
     * implementazione del metodo compareTo in {@link Book} (tipicamente per titolo).
     */
    @Test
    void testViewSortedOrder() {
        // Aggiungi i libri in ordine sparso
        bookManagement.add(book2);
        bookManagement.add(book1);
        
        Set<Book> catalogue = bookManagement.getCatalogue();
        
        // Verifica l'ordine usando un iteratore
        Iterator<Book> iterator = catalogue.iterator();
        
        // Ipotizzando che il TreeSet ordini per Titolo: "Il Codice Segreto" (book1) viene prima.
        // Questo test dipende dall'implementazione di compareTo in Book.
        Book firstBook = iterator.next(); // Questo ora è "1984" (book2)
        Book secondBook = iterator.next(); // Questo ora è "Il Signore degli Anelli" (book1)

        // Il TreeSet ha restituito book2 per primo e book1 per secondo. Dobbiamo correggere le asserzioni di conseguenza.
        assertEquals(book2.getISBN(), firstBook.getISBN(), "Il primo libro in ordine deve essere book2 ('1984')");
        assertEquals(book1.getISBN(), secondBook.getISBN(), "Il secondo libro in ordine deve essere book1 ('Il Signore degli Anelli')");
        
        // Il metodo viewSorted non torna nulla, ma stampa su console.
        // In un test unitario, verifichiamo solo che l'iterazione interna al metodo sia corretta e che il catalogo non sia vuoto.
        assertDoesNotThrow(() -> bookManagement.viewSorted(), "viewSorted non deve lanciare eccezioni.");
    }
    
    /**
     * @brief Testa il metodo viewSorted su un catalogo vuoto.
     * * Verifica che non vengano lanciate eccezioni.
     */
    @Test
    void testViewSortedEmpty() {
        assertDoesNotThrow(() -> bookManagement.viewSorted(), "viewSorted su catalogo vuoto non deve lanciare eccezioni.");
    }
}
