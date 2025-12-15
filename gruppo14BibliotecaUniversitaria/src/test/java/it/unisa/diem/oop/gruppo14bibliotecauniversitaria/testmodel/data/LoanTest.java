package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class LoanTest
 * @brief Suite di test JUnit 5 per la classe Loan.
 * * Adattata per i costruttori specifici di User e Book:
 * - User: (name, surname, numberId, email) e (inputRicerca -> Regex logic)
 * - Book: (title, authors, year, isbn, copies) e (inputRicerca -> Unified logic)
 */
class LoanTest {

    private Loan loan;
    private User user;
    private Book book;
    private LocalDate dueDate;

    @BeforeEach
    void setUp() {
        // COSTRUTTORI REALI FORNITI NEL PROMPT
        
        // public User(String name, String surname, String numberId, String email)
        user = new User("Mario", "Rossi", "001", "mario.rossi@email.it");
        
        // public Book(String title, String authors, String publicationYear, String ISBN, int availableCopies)
        book = new Book("Java Programming", "Bruno", "2020", "ISBN-123", 5);
        
        dueDate = LocalDate.now().plusDays(30);

        // Creazione del prestito standard
        loan = new Loan(book, user, dueDate);
    }

    @Nested
    @DisplayName("Test dei Costruttori")
    class ConstructorTests {

        @Test
        @DisplayName("Costruttore Principale: inizializzazione corretta con oggetti completi")
        void testMainConstructor() {
            assertAll("Verifica proprietà principali",
                () -> assertNotNull(loan),
                () -> assertEquals(book, loan.getBook()),
                () -> assertEquals(user, loan.getUser()),
                () -> assertEquals(dueDate, loan.getDueDate())
            );
        }

        @Test
        @DisplayName("Costruttore Ricerca User: Input Numerico -> Matricola")
        void testSearchConstructor_UserNumeric() {
            // Se l'input è solo numeri, User(String) lo assegna a numberId
            String inputUser = "12345";
            String inputBook = "TestBook";
            
            Loan searchLoan = new Loan(inputUser, inputBook);

            assertNotNull(searchLoan.getUser());
            // Verifica logica User: numeri finiscono in numberId
            assertEquals("12345", searchLoan.getUser().getNumberId());
            // Il surname dovrebbe essere null perché l'input era numerico
            assertNull(searchLoan.getUser().getSurname());
        }

        @Test
        @DisplayName("Costruttore Ricerca User: Input Testo -> Cognome")
        void testSearchConstructor_UserString() {
            // Se l'input contiene lettere, User(String) lo assegna a surname
            String inputUser = "Rossi";
            String inputBook = "TestBook";
            
            Loan searchLoan = new Loan(inputUser, inputBook);

            assertNotNull(searchLoan.getUser());
            // Verifica logica User: testo finisce in surname
            assertEquals("Rossi", searchLoan.getUser().getSurname());
            // La matricola dovrebbe essere null
            assertNull(searchLoan.getUser().getNumberId());
        }
        
        @Test
        @DisplayName("Costruttore Ricerca Book: Input -> Popola tutto (Unified Search)")
        void testSearchConstructor_BookUnified() {
            // Il costruttore di ricerca di Book popola Title, Authors e ISBN con la stessa stringa
            String inputUser = "123";
            String inputBook = "Java";
            
            Loan searchLoan = new Loan(inputUser, inputBook);
            Book createdBook = searchLoan.getBook();

            assertNotNull(createdBook);
            assertAll("Verifica propagazione query su Book",
                () -> assertEquals("Java", createdBook.getTitle()),
                () -> assertEquals("Java", createdBook.getAuthors()),
                () -> assertEquals("Java", createdBook.getISBN())
            );
        }

        @Test
        @DisplayName("Costruttore Ricerca: Robustezza Null")
        void testSearchConstructor_Nulls() {
            Loan emptyLoan = new Loan(null, null);
            assertAll("Verifica gestione null",
                () -> assertNull(emptyLoan.getUser()),
                () -> assertNull(emptyLoan.getBook()),
                () -> assertNull(emptyLoan.getDueDate())
            );
        }
    }

    @Nested
    @DisplayName("Test Metodi Wrapper (Delegation)")
    class WrapperTests {

        @Test
        @DisplayName("Wrapper: delegano correttamente ai campi di User e Book")
        void testWrapperDelegation() {
            // Questi valori derivano dal setUp()
            assertAll("Verifica valori wrapper",
                () -> assertEquals("Mario", loan.getName()),
                () -> assertEquals("Rossi", loan.getSurname()),
                () -> assertEquals("001", loan.getNumberId()),
                () -> assertEquals("Java Programming", loan.getTitle()),
                () -> assertEquals("ISBN-123", loan.getISBN())
            );
        }

        @Test
        @DisplayName("Wrapper: gestiscono User/Book null senza crashare")
        void testWrapperNullSafety() {
            Loan nullLoan = new Loan(null, null, null);

            assertAll("Verifica ritorno stringhe vuote",
                () -> assertEquals("", nullLoan.getName()),
                () -> assertEquals("", nullLoan.getSurname()),
                () -> assertEquals("", nullLoan.getNumberId()),
                () -> assertEquals("", nullLoan.getTitle()),
                () -> assertEquals("", nullLoan.getAuthors()),
                () -> assertEquals("", nullLoan.getISBN())
            );
        }
    }

    @Nested
    @DisplayName("Test Identità (Equals & HashCode)")
    class IdentityTests {

        @Test
        @DisplayName("equals: true per stessi UserID e ISBN")
        void testEqualsTrue() {
            // Creiamo un nuovo utente con STESSO ID ma dati diversi
            User u2 = new User("MarioClone", "RossiClone", "001", "email@fake.com");
            // Creiamo un nuovo libro con STESSO ISBN ma dati diversi
            Book b2 = new Book("TitleClone", "AuthClone", "2022", "ISBN-123", 10);
            
            Loan loan2 = new Loan(b2, u2, LocalDate.now()); // Data diversa ininfluente per equals

            assertEquals(loan, loan2);
        }

        @Test
        @DisplayName("equals: false se UserID differisce")
        void testEqualsFalseUser() {
            User uDiff = new User("Mario", "Rossi", "999", "email"); // ID diverso
            Loan lDiffUser = new Loan(book, uDiff, dueDate);

            assertNotEquals(loan, lDiffUser);
        }

        @Test
        @DisplayName("equals: false se ISBN differisce")
        void testEqualsFalseBook() {
            Book bDiff = new Book("Java", "Bruno", "2020", "ISBN-999", 5); // ISBN diverso
            Loan lDiffBook = new Loan(bDiff, user, dueDate);

            assertNotEquals(loan, lDiffBook);
        }

        @Test
        @DisplayName("hashCode: consistente con equals")
        void testHashCode() {
            // User e Book "cloni" (stessi ID/ISBN)
            User u2 = new User("A", "B", "001", "C");
            
            // CORREZIONE: Cambiato "F" in "2020" per rispettare il formato dell'anno
            Book b2 = new Book("D", "E", "2020", "ISBN-123", 1);
            
            Loan loan2 = new Loan(b2, u2, dueDate);

            assertEquals(loan.hashCode(), loan2.hashCode());
        }
    }

    @Nested
    @DisplayName("Test Ordinamento (CompareTo)")
    class OrderTests {

        @Test
        @DisplayName("compareTo: ordina in base alla data di scadenza")
        void testCompareTo() {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);
            
            Loan lToday = new Loan(book, user, today);
            Loan lTomorrow = new Loan(book, user, tomorrow);

            assertAll("Verifica ordinamento temporale",
                () -> assertTrue(lToday.compareTo(lTomorrow) < 0, "Oggi deve venire prima di domani"),
                () -> assertTrue(lTomorrow.compareTo(lToday) > 0, "Domani deve venire dopo oggi"),
                () -> assertEquals(0, lToday.compareTo(lToday), "Stesso oggetto = 0")
            );
        }

        @Test
        @DisplayName("compareTo: lancia eccezione con null")
        void testCompareToNull() {
            assertThrows(IllegalArgumentException.class, () -> loan.compareTo(null));
        }
    }

    @Test
    @DisplayName("toString: non restituisce null")
    void testToString() {
        String result = loan.toString();
        assertNotNull(result);
        assertTrue(result.contains(dueDate.toString()));
    }
}