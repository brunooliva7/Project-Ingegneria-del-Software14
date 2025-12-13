/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

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
 *
 * @author bruno
 */
public class LoanManagementTest {
    
 private LoanManagement loanManagement;
    
    // Oggetti di supporto
    private User user1;
    private User user2;
    private Book book1;
    private Book book2;
    
    // Prestiti da testare
    private Loan loan1;
    private Loan loan2;
    private Loan loanNew; // Per l'update
    private Loan loanNotPresent;

    @BeforeEach
    void setUp() {
        loanManagement = new LoanManagement();
        
        // Puliamo la lista in memoria per evitare interferenze dai test precedenti o dal file
        loanManagement.getLoan().clear();

        // 1. Creazione degli UTENTI (Name, Surname, ID, Email)
        user1 = new User("Mario", "Rossi", "001", "mario.rossi@studenti.unisa.it");
        user2 = new User("Luigi", "Bianchi", "002", "luigi.bianchi@studenti.unisa.it");

        // 2. Creazione dei LIBRI (Title, Authors, Year, ISBN, AvailableCopies)
        book1 = new Book("Java Programming", "Joshua Bloch", LocalDate.of(2018, 1, 1), "978-88-1234", 5);
        book2 = new Book("Ingegneria del Software", "Sommerville", LocalDate.of(2020, 5, 15), "978-88-5678", 3);

        // 3. Creazione dei PRESTITI (User, Book, Data Scadenza/Inizio)
        // Prestito 1: Mario prende il libro Java
        loan1 = new Loan(book1, user1, LocalDate.of(2025, 1, 10));
        
        // Prestito 2: Luigi prende il libro Ingegneria (Data diversa)
        loan2 = new Loan(book2, user2, LocalDate.of(2025, 1, 15));

        // Prestito Nuovo (per testare l'update): Mario rinnova o cambia data
        loanNew = new Loan(book1, user1, LocalDate.of(2025, 2, 10));
        
        // Prestito "Fantasma" (mai inserito)
        loanNotPresent = new Loan(book2, user1, LocalDate.of(2025, 3, 1));
    }

    // --- TEST METODO ADD ---

    @Test
    void testAddValidLoan() {
        boolean result = loanManagement.add(loan1);
        
        assertTrue(result, "Il metodo add dovrebbe restituire true per un prestito valido");
        assertEquals(1, loanManagement.getLoan().size(), "La lista dei prestiti dovrebbe contenere 1 elemento");
    }

    @Test
    void testAddNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManagement.add(null), 
            "Aggiungere null deve lanciare IllegalArgumentException");
    }

    // --- TEST METODO REMOVE ---
    // Questo testa specificamente la tua logica con l'ITERATOR
    
    @Test
    void testRemoveExistingLoan() {
        loanManagement.add(loan1); // Aggiungo
        
        // Rimuovo usando un oggetto identico per equals()
        // Grazie all'Iteratore nel tuo codice, questo funziona anche se compareTo è diverso/impreciso
        boolean result = loanManagement.remove(loan1);
        
        assertTrue(result, "La rimozione dovrebbe restituire true");
        assertEquals(0, loanManagement.getLoan().size(), "La lista dovrebbe essere vuota dopo la rimozione");
    }

    @Test
    void testRemoveNonExistingLoan() {
        loanManagement.add(loan1);
        
        // Provo a rimuovere un prestito che non c'è
        boolean result = loanManagement.remove(loanNotPresent);
        
        assertFalse(result, "Dovrebbe restituire false se si prova a rimuovere un prestito inesistente");
        assertEquals(1, loanManagement.getLoan().size(), "La dimensione della lista non deve cambiare");
    }

    @Test
    void testRemoveNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManagement.remove(null));
    }

    // --- TEST METODO UPDATE ---
    
    @Test
    void testUpdateLoan() {
        loanManagement.add(loan1); // Inserisco il prestito originale
        
        // Aggiorno: sostituisco loan1 con loanNew
        // Il tuo codice cerca loan1 (con l'iteratore e equals), lo rimuove e inserisce loanNew
        boolean result = loanManagement.update(loanNew, loan1);
        
        assertTrue(result, "L'update dovrebbe restituire true");
        
        // Verifico che loan1 NON ci sia più
        assertNull(loanManagement.search(loan1), "Il vecchio prestito dovrebbe essere stato rimosso");
        
        // Verifico che loanNew SIA presente
        assertNotNull(loanManagement.search(loanNew), "Il nuovo prestito dovrebbe essere presente");
    }

    @Test
    void testUpdateNonExistingLoan() {
        // Cerco di aggiornare un prestito che non esiste
        boolean result = loanManagement.update(loanNew, loanNotPresent);
        
        assertFalse(result, "L'update deve fallire se il prestito da modificare non esiste");
    }

    @Test
    void testUpdateNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManagement.update(null, loan1));
        assertThrows(IllegalArgumentException.class, () -> loanManagement.update(loanNew, null));
    }

    // --- TEST METODO SEARCH ---

    @Test
    void testSearchExistingLoan() {
        loanManagement.add(loan1);
        
        Loan found = loanManagement.search(loan1);
        
        assertNotNull(found, "Il prestito inserito dovrebbe essere trovato");
        assertEquals(loan1, found, "L'oggetto trovato deve essere uguale a quello cercato");
    }

    @Test
    void testSearchNotFound() {
        loanManagement.add(loan1);
        
        Loan found = loanManagement.search(loanNotPresent);
        
        assertNull(found, "Dovrebbe restituire null se il prestito non esiste");
    }
    
    
     // --- TEST VIEWSORTED ---
    @Test
    void testViewSortedOrder() {
        
        // Aggiungo in ordine sparso
        loanManagement.add(loan2); 
        loanManagement.add(loan1); 
        
        // Il TreeSet (loanManagement.getLoan()) ordina per data di scadenza (più vicina precede).
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

    @Test
    void testViewSortedEmpty() {
        // Assicurati che l'esecuzione su catalogo vuoto non dia errori
        assertDoesNotThrow(() -> loanManagement.viewSorted(), "viewSorted su catalogo vuoto non deve lanciare eccezioni.");
    }
}
