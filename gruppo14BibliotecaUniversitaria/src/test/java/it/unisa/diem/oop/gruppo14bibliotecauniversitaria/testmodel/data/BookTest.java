package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;
    // Dati di default per i test
    private final String TITLE = "Il Signore degli Anelli";
    private final String AUTHORS = "J.R.R. Tolkien";
    private final String YEAR = "1954";
    private final String ISBN = "978-88-123456";
    private final int COPIES = 10;

    @BeforeEach
    void setUp() {
        // Inizializza un libro valido prima di ogni test
        book = new Book(TITLE, AUTHORS, YEAR, ISBN, COPIES);
    }

    @Nested
    @DisplayName("Test dei Costruttori")
    class ConstructorTests {

        @Test
        @DisplayName("Costruttore Completo: crea oggetto con dati validi")
        void testFullConstructor() {
            assertAll("Verifica proprietà iniziali",
                () -> assertEquals(TITLE, book.getTitle()),
                () -> assertEquals(AUTHORS, book.getAuthors()),
                () -> assertEquals(YEAR, book.getPublicationYear()),
                () -> assertEquals(ISBN, book.getISBN()),
                () -> assertEquals(COPIES, book.getAvailableCopies())
            );
        }

        @Test
        @DisplayName("Costruttore Completo: lancia eccezione per copie negative")
        void testConstructorNegativeCopies() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Book(TITLE, AUTHORS, YEAR, ISBN, -1);
            });
            assertTrue(exception.getMessage().contains("non può essere negativo"));
        }

        @ParameterizedTest
        @DisplayName("Costruttore Completo: lancia eccezione per anno non valido")
        @ValueSource(strings = {"123", "12345", "abcd", "202a"})
        void testConstructorInvalidYear(String invalidYear) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Book(TITLE, AUTHORS, invalidYear, ISBN, COPIES);
            });
            assertTrue(exception.getMessage().contains("esattamente 4 cifre"));
        }
        
        @Test
        @DisplayName("Costruttore Completo: accetta anno null o vuoto senza eccezioni")
        void testConstructorNullYear() {
            // Dal codice: il controllo regex avviene solo se year != null e !isEmpty
            Book bNull = new Book(TITLE, AUTHORS, null, ISBN, COPIES);
            assertNull(bNull.getPublicationYear());
            
            Book bEmpty = new Book(TITLE, AUTHORS, "", ISBN, COPIES);
            assertEquals("", bEmpty.getPublicationYear());
        }

        // --- TEST DEL COSTRUTTORE DI RICERCA (CORRETTO) ---
        
        @Test
        @DisplayName("Costruttore Ricerca: popola Titolo, Autori e ISBN con lo stesso input")
        void testSearchConstructorStandard() {
            String query = "Harry Potter";
            Book searchBook = new Book(query);

            assertAll("Verifica propagazione query su tutti i campi",
                () -> assertEquals(query, searchBook.getTitle(), "Il titolo deve contenere la query"),
                () -> assertEquals(query, searchBook.getAuthors(), "Gli autori devono contenere la query"),
                () -> assertEquals(query, searchBook.getISBN(), "L'ISBN deve contenere la query"),
                // I campi non pertinenti alla ricerca devono essere null/0
                () -> assertNull(searchBook.getPublicationYear()),
                () -> assertEquals(0, searchBook.getAvailableCopies())
            );
        }

        @Test
        @DisplayName("Costruttore Ricerca: con ISBN popola comunque tutti i campi")
        void testSearchConstructorWithISBN() {
            String isbnInput = "123456789X";
            Book searchBook = new Book(isbnInput);

            // La classe è progettata per mettere l'input in tutti i campi ricercabili
            assertEquals(isbnInput, searchBook.getISBN());
            assertEquals(isbnInput, searchBook.getTitle());
            assertEquals(isbnInput, searchBook.getAuthors());
        }

        @Test
        @DisplayName("Costruttore Ricerca: gestisce input null o vuoti")
        void testSearchConstructorNullEmpty() {
            Book bNull = new Book(null);
            assertNull(bNull.getTitle());
            assertNull(bNull.getISBN());

            Book bEmpty = new Book("   ");
            assertNull(bEmpty.getTitle());
            assertNull(bEmpty.getISBN());
        }
    }

    @Nested
    @DisplayName("Test Setters e Validazione")
    class SetterTests {

        @Test
        @DisplayName("setAvailableCopies: aggiorna valore valido")
        void testSetAvailableCopies() {
            book.setAvailableCopies(50);
            assertEquals(50, book.getAvailableCopies());
        }

        @Test
        @DisplayName("setAvailableCopies: lancia eccezione se negativo")
        void testSetAvailableCopiesNegative() {
            assertThrows(IllegalArgumentException.class, () -> book.setAvailableCopies(-5));
        }

        @Test
        @DisplayName("Altri setter: aggiornano correttamente i campi")
        void testOtherSetters() {
            book.setTitle("Nuovo Titolo");
            book.setAuthors("Nuovo Autore");
            book.setPublicationYear("2000");
            book.setISBN("000000000");

            assertAll(
                () -> assertEquals("Nuovo Titolo", book.getTitle()),
                () -> assertEquals("Nuovo Autore", book.getAuthors()),
                () -> assertEquals("2000", book.getPublicationYear()),
                () -> assertEquals("000000000", book.getISBN())
            );
        }
    }

    @Nested
    @DisplayName("Test compareTo (Ordinamento)")
    class CompareToTests {

        @Test
        @DisplayName("compareTo: ordina per Titolo (alfabetico)")
        void testCompareToTitle() {
            Book b1 = new Book("A_Libro", AUTHORS, YEAR, ISBN, COPIES);
            Book b2 = new Book("Z_Libro", AUTHORS, YEAR, ISBN, COPIES);

            assertTrue(b1.compareTo(b2) < 0, "A deve precedere Z");
            assertTrue(b2.compareTo(b1) > 0, "Z deve seguire A");
        }

        @Test
        @DisplayName("compareTo: ordina per Titolo (case-insensitive)")
        void testCompareToTitleCaseInsensitive() {
            Book b1 = new Book("albero", AUTHORS, YEAR, ISBN, COPIES);
            Book b2 = new Book("Zattera", AUTHORS, YEAR, ISBN, COPIES);
            
            assertTrue(b1.compareTo(b2) < 0);
        }

        @Test
        @DisplayName("compareTo: a parità di Titolo, ordina per Autore")
        void testCompareToAuthors() {
            Book b1 = new Book(TITLE, "A_Autore", YEAR, ISBN, COPIES);
            Book b2 = new Book(TITLE, "B_Autore", YEAR, ISBN, COPIES);

            assertTrue(b1.compareTo(b2) < 0);
        }

        @Test
        @DisplayName("compareTo: identici restituiscono 0")
        void testCompareToEqual() {
            Book b1 = new Book(TITLE, AUTHORS, YEAR, ISBN, COPIES);
            Book b2 = new Book(TITLE, AUTHORS, YEAR, "ISBN_DIVERSO", COPIES); 
            
            // compareTo ignora ISBN, guarda solo Titolo e Autore
            assertEquals(0, b1.compareTo(b2));
        }
    }

    @Nested
    @DisplayName("Test equals e hashCode")
    class IdentityTests {

        @Test
        @DisplayName("equals: basato solo su ISBN")
        void testEquals() {
            Book b1 = new Book(TITLE, AUTHORS, YEAR, "ISBN_111", COPIES);
            // b2 ha dati diversi ma stesso ISBN
            Book b2 = new Book("Altro Titolo", "Altro Autore", "2000", "ISBN_111", 0);
            // b3 ha ISBN diverso
            Book b3 = new Book(TITLE, AUTHORS, YEAR, "ISBN_222", COPIES);

            assertEquals(b1, b2);
            assertNotEquals(b1, b3);
        }

        @Test
        @DisplayName("equals: gestisce null e classi diverse")
        void testEqualsEdgeCases() {
            assertNotEquals(book, null);
            assertNotEquals(book, "Una stringa");
        }
        
        @Test
        @DisplayName("equals: gestisce ISBN null in modo sicuro")
        void testEqualsWithNullISBN() {
            // Se ISBN è null, il metodo equals restituisce false
            Book bNullISBN1 = new Book(TITLE, AUTHORS, YEAR, null, COPIES);
            Book bValidISBN = new Book(TITLE, AUTHORS, YEAR, ISBN, COPIES);
            
            assertNotEquals(bValidISBN, bNullISBN1);
            
            // Due libri con ISBN null non sono considerati uguali dall'implementazione corrente
            Book bNullISBN2 = new Book(TITLE, AUTHORS, YEAR, null, COPIES);
            assertNotEquals(bNullISBN1, bNullISBN2); 
        }

        @Test
        @DisplayName("hashCode: consistente con equals")
        void testHashCode() {
            Book b1 = new Book(TITLE, AUTHORS, YEAR, "ISBN_UNIQUE", COPIES);
            Book b2 = new Book("Titolo Diverso", "Autore Diverso", "1999", "ISBN_UNIQUE", 5);

            assertEquals(b1.hashCode(), b2.hashCode());
        }
    }

    @Test
    @DisplayName("toString: contiene i dati principali")
    void testToString() {
        String result = book.toString();
        assertAll(
            () -> assertTrue(result.contains(TITLE)),
            () -> assertTrue(result.contains(AUTHORS)),
            () -> assertTrue(result.contains(ISBN)),
            () -> assertTrue(result.contains(String.valueOf(COPIES)))
        );
    }
}