package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.storage;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class FileManagerTest
 * @brief Suite di test per la classe FileManager.
 * * Verifica le operazioni di I/O su file per oggetti serializzati e file di testo.
 */
class FileManagerTest {

    private File tempObjFile;
    private File tempTextFile;

    @BeforeEach
    void setUp() {
        // Creiamo nomi di file temporanei univoci per evitare conflitti
        tempObjFile = new File("test_objects_" + System.currentTimeMillis() + ".dat");
        tempTextFile = new File("test_text_" + System.currentTimeMillis() + ".txt");
    }

    @AfterEach
    void tearDown() {
        // Pulizia: cancelliamo i file creati durante i test
        if (tempObjFile.exists()) tempObjFile.delete();
        if (tempTextFile.exists()) tempTextFile.delete();
    }

    @Nested
    @DisplayName("Test Gestione Oggetti Serializzabili")
    class ObjectPersistenceTests {

        @Test
        @DisplayName("writeToTextFileObject: scrive correttamente un Set di stringhe")
        void testWriteObject() {
            Set<String> testSet = new HashSet<>();
            testSet.add("Test1");
            testSet.add("Test2");

            FileManager.writeToTextFileObject(testSet, tempObjFile);

            assertTrue(tempObjFile.exists(), "Il file deve essere creato");
            assertTrue(tempObjFile.length() > 0, "Il file non deve essere vuoto");

            // Verifica leggendo manualmente ciò che è stato scritto
            Set<String> readSet = readObjectFromFile(tempObjFile);
            assertNotNull(readSet);
            assertEquals(2, readSet.size());
            assertTrue(readSet.contains("Test1"));
            assertTrue(readSet.contains("Test2"));
        }

        @Test
        @DisplayName("updateFileObject: sovrascrive correttamente il file")
        void testUpdateObject() {
            // 1. Scriviamo un primo set
            Set<String> initialSet = new HashSet<>();
            initialSet.add("OldValue");
            FileManager.writeToTextFileObject(initialSet, tempObjFile);

            // 2. Creiamo un nuovo set aggiornato
            Set<String> newSet = new HashSet<>();
            newSet.add("NewValue1");
            newSet.add("NewValue2");
            newSet.add("NewValue3");

            // 3. Eseguiamo update
            FileManager.updateFileObject(newSet, tempObjFile);

            // 4. Verifichiamo che leggendo otteniamo solo i nuovi valori
            Set<String> readSet = readObjectFromFile(tempObjFile);
            
            assertNotNull(readSet);
            assertEquals(3, readSet.size(), "La dimensione deve riflettere il nuovo set");
            assertTrue(readSet.contains("NewValue1"));
            assertFalse(readSet.contains("OldValue"), "I vecchi valori non devono esserci");
        }

        @Test
        @DisplayName("writeToTextFileObject: lancia eccezione con parametri null")
        void testWriteObjectNull() {
            Set<String> set = new HashSet<>();
            assertThrows(IllegalArgumentException.class, () -> FileManager.writeToTextFileObject(null, tempObjFile));
            assertThrows(IllegalArgumentException.class, () -> FileManager.writeToTextFileObject(set, null));
        }

        // Helper method per leggere e verificare il contenuto scritto da FileManager
        @SuppressWarnings("unchecked")
        private Set<String> readObjectFromFile(File file) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Set<String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Nested
    @DisplayName("Test Gestione File di Testo")
    class TextFileTests {

        @Test
        @DisplayName("writeLine: scrive una riga su file")
        void testWriteLine() {
            String content = "Hello World";
            FileManager.writeLine(content, tempTextFile);

            assertTrue(tempTextFile.exists());
            
            // Verifichiamo il contenuto leggendo manualmente
            String readContent = readStringFromFile(tempTextFile);
            assertEquals(content, readContent);
        }

        @Test
        @DisplayName("readLine: non lancia eccezioni (verifica base)")
        void testReadLine() {
            // Il metodo readLine nel tuo codice originale stampa o legge una variabile locale 'line' 
            // ma NON restituisce nulla. Quindi possiamo solo testare che non crashi.
            
            // Creiamo un file con contenuto
            FileManager.writeLine("Test Line", tempTextFile);
            
            // Eseguiamo readLine (attualmente void)
            assertDoesNotThrow(() -> FileManager.readLine(tempTextFile));
        }

        // Helper method per leggere stringhe da file
        private String readStringFromFile(File file) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}