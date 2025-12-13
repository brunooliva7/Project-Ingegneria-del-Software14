/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

/**
 * @file UserManagementTest.java
 *
 * @author bruno
 * @date 12-12-2025
 */
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @class UserManagementTest
 * 
 * @brief Implementa una suite di test JUnit per la classe {@link it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement}
 * 
 */
public class UserManagementTest {
    
    private UserManagement userManagement; ///< Istanza della classe da testare
    private User user1; ///< Utente di test 1
    private User user2; ///< Utente di test 2
    private User userNotPresent; ///< Utente non presente

    /**
     * @brief Configurazione eseguita prima di ogni test.
     * * Inizializza un nuovo {@link UserManagement} e gli oggetti {@link User}
     * di supporto. Pulisce la lista interna per garantire l'isolamento dei test.
     */
    @BeforeEach
    void setUp() {
        userManagement = new UserManagement();

        // Inizializzo gli utenti con: Nome, Cognome, Matricola, Email
        user1 = new User("Mario", "Rossi", "0001", "mario.rossi@studenti.unisa.it");
        user2 = new User("Luigi", "Bianchi", "0002", "luigi.bianchi@studenti.unisa.it");
        
        // Questo utente non verrà aggiunto alla lista, serve per i test negativi
        userNotPresent = new User("Anna", "Verdi", "0003", "anna.verdi@studenti.unisa.it");
        
        // Pulisco la lista prima di ogni test per evitare interferenze col file
        userManagement.getList().clear();
    }

    // --- TEST ADD ---
    
    /**
     * @brief Testa l'aggiunta di un utente valido.
     * * Verifica che il metodo {@code add} restituisca true e incrementi la dimensione della lista.
     */
    @Test
    void testAddValidUser() {
        boolean result = userManagement.add(user1);
        assertTrue(result, "Dovrebbe restituire true quando si aggiunge un utente valido");
        assertEquals(1, userManagement.getList().size(), "La lista dovrebbe contenere 1 utente");
    }

    /**
     * @brief Testa l'aggiunta di un utente duplicato (stessa matricola).
     * * Verifica che il metodo restituisca false e che la dimensione della lista non cambi.
     */
    @Test
    void testAddDuplicateUser() {
        userManagement.add(user1);
        boolean result = userManagement.add(user1); // Provo ad aggiungere lo stesso utente
        
        assertFalse(result, "Dovrebbe restituire false se l'utente (stessa matricola) esiste già");
        assertEquals(1, userManagement.getList().size(), "La dimensione non dovrebbe aumentare");
    }

    /**
     * @brief Testa l'aggiunta di un riferimento nullo.
     * * Verifica che il metodo lanci {@code IllegalArgumentException}.
     */
    @Test
    void testAddNullUser() {
        assertThrows(IllegalArgumentException.class, () -> userManagement.add(null));
    }

    // --- TEST REMOVE ---
    
    /**
     * @brief Testa la rimozione di un utente esistente.
     * * Verifica che il metodo restituisca true e che la lista risulti vuota.
     */
    @Test
    void testRemoveExistingUser() {
        userManagement.add(user1);
        boolean result = userManagement.remove(user1);
        
        assertTrue(result, "Dovrebbe restituire true se rimuove un utente esistente");
        assertEquals(0, userManagement.getList().size(), "La lista dovrebbe essere vuota");
    }

    /**
     * @brief Testa la rimozione di un utente non esistente.
     * * Verifica che il metodo restituisca false e che la dimensione della lista non cambi.
     */
    @Test
    void testRemoveNonExistingUser() {
        userManagement.add(user1);
        boolean result = userManagement.remove(userNotPresent);
        
        assertFalse(result, "Dovrebbe restituire false se si prova a rimuovere un utente che non c'è");
        assertEquals(1, userManagement.getList().size(), "La lista non dovrebbe cambiare");
    }
    
    /**
     * @brief Testa la rimozione di un riferimento nullo.
     * * Verifica che il metodo lanci {@code IllegalArgumentException}.
     */
    @Test
    void testRemoveNullUserThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userManagement.remove(null));
    }

    // --- TEST UPDATE ---
    /**
     * @brief Testa l'aggiornamento di un utente esistente.
     * * Verifica che l'utente originale sia rimosso e quello nuovo sia aggiunto
     * (si assume che l'update gestisca la sostituzione completa dell'oggetto nel Set).
     */
    @Test
    void testUpdateUser() {
        userManagement.add(user1);
        
        // Aggiorno user1 facendolo diventare user2
        boolean result = userManagement.update(user1, user2);
        
        assertTrue(result, "Update dovrebbe restituire true");
        assertFalse(userManagement.getList().contains(user1), "Il vecchio utente non deve più esistere");
        assertTrue(userManagement.getList().contains(user2), "Il nuovo utente deve essere presente");
    }
    
    /**
     * @brief Testa l'aggiornamento di un utente non presente nella lista.
     * Verifica che l'update fallisca (false).
     */
    @Test
    void testUpdateNonExistingUser() {
        // Tento di aggiornare userNotPresent che non è nel Set
        boolean result = userManagement.update(userNotPresent, user1);
        assertFalse(result, "Update dovrebbe fallire se il libro da modificare non esiste");
    }
    
    /**
     * @brief Testa l'aggiornamento quando l'utente da modificare è nullo.
     * * Verifica che venga lanciata {@code IllegalArgumentException}.
     */
    @Test
    void testUpdateNullOldUserThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userManagement.update(null, user2));
    }

    // --- TEST SEARCH ---
    
    /**
     * @brief Testa la ricerca di un utente utilizzando la matricola.
     * * Verifica che venga trovato l'utente corretto utilizzando solo la matricola come criterio.
     */
    @Test
    void testSearchByMatricola() {
        userManagement.add(user1); // Matricola "0001"
        
        // Creiamo un oggetto "sonda" che ha la stessa matricola
        User searchCriteria = new User("Test", "Test", "0001", "test@test.it");
        
        User found = userManagement.search(searchCriteria);
        assertNotNull(found, "Dovrebbe trovare l'utente tramite matricola");
        assertEquals("Mario", found.getName(), "Dovrebbe restituire l'oggetto completo corretto");
    }

    /**
     * @brief Testa la ricerca di un utente utilizzando il cognome.
     * * Si assume che la ricerca fallisca sulla matricola (perché null) e prosegua sul cognome.
     */
    @Test
    void testSearchByCognome() {
        userManagement.add(user2); // Cognome "Bianchi"
        
        // la matricola DEVE essere null, altrimenti entra nel primo if.
        User searchCriteria = new User(null, "Bianchi", null, null);
        
        User found = userManagement.search(searchCriteria);
        
        assertNotNull(found, "Dovrebbe trovare l'utente tramite cognome");
        assertEquals("0002", found.getNumberId(), "Dovrebbe aver trovato l'utente Luigi Bianchi");
    }

    /**
     * @brief Testa la ricerca di un utente non trovato.
     * * Verifica che il metodo restituisca null.
     */
    @Test
    void testSearchNotFound() {
        userManagement.add(user1);
        User found = userManagement.search(userNotPresent);
        assertNull(found, "Dovrebbe restituire null se l'utente non viene trovato");
    }
    
    /**
     * @brief Testa la ricerca con un riferimento utente nullo.
     * * Verifica che il metodo lanci {@code IllegalArgumentException}.
     */
    @Test
    void testSearchNullUserThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userManagement.search(null));
    }
    
    // --- TEST VIEWSORTED ---
    /**
     * @brief Testa l'ordinamento degli utenti.
     * * Verifica che gli utenti siano ordinati alfabeticamente per Cognome (come previsto dal interno).
     */
    @Test
    void testViewSortedOrder() {
        userManagement.add(user1); 
        userManagement.add(user2); 
        
        // TreeSet ordina per Cognome e poi Nome. Bianchi (B) viene prima di Rossi (R).
        Set<User> list = userManagement.getList();
        
        Iterator<User> iterator = list.iterator();
        
        User first = iterator.next();  // Ci aspettiamo Bianchi (user2)
        User second = iterator.next(); // Ci aspettiamo Rossi (user1)
        
        assertEquals(user2.getNumberId(), first.getNumberId(), "Il primo utente deve essere Bianchi (B prima di R).");
        assertEquals(user1.getNumberId(), second.getNumberId(), "Il secondo utente deve essere Rossi.");
        
        assertDoesNotThrow(() -> userManagement.viewSorted(), "viewSorted non deve lanciare eccezioni.");
    }

    /**
     * @brief Testa il metodo viewSorted su una lista vuota.
     * * Verifica che non vengano lanciate eccezioni.
     */
    @Test
    void testViewSortedEmpty() {
        assertDoesNotThrow(() -> userManagement.viewSorted(), "viewSorted su catalogo vuoto non deve lanciare eccezioni.");
    }
}
