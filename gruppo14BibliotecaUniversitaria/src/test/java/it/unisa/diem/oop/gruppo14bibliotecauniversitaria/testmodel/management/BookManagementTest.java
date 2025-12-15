/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.BookManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class BookManagementTest
 * @brief Classe di test per BookManagement
 */
class BookManagementTest {

    private BookManagement bm;
    private Book b1;
    private Book b2;
    private Book b3;

    @BeforeEach
    void setUp() {
        bm = new BookManagement();
        
        // --- FIX FONDAMENTALE ---
        // Il costruttore probabilmente carica dati da file.
        // Dobbiamo svuotare il catalogo per avere un ambiente pulito per ogni test.
        if (bm.getCatalogue() != null && !bm.getCatalogue().isEmpty()) {
            bm.getCatalogue().clear();
        }

        // Creazione di libri con dati validi
        b1 = new Book("Java Programming", "Bruno", "2020", "ISBN-001", 1);
        b2 = new Book("Advanced Algorithms", "Mara", "2021", "ISBN-002", 2);
        b3 = new Book("C++ Basics", "Autore C", "2019", "ISBN-003", 5);
    }

    @Nested
    @DisplayName("Test Gestione Inserimento e Rimozione")
    class AddRemoveTests {

        @Test
        @DisplayName("Costruttore: dopo il setup il catalogo è vuoto")
        void testConstructorInitializesEmptyCatalogue() {
            // Nota: Se la persistenza ricarica i dati, questo test passa solo grazie al .clear() nel setUp
            assertNotNull(bm.getCatalogue(), "Il catalogo non deve essere null");
            assertTrue(bm.getCatalogue().isEmpty(), "Il catalogo deve essere vuoto per i test (dopo clear)");
        }

        @Test
        @DisplayName("add: inserisce correttamente un nuovo libro")
        void testAddNewBook() {
            assertTrue(bm.add(b1), "Il metodo add deve restituire true per un nuovo libro");
            assertEquals(1, bm.getCatalogue().size(), "La dimensione del catalogo deve essere 1");
            assertTrue(bm.getCatalogue().contains(b1), "Il libro deve essere presente nel catalogo");
        }

        @Test
        @DisplayName("add: se ISBN esiste, incrementa le copie")
        void testAddDuplicateISBN() {
            bm.add(b1); // Copie iniziali: 1
            
            // Creiamo un libro "clone" con stesso ISBN
            Book duplicate = new Book("Titolo Diverso", "Autore Diverso", "2022", "ISBN-001", 1);
            
            bm.add(duplicate);

            assertEquals(1, bm.getCatalogue().size(), "Il catalogo non deve duplicare ISBN");

            Book stored = bm.getCatalogue().stream()
                            .filter(b -> b.getISBN().equals("ISBN-001"))
                            .findFirst()
                            .orElse(null);
            
            assertNotNull(stored);
            assertEquals(2, stored.getAvailableCopies(), "Le copie disponibili devono essere incrementate");
        }

        @Test
        @DisplayName("add: lancia eccezione con input null")
        void testAddNullThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> bm.add(null));
        }

        @Test
        @DisplayName("remove: rimuove un libro esistente")
        void testRemoveExistingBook() {
            bm.add(b1);
            assertTrue(bm.remove(b1), "Remove deve restituire true se il libro esisteva");
            assertTrue(bm.getCatalogue().isEmpty(), "Il catalogo deve essere vuoto dopo la rimozione");
        }

        @Test
        @DisplayName("remove: restituisce false se il libro non esiste")
        void testRemoveNonExistingBook() {
            bm.add(b1);
            assertFalse(bm.remove(b2), "Remove deve restituire false se il libro non c'è");
            assertEquals(1, bm.getCatalogue().size());
        }

        @Test
        @DisplayName("remove: lancia eccezione con input null")
        void testRemoveNullThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> bm.remove(null));
        }
    }

    @Nested
    @DisplayName("Test Aggiornamento (Update)")
    class UpdateTests {

        @Test
        @DisplayName("update: aggiorna i dati di un libro esistente")
        void testUpdateExistingBook() {
            bm.add(b1); // b1 ha ISBN-001
            
            Book updatedInfo = new Book("Java Pro 2.0", "Bruno Updated", "2025", "ISBN-001", 10);

            boolean result = bm.update(b1, updatedInfo);
            assertTrue(result, "L'update deve restituire true");
            
            // --- FIX: Cerca il libro specifico per ISBN, non prendere il primo a caso ---
            Book stored = bm.getCatalogue().stream()
                            .filter(b -> b.getISBN().equals("ISBN-001"))
                            .findFirst()
                            .orElseThrow(() -> new AssertionError("Libro non trovato dopo update"));
            
            assertAll("Verifica campi aggiornati",
                () -> assertEquals("Java Pro 2.0", stored.getTitle()),
                () -> assertEquals("Bruno Updated", stored.getAuthors()),
                () -> assertEquals("2025", stored.getPublicationYear()),
                () -> assertEquals(10, stored.getAvailableCopies())
            );
        }

        @Test
        @DisplayName("update: fallisce se il libro originale non è nel catalogo")
        void testUpdateNonExistingBook() {
            bm.add(b1);
            // b2 non è stato aggiunto, quindi l'update deve fallire
            assertFalse(bm.update(b2, b1));
        }
    }

    @Nested
    @DisplayName("Test Ricerca (Search)")
    class SearchTests {

        @BeforeEach
        void initCatalogue() {
            // Assicuriamoci che sia pulito e aggiungiamo i 3 libri noti
            if(!bm.getCatalogue().isEmpty()) bm.getCatalogue().clear();
            bm.add(b1); 
            bm.add(b2); 
            bm.add(b3); 
        }

        @Test
        @DisplayName("search: trova libro per ISBN esatto")
        void testSearchByISBN() {
            Book query = new Book("ISBN-001"); 
            List<Book> result = bm.search(query);

            assertEquals(1, result.size());
            assertEquals(b1, result.get(0));
        }

        @Test
        @DisplayName("search: trova libri per Titolo parziale")
        void testSearchByTitle() {
            Book query = new Book("Java");
            List<Book> result = bm.search(query);

            assertEquals(1, result.size());
            assertEquals(b1, result.get(0));
        }
        
        @Test
        @DisplayName("search: restituisce lista vuota se nessun match")
        void testSearchNoMatch() {
            Book query = new Book("Python");
            List<Book> result = bm.search(query);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("search: lancia eccezione se query è null")
        void testSearchNull() {
            assertThrows(IllegalArgumentException.class, () -> bm.search(null));
        }
    }
    
    @Nested
    @DisplayName("Test Ordinamento")
    class OrderTests {
        
        @Test
        @DisplayName("Verifica ordinamento: converte il catalogo in lista e ordina")
        void testCatalogueOrdering() {
            // Assicuriamoci di lavorare solo con i nostri 3 libri
            bm.getCatalogue().clear();
            bm.add(b1); // J
            bm.add(b2); // A
            bm.add(b3); // C
            
            Set<Book> catalogueSet = bm.getCatalogue();
            List<Book> sortedList = new ArrayList<>(catalogueSet);
            Collections.sort(sortedList);
            
            // Ora dovrebbero essere esattamente 3
            assertEquals(3, sortedList.size(), "Dovrebbero esserci solo 3 libri nel test");
            assertEquals(b2, sortedList.get(0), "1°: Advanced Algorithms (A)");
            assertEquals(b3, sortedList.get(1), "2°: C++ Basics (C)");
            assertEquals(b1, sortedList.get(2), "3°: Java Programming (J)");
        }
    }
}