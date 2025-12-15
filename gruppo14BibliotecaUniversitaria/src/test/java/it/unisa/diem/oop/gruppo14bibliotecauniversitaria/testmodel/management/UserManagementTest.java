package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.UserManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class UserManagementTest
 * @brief Suite di test JUnit 5 per UserManagement.
 * * Verifica le funzionalità CRUD (Create, Read, Update, Delete) e la ricerca
 * degli utenti nel sistema, gestendo anche la persistenza su file.
 */
class UserManagementTest {

    private UserManagement userManager;
    private User user1;
    private User user2;
    private File dbFile;

    @BeforeEach
    void setUp() {
        // 1. Inizializza il manager
        userManager = new UserManagement();

        // 2. Pulisci la memoria se carica dati vecchi dal file
        if (!userManager.getList().isEmpty()) {
            userManager.getList().clear();
        }

        // 3. Crea utenti di test
        // Assumo costruttore User(name, surname, numberId, email)
        user1 = new User("Mario", "Rossi", "001", "mario.rossi@email.it");
        user2 = new User("Luigi", "Verdi", "002", "luigi.verdi@email.it");

        // Riferimento al file per la pulizia nel tearDown
        dbFile = new File("archivio_utenti.dat");
    }

    @AfterEach
    void tearDown() {
        // Cancella il file database per non lasciare sporcizia
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @Nested
    @DisplayName("Test Gestione Base (Add/Remove)")
    class BasicManagementTests {

        @Test
        @DisplayName("add: aggiunge correttamente un utente")
        void testAddUser() {
            assertTrue(userManager.add(user1), "Dovrebbe restituire true all'aggiunta");
            assertEquals(1, userManager.getList().size());
            assertTrue(userManager.getList().contains(user1));

            // Verifica persistenza
            assertTrue(dbFile.exists(), "Il file database deve essere creato dopo l'aggiunta");
        }

        @Test
        @DisplayName("add: non aggiunge duplicati (stesso ID)")
        void testAddDuplicate() {
            userManager.add(user1);
            
            // Utente con stesso ID ma dati diversi (dipende da equals/hashCode di User)
            User duplicate = new User("Fake", "Clone", "001", "fake@email.it");
            
            assertFalse(userManager.add(duplicate), "Non deve aggiungere utenti con stessa matricola");
            assertEquals(1, userManager.getList().size());
        }

        @Test
        @DisplayName("add: lancia eccezione con null")
        void testAddNull() {
            assertThrows(IllegalArgumentException.class, () -> userManager.add(null));
        }

        @Test
        @DisplayName("remove: rimuove utente esistente")
        void testRemoveUser() {
            userManager.add(user1);
            userManager.add(user2);

            assertTrue(userManager.remove(user1));
            assertEquals(1, userManager.getList().size());
            assertFalse(userManager.getList().contains(user1));
            assertTrue(userManager.getList().contains(user2));
        }

        @Test
        @DisplayName("remove: restituisce false se utente non esiste")
        void testRemoveNonExisting() {
            userManager.add(user1);
            assertFalse(userManager.remove(user2));
            assertEquals(1, userManager.getList().size());
        }
        
        @Test
        @DisplayName("remove: lancia eccezione con null")
        void testRemoveNull() {
            assertThrows(IllegalArgumentException.class, () -> userManager.remove(null));
        }
    }

    @Nested
    @DisplayName("Test Aggiornamento (Update)")
    class UpdateTests {

        @Test
        @DisplayName("update: aggiorna dati utente esistente")
        void testUpdateUser() {
            userManager.add(user1); // Matricola 001

            // Nuovo oggetto con dati aggiornati (stessa matricola, o diversa se consentito)
            // Nel tuo codice update fa remove(u1) e add(u2).
            User updatedInfo = new User("Mario", "Bianchi", "001", "mario.bianchi@newemail.it");

            assertTrue(userManager.update(user1, updatedInfo));

            // Verifica che nel set ci sia l'utente aggiornato
            // Nota: poichè è un Set, prendiamo l'elemento (ce n'è uno solo)
            User stored = userManager.getList().iterator().next();
            
            assertAll("Verifica campi aggiornati",
                () -> assertEquals("Bianchi", stored.getSurname()),
                () -> assertEquals("mario.bianchi@newemail.it", stored.getEmail())
            );
        }

        @Test
        @DisplayName("update: fallisce se utente originale non esiste")
        void testUpdateNonExisting() {
            userManager.add(user1);
            assertFalse(userManager.update(user2, user1));
        }

        @Test
        @DisplayName("update: lancia eccezione con parametri null")
        void testUpdateNull() {
            assertThrows(IllegalArgumentException.class, () -> userManager.update(null, user1));
            assertThrows(IllegalArgumentException.class, () -> userManager.update(user1, null));
        }
    }

    @Nested
    @DisplayName("Test Ricerca (Search)")
    class SearchTests {

        @BeforeEach
        void initData() {
            if(!userManager.getList().isEmpty()) userManager.getList().clear();
            userManager.add(user1); // Rossi, 001
            userManager.add(user2); // Verdi, 002
        }

        @Test
        @DisplayName("search: trova per Matricola")
        void testSearchByMatricola() {
            // Costruttore ricerca User(String input) -> Se numeri, va in numberId
            User filter = new User("001");
            
            List<User> results = userManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(user1, results.get(0));
        }

        @Test
        @DisplayName("search: trova per Cognome")
        void testSearchBySurname() {
            // Costruttore ricerca User(String input) -> Se lettere, va in surname
            User filter = new User("Verdi");
            
            List<User> results = userManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(user2, results.get(0));
        }
        
        @Test
        @DisplayName("search: trova per Cognome (case insensitive)")
        void testSearchBySurnameCaseInsensitive() {
            User filter = new User("rossi"); // minuscolo
            
            List<User> results = userManager.search(filter);
            
            assertEquals(1, results.size());
            assertEquals(user1, results.get(0));
        }

        @Test
        @DisplayName("search: nessun risultato")
        void testSearchNoMatch() {
            User filter = new User("999"); // Matricola inesistente
            assertTrue(userManager.search(filter).isEmpty());
            
            User filterName = new User("Gialli"); // Cognome inesistente
            assertTrue(userManager.search(filterName).isEmpty());
        }

        @Test
        @DisplayName("search: lancia eccezione con null")
        void testSearchNull() {
            assertThrows(IllegalArgumentException.class, () -> userManager.search(null));
        }
    }
}