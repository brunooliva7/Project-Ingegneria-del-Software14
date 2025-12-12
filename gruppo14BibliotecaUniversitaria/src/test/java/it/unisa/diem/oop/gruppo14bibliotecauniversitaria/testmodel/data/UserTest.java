/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

/**
 * @file UserTest.java
 *
 * @author maramariano
 * @date 12-12-2025
 * @version 1.0
 */
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class UserTest
 * @brief Classe di test per la classe User utilizzando JUnit 5.
 */
class UserTest {

    private User user1;
    private User user2_sameIdAs1;
    private User user3_different;
    
    // Oggetti di supporto necessari per la mappa booksOnloan
    private Book bookA;
    private Book bookB;
    private LocalDate dueDateA;
    private LocalDate dueDateB;

    /**
     * @brief Configurazione iniziale eseguita prima di ogni metodo di test.
     */
    @BeforeEach
    void setUp() {
        // Inizializzazione degli oggetti Book con ISBN DIVERSI
        bookA = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, 8, 11), "978-0132350884", 1);
        bookB = new Book("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.of(1925, 4, 10), "978-0743273565", 1);
        dueDateA = LocalDate.now().plusDays(10);
        dueDateB = LocalDate.now().plusDays(5);
        
        // Utente standard
        user1 = new User("Mario", "Rossi", "S123456", "mario.rossi@unisa.it");
        
        // Utente con la stessa matricola di user1
        user2_sameIdAs1 = new User("Luigi", "Verdi", "S123456", "luigi.verdi@unisa.it");
        
        // Utente completamente diverso
        user3_different = new User("Anna", "Bianchi", "A987654", "anna.bianchi@unisa.it");
    }

    // --- TEST COSTUTTORI, GETTER e SETTER ---

    @Test
    void testStandardConstructorAndGetters() {
        assertEquals("Mario", user1.getName());
        assertEquals("Rossi", user1.getSurname());
        assertEquals("S123456", user1.getNumberId());
        assertEquals("mario.rossi@unisa.it", user1.getEmail());
        assertTrue(user1.getBooksOnloan() instanceof TreeMap, "La mappa deve essere un'istanza di TreeMap");
        assertTrue(user1.getBooksOnloan().isEmpty(), "La mappa booksOnloan deve essere vuota inizialmente");
    }

    @Test
    void testSearchConstructor() {
        // Ricerca per Matricola
        User searchById = new User("777123"); 
        assertEquals("777123", searchById.getNumberId());
        assertNull(searchById.getSurname()); 
        
        // Ricerca per Cognome
        User searchBySurname = new User("Ferrari"); 
        assertEquals("Ferrari", searchBySurname.getSurname());
        assertNull(searchBySurname.getNumberId());
    }

    @Test
    void testSetters() {
        user1.setName("Giuseppe");
        user1.setSurname("Bianchi");
        user1.setNumberId("999000");
        user1.setEmail("nuova.email@test.it");
        
        TreeMap<Book, LocalDate> newMap = new TreeMap<>();
        newMap.put(bookB, dueDateB);
        user1.setBooksOnLoan(newMap);

        assertEquals("Giuseppe", user1.getName());
        assertEquals("Bianchi", user1.getSurname());
        assertEquals("999000", user1.getNumberId());
        assertEquals("nuova.email@test.it", user1.getEmail());
        assertEquals(newMap, user1.getBooksOnloan());
    }
    
    @Test
    void testFindLoans() {
        user1.findLoans(bookA, dueDateA);
        user1.findLoans(bookB, dueDateB);
        
        Map<Book, LocalDate> loanedBooks = user1.getBooksOnloan();
        
        assertEquals(2, loanedBooks.size(), "La dimensione della mappa dovrebbe essere 2 per due libri diversi.");
        
        LocalDate newDueDate = LocalDate.now().plusDays(30);
        user1.findLoans(bookA, newDueDate);
        
        assertEquals(2, loanedBooks.size(), "L'aggiornamento non deve cambiare la dimensione.");
        assertEquals(newDueDate, loanedBooks.get(bookA), "La data di scadenza deve essere aggiornata.");
    }

    // --- TEST UGUALIANZA E ORDINAMENTO ---

    @Test
    void testCompareTo() {
        // 1. Cognomi diversi (Bianchi vs Rossi) -> Bianchi precede Rossi (< 0)
        assertTrue(user3_different.compareTo(user1) < 0, "Bianchi dovrebbe precedere Rossi (< 0)");

        // 2. Cognomi uguali, nomi diversi (Luigi vs Mario) -> Luigi precede Mario (< 0)
        User user5_sameSurname = new User("Luigi", "Rossi", "Y888", "l@test.it");
        assertTrue(user5_sameSurname.compareTo(user1) < 0, "Luigi Rossi dovrebbe precedere Mario Rossi (< 0)");
        
        // 3. Cognomi e nomi uguali (dovrebbe restituire 0)
        User user4_sameName = new User("Mario", "Rossi", "Z999", "m@test.it");
        assertTrue(user1.compareTo(user4_sameName) == 0, "Stesso cognome e nome dovrebbero restituire 0");
    }

    @Test
    void testEquals() {
        assertTrue(user1.equals(user1));
        assertTrue(user1.equals(user2_sameIdAs1), "Gli utenti con la stessa matricola devono essere uguali (true)");
        assertFalse(user1.equals(user3_different), "Utenti con matricole diverse non devono essere uguali (false)");
        assertFalse(user1.equals(null));
    }

    @Test
    void testHashCode() {
        assertEquals(user1.hashCode(), user2_sameIdAs1.hashCode(), "L'hash code dovrebbe essere lo stesso se numberId è lo stesso");
        assertNotEquals(user1.hashCode(), user3_different.hashCode(), "L'hash code dovrebbe essere diverso se numberId è diverso");
    }

    /**
     * @brief Test del metodo toString.
     * * CORREGGE IL PROBLEMA DELLA SPAZIATURA DEL NEWLINE.
     */
    @Test
    void testToString() {
        user1.findLoans(bookA, dueDateA);
        
        String expectedBookString = bookA.toString(); 
        
        // *** CORREZIONE: Usiamo .append("\n") senza spazi o tabulazioni dopo. ***
        String expectedToString = new StringBuilder()
            .append("User{name='Mario', surname='Rossi', numberId='S123456', email='mario.rossi@unisa.it', booksOnLoan={")
            .append("\n").append(expectedBookString).append(" -> ").append(dueDateA) 
            .append("}}")
            .toString();

        assertEquals(expectedToString, user1.toString());
        
        // Verifica toString con lista vuota
        User userEmptyLoans = new User("Luigi", "Verdi", "S789", "l@test.it");
        String expectedEmptyToString = "User{name='Luigi', surname='Verdi', numberId='S789', email='l@test.it', booksOnLoan={}}";
        assertEquals(expectedEmptyToString, userEmptyLoans.toString());
    }
}