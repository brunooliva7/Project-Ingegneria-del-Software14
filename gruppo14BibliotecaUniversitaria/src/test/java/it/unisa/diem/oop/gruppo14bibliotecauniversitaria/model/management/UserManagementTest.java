/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
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
public class UserManagementTest {
    
   private UserManagement userManagement;
    private User user1;
    private User user2;
    private User userNotPresent;

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
    @Test
    void testAddValidUser() {
        boolean result = userManagement.add(user1);
        assertTrue(result, "Dovrebbe restituire true quando si aggiunge un utente valido");
        assertEquals(1, userManagement.getList().size(), "La lista dovrebbe contenere 1 utente");
    }

    @Test
    void testAddDuplicateUser() {
        userManagement.add(user1);
        boolean result = userManagement.add(user1); // Provo ad aggiungere lo stesso utente
        
        assertFalse(result, "Dovrebbe restituire false se l'utente (stessa matricola) esiste già");
        assertEquals(1, userManagement.getList().size(), "La dimensione non dovrebbe aumentare");
    }

    @Test
    void testAddNullUser() {
        assertThrows(IllegalArgumentException.class, () -> userManagement.add(null));
    }

    // --- TEST REMOVE ---
    @Test
    void testRemoveExistingUser() {
        userManagement.add(user1);
        boolean result = userManagement.remove(user1);
        
        assertTrue(result, "Dovrebbe restituire true se rimuove un utente esistente");
        assertEquals(0, userManagement.getList().size(), "La lista dovrebbe essere vuota");
    }

    @Test
    void testRemoveNonExistingUser() {
        userManagement.add(user1);
        boolean result = userManagement.remove(userNotPresent);
        
        assertFalse(result, "Dovrebbe restituire false se si prova a rimuovere un utente che non c'è");
        assertEquals(1, userManagement.getList().size(), "La lista non dovrebbe cambiare");
    }

    // --- TEST UPDATE ---
    @Test
    void testUpdateUser() {
        userManagement.add(user1);
        
        // Aggiorno user1 facendolo diventare user2
        // Parametri: (vecchioUtente, nuovoUtente) -> Attenzione all'ordine nel tuo metodo update!
        // Nel tuo codice hai scritto update(User u1, User u2) dove u1 è il vecchio (da rimuovere) e u2 il nuovo.
        boolean result = userManagement.update(user1, user2);
        
        assertTrue(result, "Update dovrebbe restituire true");
        assertFalse(userManagement.getList().contains(user1), "Il vecchio utente non deve più esistere");
        assertTrue(userManagement.getList().contains(user2), "Il nuovo utente deve essere presente");
    }

    // --- TEST SEARCH ---
    
    @Test
    void testSearchByMatricola() {
        userManagement.add(user1); // Matricola "0001"
        
        // Creiamo un oggetto "sonda" che ha la stessa matricola
        User searchCriteria = new User("Test", "Test", "0001", "test@test.it");
        
        User found = userManagement.search(searchCriteria);
        assertNotNull(found, "Dovrebbe trovare l'utente tramite matricola");
        assertEquals("Mario", found.getName(), "Dovrebbe restituire l'oggetto completo corretto");
    }

    @Test
    void testSearchByCognome() {
        userManagement.add(user2); // Cognome "Bianchi"
        
        // IMPORTANTE: Per testare il ramo "else if(surname != null)" del tuo codice,
        // la matricola DEVE essere null, altrimenti entra nel primo if.
        User searchCriteria = new User(null, "Bianchi", null, null);
        
        User found = userManagement.search(searchCriteria);
        
        // Se il test fallisce qui, verifica che il costruttore di User accetti null!
        assertNotNull(found, "Dovrebbe trovare l'utente tramite cognome");
        assertEquals("0002", found.getNumberId(), "Dovrebbe aver trovato l'utente Luigi Bianchi");
    }

    @Test
    void testSearchNotFound() {
        userManagement.add(user1);
        User found = userManagement.search(userNotPresent);
        assertNull(found, "Dovrebbe restituire null se l'utente non viene trovato");
    }
    
}
