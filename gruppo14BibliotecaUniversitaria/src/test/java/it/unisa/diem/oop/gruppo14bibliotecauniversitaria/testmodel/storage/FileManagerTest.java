/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.storage;

/**
 * 
 *@file FileManagerTest.java
 *
 * @author maramariano
 * @date 12-12-2025
 * @version 1.0
 */

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @class FileManagerTest
 * @brief Classe di test per la classe FileManager.
 */
class FileManagerTest {

    // JUnit Jupiter fornisce una directory temporanea pulita
    @TempDir
    File tempDir; 
    
    private File tempObjectFile;
    private File tempTextFile;
    
    // Classe serializzabile e comparabile per i test generici
    // Deve essere statica per essere creata senza un'istanza di FileManagerTest
    static class TestData implements Serializable, Comparable<TestData> {
        private static final long serialVersionUID = 1L;
        private String value;
        private int id;

        public TestData(String value, int id) {
            this.value = value;
            this.id = id;
        }

        @Override
        public String toString() {
            return value + ":" + id;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestData testData = (TestData) o;
            return id == testData.id;
        }
        
        // Necessario per la TreeSet
        @Override
        public int compareTo(TestData other) {
            return Integer.compare(this.id, other.id);
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        // Crea file temporanei per ogni esecuzione di test
        tempObjectFile = new File(tempDir, "test_objects.dat");
        tempTextFile = new File(tempDir, "test_text.txt");
    }

    // --- TEST METODI SERIALIZZAZIONE (OGGETTI) ---

    /**
     * @brief Test di writeToTextFileObject (Scrittura di un singolo oggetto serializzabile).
     */
    @Test
    void testWriteToTextFileObject_Success() throws IOException, ClassNotFoundException {
        TestData originalData = new TestData("TestObject", 100);
        
        // 1. Scrivi l'oggetto
        FileManager.writeToTextFileObject(originalData, tempObjectFile);

        // 2. Verifica che il file esista e non sia vuoto
        assertTrue(tempObjectFile.exists());
        assertTrue(tempObjectFile.length() > 0);

        // 3. Leggi l'oggetto per verificare il contenuto
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(tempObjectFile))) {
            TestData readData = (TestData) read.readObject();
            assertEquals(originalData.id, readData.id);
            assertEquals(originalData.value, readData.value);
        }
    }

    /**
     * @brief Test di writeToTextFileObject con input non valido.
     */
    @Test
    void testWriteToTextFileObject_InvalidInput() {
        // Oggetto null
        assertThrows(IllegalArgumentException.class, () -> {
            FileManager.writeToTextFileObject(null, tempObjectFile);
        }, "Dovrebbe lanciare IllegalArgumentException se l'oggetto è null.");

        // File null
        assertThrows(IllegalArgumentException.class, () -> {
            FileManager.writeToTextFileObject(new TestData("data", 1), null);
        }, "Dovrebbe lanciare IllegalArgumentException se il file è null.");
    }
    
    /**
     * @brief Test di updateFileObject (Sovrascrittura di un Set di oggetti).
     */
    @Test
    void testUpdateFileObject_Success() throws IOException, ClassNotFoundException {
        // 1. Prepara il Set da scrivere
        Set<TestData> originalSet = new TreeSet<>();
        originalSet.add(new TestData("First", 1));
        originalSet.add(new TestData("Second", 2));

        // 2. Scrivi il Set
        FileManager.updateFileObject(originalSet, tempObjectFile);
        assertEquals(2, originalSet.size(), "Il set iniziale ha 2 elementi.");

        // 3. Verifica leggendo il contenuto
        Set<TestData> readSet;
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(tempObjectFile))) {
            readSet = (Set<TestData>) read.readObject();
        }
        
        assertEquals(originalSet.size(), readSet.size(), "Il set letto deve avere la stessa dimensione.");
        assertTrue(readSet.contains(new TestData("AnyValue", 1)), "Il set deve contenere l'oggetto con ID 1.");


        // 4. Prepara un nuovo Set (con meno elementi) e sovrascrivi
        Set<TestData> newSet = new TreeSet<>();
        newSet.add(new TestData("Third", 3)); // Un solo elemento

        FileManager.updateFileObject(newSet, tempObjectFile);

        // 5. Verifica leggendo il contenuto aggiornato
        Set<TestData> updatedReadSet;
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(tempObjectFile))) {
            updatedReadSet = (Set<TestData>) read.readObject();
        }

        assertEquals(1, updatedReadSet.size(), "Dopo l'update, il file deve contenere 1 elemento.");
        assertTrue(updatedReadSet.contains(new TestData("NewValue", 3)), "Il file deve contenere solo l'oggetto con ID 3.");
        assertFalse(updatedReadSet.contains(new TestData("OldValue", 1)), "Il vecchio oggetto con ID 1 non deve più esistere.");
    }
    
    /**
     * @brief Test di updateFileObject con input non valido.
     */
    @Test
    void testUpdateFileObject_InvalidInput() {
        // File null
        Set<TestData> testSet = new TreeSet<>();
        testSet.add(new TestData("data", 1));

        assertThrows(IllegalArgumentException.class, () -> {
            FileManager.updateFileObject(testSet, null);
        }, "Dovrebbe lanciare IllegalArgumentException se il file è null.");
    
        // Nota: la tua implementazione non gestisce esplicitamente newTree=null, 
        // ma una NullPointerException verrebbe lanciata internamente da TreeSet se newTree non è null.
        // Se si volesse testare newTree=null, bisognerebbe lanciare IllegalArgumentException 
        // e modificare la classe FileManager.
    }

    // --- TEST METODI TESTUALI (LINEE) ---
    
    /**
     * @brief Test di writeLine (Scrittura di una singola riga di testo).
     */
    @Test
    void testWriteLine_Success() throws IOException {
        String line = "Testing FileManager line write.";
        
        // 1. Scrivi la riga
        FileManager.writeLine(line, tempTextFile);
        
        // 2. Verifica leggendo il contenuto
        String readLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(tempTextFile))) {
            readLine = reader.readLine();
        }
        
        assertEquals(line, readLine, "La riga scritta deve corrispondere a quella letta.");
    }
    
    /**
     * @brief Test di readLine (Lettura di una singola riga di testo).
     * * NOTA: La tua implementazione di readLine non restituisce la riga letta, 
     * * ma ha solo effetti collaterali (stampa o log). Per renderla testabile, 
     * * dovremmo modificare FileManager.readLine() per restituire String.
     * * Qui verifichiamo solo che non lanci eccezioni.
     */
    @Test
    void testReadLine_NoExceptions() throws IOException {
        // Prepara il file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempTextFile))) {
            writer.write("Prima riga di testo\nSeconda riga");
        }
        
        // Esegui readLine (ci aspettiamo che non lanci eccezioni)
        // Se il test supera, il metodo è eseguito senza errori di I/O.
        assertDoesNotThrow(() -> FileManager.readLine(tempTextFile), "La lettura non dovrebbe lanciare eccezioni.");
    }
    
    /**
     * @brief Test di readLine su file inesistente.
     */
    @Test
    void testReadLine_FileNotFound() {
        File missingFile = new File(tempDir, "missing.txt");
        // Esegui readLine su file inesistente (ci aspettiamo che non lanci eccezioni, 
        // ma che logghi FileNotFoundException internamente)
        assertDoesNotThrow(() -> FileManager.readLine(missingFile), "Il metodo dovrebbe gestire FileNotFoundException internamente.");
    }
}
