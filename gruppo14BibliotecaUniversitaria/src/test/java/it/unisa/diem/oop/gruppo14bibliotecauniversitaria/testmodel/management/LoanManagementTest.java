package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class LoanManagementTest
 * @brief Test per la classe LoanManagement.
 * * Verifica le operazioni CRUD (Add, Remove, Update) e la logica di ricerca
 * complessa basata su filtri Utente e Libro.
 */
class LoanManagementTest {

    private LoanManagement loanManager;
    private Loan loan1;
    private Loan loan2;
    private File dbFile;

    @BeforeEach
    void setUp() {
        // 1. Inizializziamo il manager
        loanManager = new LoanManagement();
        
        // 2. Pulizia preventiva: assicuriamoci che la lista in memoria sia vuota
        // per evitare interferenze da esecuzioni precedenti se il file esisteva.
        if (!loanManager.getLoan().isEmpty()) {
            loanManager.getLoan().clear();
        }

        // 3. Creazione dati di test
        // Usiamo i costruttori completi forniti:
        // User(String name, String surname, String numberId, String email)
        User user1 = new User("Mario", "Rossi", "001", "email1@test.com");
        User user2 = new User("Luigi", "Verdi", "002", "email2@test.com");
        
        // Book(String title, String authors, String publicationYear, String ISBN, int availableCopies)
        Book book1 = new Book("Java Programming", "Authors A", "2020", "111", 5);
        Book book2 = new Book("Python Basics", "Authors B", "2021", "222", 3);

        loan1 = new Loan(book1, user1, LocalDate.now().plusDays(30));
        loan2 = new Loan(book2, user2, LocalDate.now().plusDays(15));
        
        // Riferimento al file per la pulizia nel tearDown
        dbFile = new File("loan_Database.dat");
    }

    @AfterEach
    void tearDown() {
        // Elimina il file di database creato dai test per non sporcare il progetto
        // Questo garantisce che ogni test parta da zero
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @Nested
    @DisplayName("Test Gestione Base (Add/Remove)")
    class BasicManagementTests {

        @Test
        @DisplayName("add: inserisce un prestito e aggiorna il database")
        void testAdd() {
            assertTrue(loanManager.add(loan1), "Dovrebbe restituire true per un nuovo prestito");
            assertEquals(1, loanManager.getLoan().size());
            assertTrue(loanManager.getLoan().contains(loan1));
            
            // Verifica che il file sia stato creato (persistenza)
            assertTrue(dbFile.exists(), "Il file database dovrebbe essere stato creato");
        }

        @Test
        @DisplayName("add: impedisce l'inserimento di duplicati")
        void testAddDuplicate() {
            loanManager.add(loan1);
            
            // Creiamo un duplicato logico (stesso utente, stesso libro)
            // Nota: Funziona solo se Loan implementa correttamente equals() e hashCode()
            Loan duplicate = new Loan(loan1.getBook(), loan1.getUser(), LocalDate.now());
            
            assertFalse(loanManager.add(duplicate), "Non dovrebbe aggiungere un prestito duplicato");
            assertEquals(1, loanManager.getLoan().size());
        }

        @Test
        @DisplayName("add: lancia eccezione se null")
        void testAddNull() {
            assertThrows(IllegalArgumentException.class, () -> loanManager.add(null));
        }

        @Test
        @DisplayName("remove: rimuove correttamente un prestito")
        void testRemove() {
            loanManager.add(loan1);
            loanManager.add(loan2);

            assertTrue(loanManager.remove(loan1));
            assertEquals(1, loanManager.getLoan().size());
            assertFalse(loanManager.getLoan().contains(loan1));
            assertTrue(loanManager.getLoan().contains(loan2));
        }

        @Test
        @DisplayName("remove: restituisce false se il prestito non esiste")
        void testRemoveNonExisting() {
            loanManager.add(loan1);
            assertFalse(loanManager.remove(loan2));
            assertEquals(1, loanManager.getLoan().size());
        }
    }

    @Nested
    @DisplayName("Test Aggiornamento (Update)")
    class UpdateTests {

        @Test
        @DisplayName("update: aggiorna un prestito esistente")
        void testUpdate() {
            loanManager.add(loan1);

            // Modifichiamo la data di scadenza (o qualsiasi altro campo non chiave)
            Loan updatedLoan = new Loan(loan1.getBook(), loan1.getUser(), LocalDate.now().plusDays(60));
            
            assertTrue(loanManager.update(updatedLoan, loan1));
            
            // Verifichiamo che la lista contenga l'oggetto aggiornato
            Loan stored = loanManager.getLoan().iterator().next();
            assertEquals(updatedLoan.getDueDate(), stored.getDueDate(), "La data di scadenza dovrebbe essere aggiornata");
        }
    }

    @Nested
    @DisplayName("Test Ricerca (Search)")
    class SearchTests {

        @BeforeEach
        void initData() {
            // Popoliamo con dati noti per i test di ricerca
            // Loan1: User(001, Mario Rossi), Book(111, Java)
            // Loan2: User(002, Luigi Verdi), Book(222, Python)
            if(!loanManager.getLoan().isEmpty()) loanManager.getLoan().clear();
            loanManager.add(loan1);
            loanManager.add(loan2);
        }

        @Test
        @DisplayName("search: filtro per Matricola Utente")
        void testSearchByMatricola() {
            // Costruttore ricerca: Loan(String userQuery, String bookQuery)
            // Cerchiamo matricola "001" (Utente Mario)
            Loan filter = new Loan("001", ""); 
            
            List<Loan> results = loanManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(loan1, results.get(0));
        }

        @Test
        @DisplayName("search: filtro per Cognome Utente (parziale)")
        void testSearchByName() {
            // Cerchiamo "Verdi" -> Dovrebbe trovare Loan2
            // Nota: Se input non è numerico, Loan lo tratta come cognome
            Loan filter = new Loan("Verdi", "");
            
            List<Loan> results = loanManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(loan2, results.get(0));
        }

        @Test
        @DisplayName("search: filtro per ISBN Libro")
        void testSearchByISBN() {
            // Cerchiamo ISBN "222" -> Loan2
            Loan filter = new Loan("", "222");
            
            List<Loan> results = loanManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(loan2, results.get(0));
        }

        @Test
        @DisplayName("search: filtro per Titolo Libro (parziale)")
        void testSearchByTitle() {
            // Cerchiamo "Java" -> Loan1
            // Il costruttore di ricerca Book mette "Java" sia in titolo che ISBN
            Loan filter = new Loan("", "Java");
            
            List<Loan> results = loanManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(loan1, results.get(0));
        }

        @Test
        @DisplayName("search: filtro combinato (Utente AND Libro)")
        void testSearchCombined() {
            // Cerchiamo Utente "Mario" E Libro "Java" -> Match
            Loan filterMatch = new Loan("Mario", "Java");
            assertEquals(1, loanManager.search(filterMatch).size());

            // Cerchiamo Utente "Mario" E Libro "Python" -> No Match (Mario non ha Python)
            Loan filterNoMatch = new Loan("Mario", "Python");
            assertTrue(loanManager.search(filterNoMatch).isEmpty());
        }

        @Test
        @DisplayName("search: nessun risultato")
        void testSearchNoResults() {
            Loan filter = new Loan("NonEsiste", "NemmenoQuesto");
            assertTrue(loanManager.search(filter).isEmpty());
        }
        
        @Test
        @DisplayName("search: eccezione se filtro null")
        void testSearchNull() {
            assertThrows(IllegalArgumentException.class, () -> loanManager.search(null));
        }
    }
}