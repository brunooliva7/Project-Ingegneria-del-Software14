/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.auth;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth.Librarian;
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
public class LibrarianTest {
    
    private Librarian librarian;

    @BeforeEach
    void setUp() {
        librarian = new Librarian();
        
        // PRIMA di ogni test, impostiamo il file su credenziali note.
        // Usiamo il metodo stesso della classe per settare uno stato pulito.
        // Username: "admin", Password: "password123"
        librarian.modifyCredentials("admin", "password123");
    }

    @AfterEach
    void tearDown() {
        // Opzionale: pulizia finale se necessaria
    }

    // --- TEST CHECK CREDENTIALS ---

    @Test
    void testCheckCredentialsSuccess() {
        // Verifica che le credenziali impostate nel setUp funzionino
        boolean result = librarian.checkCredentials("admin", "password123");
        assertTrue(result, "Il login dovrebbe avere successo con le credenziali corrette");
    }

    @Test
    void testCheckCredentialsWrongUsername() {
        // Username errato
        boolean result = librarian.checkCredentials("sbagliato", "password123");
        assertFalse(result, "Il login dovrebbe fallire con username errato");
    }

    @Test
    void testCheckCredentialsWrongPassword() {
        // Password errata
        boolean result = librarian.checkCredentials("admin", "sbagliata");
        assertFalse(result, "Il login dovrebbe fallire con password errata");
    }

    @Test
    void testCheckCredentialsCaseSensitivity() {
        // Verifica sensibilità maiuscole/minuscole (dipende dalla tua logica, di solito è sensibile)
        boolean result = librarian.checkCredentials("Admin", "password123");
        // Se il tuo sistema vuole username case-sensitive, questo deve essere False.
        // Se storedUsername.equals(usernameInput) è usato, è case-sensitive.
        assertFalse(result, "Il login dovrebbe fallire se il case dello username è diverso");
    }

    @Test
    void testCheckCredentialsNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            librarian.checkCredentials(null, "password123");
        }, "Dovrebbe lanciare eccezione se username è null");

        assertThrows(IllegalArgumentException.class, () -> {
            librarian.checkCredentials("admin", null);
        }, "Dovrebbe lanciare eccezione se password è null");
    }

    // --- TEST MODIFY CREDENTIALS ---

    @Test
    void testModifyCredentials() {
        // 1. Modifichiamo le credenziali
        String newUsr = "superUser";
        String newPwd = "newSecurePassword!";
        
        librarian.modifyCredentials(newUsr, newPwd);

        // 2. Verifichiamo che le VECCHIE non funzionino più
        boolean oldLogin = librarian.checkCredentials("admin", "password123");
        assertFalse(oldLogin, "Le vecchie credenziali non dovrebbero più funzionare");

        // 3. Verifichiamo che le NUOVE funzionino
        boolean newLogin = librarian.checkCredentials(newUsr, newPwd);
        assertTrue(newLogin, "Le nuove credenziali dovrebbero permettere il login");
    }

    @Test
    void testModifyCredentialsNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            librarian.modifyCredentials(null, "pass");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            librarian.modifyCredentials("user", null);
        });
    }

    // --- TEST HASHING (Indiretto) ---
    // Poiché hashPassword è privato, lo testiamo indirettamente vedendo se
    // modifyCredentials e checkCredentials riescono a capirsi.
    
    @Test
    void testHashingConsistency() {
        // Se l'hashing non fosse deterministico o fosse sbagliato, 
        // salvare e rileggere fallirebbe.
        librarian.modifyCredentials("testHash", "testPass");
        assertTrue(librarian.checkCredentials("testHash", "testPass"));
    }
    
    // --- NOTA SUI GETTER ---
    /*
     * Ho notato che nella tua classe i campi `private String username` e `password` 
     * non vengono MAI assegnati (checkCredentials legge dal file in variabili locali).
     * Di conseguenza, getUsername() e getPassword() restituiranno sempre null.
     * * Se è voluto, puoi lasciare così. Se è un errore, dovresti assegnare i valori
     * dentro checkCredentials quando il login ha successo.
     */
    @Test
    void testGettersAreNullByDefault() {
        // Questo test conferma il comportamento attuale del codice (che restituisce null)
        assertNull(librarian.getUsername(), "Username dovrebbe essere null se non settato esplicitamente");
        assertNull(librarian.getPassword(), "Password dovrebbe essere null se non settato esplicitamente");
    }
    
}
