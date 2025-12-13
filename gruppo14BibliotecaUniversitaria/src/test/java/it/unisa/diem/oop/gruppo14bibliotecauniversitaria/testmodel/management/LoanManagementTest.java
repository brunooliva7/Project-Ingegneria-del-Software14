/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

/**
 * @file LoanManagementTest.java
 *
 * @author bruno
 * @date 12-12-2025
 */
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement;
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
 * @class LoanManagementTest
 * 
 * @brief Implementa una suite di test JUnit per la classe {@link it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.LoanManagement}
 * I prestiti vengono creati con oggetti {@link it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User}
 * e {@link it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book} di supporto.
 * 
 */
public class LoanManagementTest {
    
 private LoanManagement loanManagement; //< Istanza della classe
    
    // Oggetti di supporto
    private User user1; ///< Utente di test 1
    private User user2; ///< Utente 2
    private Book book1; ///< Libro di test 1
    private Book book2; ///< Libro 2
    
    // Prestiti da testare
    private Loan loan1; ///< Prestito 1
    private Loan loan2; ///< Prestito 2
    private Loan loanNew; ///< Prestito per l'update
    private Loan loanNotPresent; ///< Prestito non presente nel catalogo

    /**
     * @brief Configurazione eseguita prima di ogni test.
     * * Inizializza un nuovo {@link LoanManagement} e gli oggetti di supporto
     * ({@link User}, {@link Book}, {@link Loan}) per garantire l'isolamento dei test.
     */
    @BeforeEach
    void setUp() {
        loanManagement = new LoanManagement();
        
        // Puliamo la lista in memoria per evitare interferenze dai test precedenti o dal file
        loanManagement.getLoan().clear();

        // 1. Creazione degli UTENTI (Name, Surname, numberID, Email)
        user1 = new User("Mario", "Rossi", "001", "mario.rossi@studenti.unisa.it");
        user2 = new User("Luigi", "Bianchi", "002", "luigi.bianchi@studenti.unisa.it");

        // 2. Creazione dei LIBRI (Title, Authors, PublicationYear, ISBN, AvailableCopies)
        book1 = new Book("Java Programming", "Joshua Bloch", LocalDate.of(2018, 1, 1), "978-88-1234", 5);
        book2 = new Book("Ingegneria del Software", "Sommerville", LocalDate.of(2020, 5, 15), "978-88-5678", 3);

        // 3. Creazione dei PRESTITI (User, Book, Data Scadenza/Inizio)
        // Prestito 1: Mario prende il libro Java (Scadenza 10/01)
        loan1 = new Loan(book1, user1, LocalDate.of(2025, 1, 10));
        
        // Prestito 2: Luigi prende il libro Ingegneria (Data diversa: scadenza 15/01)
        loan2 = new Loan(book2, user2, LocalDate.of(2025, 1, 15));

        // Prestito Nuovo (per testare l'update): Mario rinnova o cambia data (stesso user/book)
        loanNew = new Loan(book1, user1, LocalDate.of(2025, 2, 10));
        
        // Prestito "Fantasma" (mai inserito)
        loanNotPresent = new Loan(book2, user1, LocalDate.of(2025, 3, 1));
    }

    // --- TEST METODO ADD ---

    /**
     * @brief Testa l'aggiunta di un prestito valido.
     * * Verifica che il metodo {@code add} restituisca true e incrementi la dimensione della lista.
     */
    @Test
    void testAddValidLoan() {
        boolean result = loanManagement.add(loan1);
        
        assertTrue(result, "Il metodo add dovrebbe restituire true per un prestito valido");
        assertEquals(1, loanManagement.getLoan().size(), "La lista dei prestiti dovrebbe contenere 1 elemento");
    }

    /**
     * @brief Testa l'aggiunta di un prestito nullo.
     * * Verifica che il metodo lanci {@code IllegalArgumentException}.
     */
    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManagement.add(null), 
            "Aggiungere null deve lanciare IllegalArgumentException");
    }

    // --- TEST METODO REMOVE ---
    
    /**
     * @brief Testa la rimozione di un prestito esistente.
     * * Verifica che la rimozione sia confermata (true) e che la lista si svuoti.
     */
    @Test
    void testRemoveExistingLoan() {
        loanManagement.add(loan1); // Aggiungo
        
        // Rimuovo usando un oggetto identico per equals()
        boolean result = loanManagement.remove(loan1);
        
        assertTrue(result, "La rimozione dovrebbe restituire true");
        assertEquals(0, loanManagement.getLoan().size(), "La lista dovrebbe essere vuota dopo la rimozione");
    }

    /**
     * @brief Testa la rimozione di un prestito non presente.
     * * Verifica che il metodo restituisca false e che la dimensione della lista rimanga invariata.
     */
    @Test
    void testRemoveNonExistingLoan() {
        loanManagement.add(loan1);
        
        // Provo a rimuovere un prestito che non c'è
        boolean result = loanManagement.remove(loanNotPresent);
        
        assertFalse(result, "Dovrebbe restituire false se si prova a rimuovere un prestito inesistente");
        assertEquals(1, loanManagement.getLoan().size(), "La dimensione della lista non deve cambiare");
    }

    /**
     * @brief Testa la rimozione di un riferimento nullo.
     * * Verifica che il metodo lanci {@code IllegalArgumentException}.
     */
    @Test
    void testRemoveNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManagement.remove(null));
    }

    // --- TEST METODO UPDATE ---
    
    /**
     * @brief Testa l'aggiornamento di un prestito esistente.
     * * Verifica che il prestito vecchio sia sostituito dal nuovo nella lista.
     */
    @Test
    void testUpdateLoan() {
        loanManagement.add(loan1); // Inserisco il prestito originale
        
        // Aggiorno: sostituisco loan1 con loanNew (nuova data)
        boolean result = loanManagement.update(loanNew, loan1);
        
        assertTrue(result, "L'update dovrebbe restituire true");
        
        // Verifico che loan1 NON ci sia più
        assertNull(loanManagement.search(loan1), "Il vecchio prestito dovrebbe essere stato rimosso");
        
        // Verifico che loanNew SIA presente
        assertNotNull(loanManagement.search(loanNew), "Il nuovo prestito dovrebbe essere presente");
    }

    /**
     * @brief Testa l'aggiornamento di un prestito non esistente.
     * * Verifica che l'update fallisca (false).
     */
    @Test
    void testUpdateNonExistingLoan() {
        // Cerco di aggiornare un prestito che non esiste
        boolean result = loanManagement.update(loanNew, loanNotPresent);
        
        assertFalse(result, "L'update deve fallire se il prestito da modificare non esiste");
    }

    /**
     * @brief Testa l'aggiornamento con uno o entrambi i parametri nulli.
     * * Verifica che venga lanciata {@code IllegalArgumentException}.
     */
    @Test
    void testUpdateNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManagement.update(null, loan1));
        assertThrows(IllegalArgumentException.class, () -> loanManagement.update(loanNew, null));
    }

    // --- TEST METODO SEARCH ---

    /**
     * @brief Testa la ricerca di un prestito esistente.
     * * Verifica che il prestito trovato sia non nullo e uguale all'oggetto cercato (tramite equals).
     */
    @Test
    void testSearchExistingLoan() {
        loanManagement.add(loan1);
        
        Loan found = loanManagement.search(loan1);
        
        assertNotNull(found, "Il prestito inserito dovrebbe essere trovato");
        assertEquals(loan1, found, "L'oggetto trovato deve essere uguale a quello cercato");
    }

    /**
     * @brief Testa la ricerca di un prestito non esistente.
     * * Verifica che venga restituito null.
     */
    @Test
    void testSearchNotFound() {
        loanManagement.add(loan1);
        
        Loan found = loanManagement.search(loanNotPresent);
        
        assertNull(found, "Dovrebbe restituire null se il prestito non esiste");
    }
    
    
     // --- TEST VIEWSORTED ---
    /**
     * @brief Testa l'ordinamento dei prestiti.
     * * Verifica che i prestiti siano ordinati in base alla data di scadenza (più vicina precede), 
     * in accordo con l'implementazione di {@code compareTo} in {@link Loan}.
     */
    @Test
    void testViewSortedOrder() {
        
        // Aggiungo in ordine sparso
        loanManagement.add(loan2); 
        loanManagement.add(loan1); 
        
        // Il TreeSet dovrebbe ordinare: loan1 (10/01) poi loan2 (15/01).
        Set<Loan> loanSet = loanManagement.getLoan();
        Iterator<Loan> iterator = loanSet.iterator();
        
        Loan first = iterator.next();  // Ci aspettiamo loan1 (10/01)
        Loan second = iterator.next(); // Ci aspettiamo loan2 (15/01)
        
        // Verifico l'ordinamento
        assertEquals(loan1.getDueDate(), first.getDueDate(), "Il primo prestito deve avere la scadenza più vicina (loan1).");
        assertEquals(loan2.getDueDate(), second.getDueDate(), "Il secondo prestito deve avere la scadenza più lontana (loan2).");
        
        // Verifico che viewSorted non lanci eccezioni (sebbene non verifichiamo l'output su console)
        assertDoesNotThrow(() -> loanManagement.viewSorted(), "viewSorted non deve lanciare eccezioni.");
    }

    /**
     * @brief Testa il metodo viewSorted su un catalogo vuoto.
     * * Verifica che non vengano lanciate eccezioni.
     */
    @Test
    void testViewSortedEmpty() {
        assertDoesNotThrow(() -> loanManagement.viewSorted(), "viewSorted su catalogo vuoto non deve lanciare eccezioni.");
    }
}
