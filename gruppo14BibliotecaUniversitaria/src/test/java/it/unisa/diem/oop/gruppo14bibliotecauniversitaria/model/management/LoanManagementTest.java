/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Loan;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
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
public class LoanManagementTest {
    
      private LoanManagement loanManagement;
    private User user1;
    private User user2;
    private Book book1;
    private Book book2;
    private Loan loan1;
    private Loan loan2;

    @BeforeEach
    void setUp() {
        loanManagement = new LoanManagement();

        // Creazione utenti
        user1 = new User("Mario", "Rossi", "U001", "mario.rossi@unisa.it");
        user2 = new User("Luigi", "Bianchi", "U002", "luigi.bianchi@unisa.it");

        // Creazione libri
        book1 = new Book("Il Nome della Rosa", "Umberto Eco", LocalDate.of(1980, 1, 1), "ISBN001", 3);
        book2 = new Book("Divina Commedia", "Dante Alighieri", LocalDate.of(1320, 1, 1), "ISBN002", 2);

        // Creazione prestiti
        loan1 = new Loan(book1, user1, LocalDate.now());
        loan2 = new Loan(book2, user2, LocalDate.now());
    }

    @Test
    void testAddLoan() {
        boolean added = loanManagement.add(loan1);
        assertTrue(added, "Il prestito dovrebbe essere aggiunto");
        assertTrue(loanManagement.getLoan().contains(loan1));
    }

    @Test
    void testAddDuplicateLoan() {
        loanManagement.add(loan1);
        boolean addedAgain = loanManagement.add(loan1);
        assertFalse(addedAgain, "Non deve aggiungere un prestito duplicato");
    }

    @Test
    void testRemoveLoan() {
        loanManagement.add(loan1);
        boolean removed = loanManagement.remove(loan1);
        assertTrue(removed, "Il prestito dovrebbe essere rimosso");
        assertFalse(loanManagement.getLoan().contains(loan1));
    }

    @Test
    void testRemoveNonExistingLoan() {
        boolean removed = loanManagement.remove(loan2);
        assertFalse(removed, "Non deve rimuovere un prestito inesistente");
    }

    @Test
    void testUpdateLoan() {
        loanManagement.add(loan1);
        Loan updatedLoan = new Loan(book2, user1, LocalDate.now());

        boolean updated = loanManagement.update(updatedLoan, loan1);
        assertTrue(updated, "Il prestito dovrebbe essere aggiornato");
        assertTrue(loanManagement.getLoan().contains(updatedLoan));
        assertFalse(loanManagement.getLoan().contains(loan1));
    }

    @Test
    void testUpdateNonExistingLoan() {
        boolean updated = loanManagement.update(loan2, loan1);
        assertFalse(updated, "Non deve aggiornare un prestito inesistente");
    }

    @Test
    void testSearchLoan() {
        loanManagement.add(loan1);
        Loan found = loanManagement.search(loan1);
        assertNotNull(found, "Il prestito dovrebbe essere trovato");
        assertEquals(loan1, found);
    }

    @Test
    void testSearchNonExistingLoan() {
        Loan found = loanManagement.search(loan2);
        assertNull(found, "Il prestito non dovrebbe essere trovato");
    }

    @Test
    void testGetLoanReturnsSet() {
        assertNotNull(loanManagement.getLoan(), "Il set dei prestiti non deve essere null");
        assertTrue(loanManagement.getLoan().isEmpty(), "All'inizio deve essere vuoto");
    }
    
}
