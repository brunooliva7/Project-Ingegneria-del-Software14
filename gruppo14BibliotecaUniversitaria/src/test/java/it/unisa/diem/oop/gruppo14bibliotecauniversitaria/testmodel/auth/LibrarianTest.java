package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.auth;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth.Librarian;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class LibrarianTest
 * @brief Suite di test per la classe Librarian.
 * * Verifica la gestione delle credenziali (lettura, scrittura, hashing)
 * e la gestione dei casi limite (input null).
 */
class LibrarianTest {

    private Librarian librarian;
    private File realFile;
    private File backupFile;

    // Percorso hardcoded nella classe Librarian
    private final String FILE_PATH = "src/main/resources/credentials.txt";

    @BeforeEach
    void setUp() throws IOException {
        librarian = new Librarian();
        
        realFile = new File(FILE_PATH);
        backupFile = new File(FILE_PATH + ".bak");

        // Assicuriamoci che la cartella esista
        if (realFile.getParentFile() != null) {
            realFile.getParentFile().mkdirs();
        }

        // 1. SALVAGUARDIA: Se esiste un file reale, facciamo un backup
        if (realFile.exists()) {
            Files.move(realFile.toPath(), backupFile.toPath());
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        // 1. PULIZIA: Eliminiamo il file creato dai test
        if (realFile.exists()) {
            realFile.delete();
        }

        // 2. RIPRISTINO: Se c'era un backup, lo rimettiamo al suo posto
        if (backupFile.exists()) {
            Files.move(backupFile.toPath(), realFile.toPath());
        }
    }

    @Test
    @DisplayName("Flusso completo: Modifica credenziali e verifica accesso con successo")
    void testModifyAndCheckSuccess() {
        String testUser = "adminTest";
        String testPass = "passwordSegreta123";

        // 1. Scriviamo le credenziali
        librarian.modifyCredentials(testUser, testPass);

        // 2. Verifichiamo che il file esista
        assertTrue(realFile.exists(), "Il file delle credenziali deve essere creato");

        // 3. Verifichiamo che l'accesso funzioni
        boolean result = librarian.checkCredentials(testUser, testPass);
        assertTrue(result, "Le credenziali dovrebbero essere valide");
    }

    @Test
    @DisplayName("Verifica accesso fallito: Password errata")
    void testCheckCredentialsWrongPassword() {
        // Setup credenziali
        librarian.modifyCredentials("user", "passwordGiusta");

        // Tentativo con password errata
        boolean result = librarian.checkCredentials("user", "passwordSbagliata");
        
        assertFalse(result, "L'accesso deve essere negato con password errata");
    }

    @Test
    @DisplayName("Verifica accesso fallito: Username errato")
    void testCheckCredentialsWrongUsername() {
        // Setup credenziali
        librarian.modifyCredentials("userGiusto", "pass");

        // Tentativo con username errato
        boolean result = librarian.checkCredentials("userSbagliato", "pass");
        
        assertFalse(result, "L'accesso deve essere negato con username errato");
    }

    @Test
    @DisplayName("Sicurezza: La password non deve essere salvata in chiaro")
    void testPasswordHashedOnDisk() throws IOException {
        String plainPassword = "miaPasswordInChiaro";
        librarian.modifyCredentials("testUser", plainPassword);

        // Leggiamo il file fisicamente
        List<String> lines = Files.readAllLines(realFile.toPath());
        
        boolean foundPlainPassword = false;
        boolean foundHashedPassword = false;

        for (String line : lines) {
            if (line.contains(plainPassword)) {
                foundPlainPassword = true;
            }
            // Controllo se c'è una stringa lunga (l'hash SHA-256 è 64 caratteri hex)
            // La riga è tipo "password : <hash>"
            if (line.startsWith("password") && line.length() > 60) {
                foundHashedPassword = true;
            }
        }

        assertFalse(foundPlainPassword, "La password in chiaro NON deve apparire nel file");
        assertTrue(foundHashedPassword, "Deve essere presente un hash nel file");
    }

    @Test
    @DisplayName("Input Null: modifyCredentials lancia eccezione")
    void testModifyCredentialsNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            librarian.modifyCredentials(null, "pass"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            librarian.modifyCredentials("user", null));
    }

    @Test
    @DisplayName("Input Null: checkCredentials lancia eccezione")
    void testCheckCredentialsNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            librarian.checkCredentials(null, "pass"));
        
        assertThrows(IllegalArgumentException.class, () -> 
            librarian.checkCredentials("user", null));
    }

    @Test
    @DisplayName("File Mancante: checkCredentials restituisce false (gestione eccezione)")
    void testCheckCredentialsMissingFile() {
        // Assicuriamoci che il file non esista
        if(realFile.exists()) realFile.delete();

        // Se il file non c'è, il metodo cattura FileNotFoundException e ritorna false
        boolean result = librarian.checkCredentials("admin", "admin");
        
        assertFalse(result, "Senza file credenziali, l'accesso deve fallire");
    }
}