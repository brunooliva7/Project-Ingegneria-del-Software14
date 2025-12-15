/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * @file UserTest.java
 *
 * @author maramariano
 * @date 12-12-2025
 */


package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.testmodel.data;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * @class UserTest
 * @brief Test di unità per la classe User
 * 
 * Questa classe contiene i test unitari per verificare la corretta implementazione
 * dei costruttori, dei metodi di uguaglianza e hash (basati su numberId) e della
 * logica di ordinamento (basata su cognome e nome)
 */
public class UserTest {

    private User userMario; ///< Utente di test 1
    private User userLuigi; ///< Utente di test 2
    private User userMarioDuplicateId; ///< Utente con stesso id di Utente 1

    /**
     * @brief Imposta l'ambiente di test (fixture) prima di ogni test.
     *
     * Inizializza gli oggetti User standard per i confronti.
     */
    @BeforeEach
    void setUp() {
        userMario = new User("Mario", "Rossi", "0612700000", "mario.rossi@unisa.it");
        userLuigi = new User("Luigi", "Bianchi", "0612708900", "luigi.bianchi@unisa.it");
        // Stessa Matricola (ID) di userMario
        userMarioDuplicateId = new User("Mario Junior", "Verdi", "0612700000", "mj.verdi@unisa.it");
    }


    /**
     * @brief Testa il costruttore principale.
     *
     * Verifica l'inizializzazione corretta di tutti i campi.
     */
    @Test
    void testMainConstructor() {
        assertNotNull(userMario);
        assertEquals("Mario", userMario.getName(), "Il nome deve essere 'Mario'.");
        assertEquals("Rossi", userMario.getSurname(), "Il cognome deve essere 'Rossi'.");
        assertEquals("0612700000", userMario.getNumberId(), "La matricola deve essere '0612700000'.");
        assertNotNull(userMario.getBooksOnloan(), "La mappa dei prestiti non deve essere null.");
        assertTrue(userMario.getBooksOnloan().isEmpty(), "La mappa dei prestiti deve essere inizialmente vuota.");
    }

    /**
     * @brief Testa il costruttore parziale di ricerca con input numerico (Matricola).
     *
     * Verifica che solo il campo numberId venga popolato.
     */
    @Test
    void testSearchConstructor_NumberId() {
        String idInput = "0612718900"; // Input numerico
        User searchUser = new User(idInput);

        assertNotNull(searchUser, "L'utente di ricerca non deve essere null.");
        assertEquals(idInput, searchUser.getNumberId(), "La matricola deve corrispondere all'input numerico.");
        assertNull(searchUser.getSurname(), "Gli altri campi (cognome) devono essere null.");
        assertNull(searchUser.getName(), "Gli altri campi (nome) devono essere null.");
    }

    /**
     * @brief Testa il costruttore parziale di ricerca con input testuale (Cognome).
     *
     * Verifica che solo il campo surname venga popolato, risolvendo l'errore del test Loan.
     */
    @Test
    void testSearchConstructor_Surname() {
        String surnameInput = "Rossi"; // Input testuale
        User searchUser = new User(surnameInput);

        assertNotNull(searchUser, "L'utente di ricerca non deve essere null.");
        assertEquals(surnameInput, searchUser.getSurname(), "Il cognome deve corrispondere all'input testuale.");
        assertNull(searchUser.getNumberId(), "Gli altri campi (Matricola) devono essere null.");
        assertNull(searchUser.getName(), "Gli altri campi (nome) devono essere null.");
    }

    /**
     * @brief Testa il costruttore parziale con input null o vuoto.
     *
     * Verifica che l'oggetto sia creato ma tutti i campi restino null.
     */
    @Test
    void testSearchConstructor_NullOrEmpty() {
        User searchNull = new User(null);
        assertNull(searchNull.getNumberId(), "numberId deve essere null se l'input è null.");
        assertNull(searchNull.getSurname(), "Surname deve essere null se l'input è null.");
        
        User searchEmpty = new User(" ");
        assertNull(searchEmpty.getNumberId(), "numberId deve essere null se l'input è vuoto.");
        assertNull(searchEmpty.getSurname(), "Surname deve essere null se l'input è vuoto.");
    }


    /**
     * @brief Testa l'uguaglianza e l'hashCode basati su numberId.
     *
     * User con stessa Matricola ma nome/cognome diversi devono essere considerati uguali.
     */
    @Test
    void testEqualsAndHashCode_SameNumberId() {
        // equals()
        assertTrue(userMario.equals(userMarioDuplicateId), "Utenti con la stessa matricola devono essere uguali.");
        assertFalse(userMario.equals(userLuigi), "Utenti con matricola diversa devono essere diversi.");
        
        // hashCode()
        assertEquals(userMario.hashCode(), userMarioDuplicateId.hashCode(), "L'hashCode deve essere uguale per utenti uguali.");
        assertNotEquals(userMario.hashCode(), userLuigi.hashCode(), "L'hashCode deve essere diverso per utenti diversi.");
    }
    
    /**
     * @brief Testa equals con oggetti non User e con riferimenti null.
     */
    @Test
    void testEquals_BoundaryCases() {
        assertFalse(userMario.equals(null), "Non deve essere uguale a null.");
        assertFalse(userMario.equals(new Object()), "Non deve essere uguale a un oggetto di classe diversa.");
    }


    /**
     * @brief Testa l'ordinamento basato su Cognome, poi Nome.
     */
    @Test
    void testCompareTo_SurnamePriority() {
        // userLuigi (Bianchi) vs userMario (Rossi) -> Bianchi deve venire prima
        assertTrue(userLuigi.compareTo(userMario) < 0, "Bianchi dovrebbe precedere Rossi per cognome.");
        
        // userMario (Rossi) vs userLuigi (Bianchi) -> Rossi deve venire dopo
        assertTrue(userMario.compareTo(userLuigi) > 0, "Rossi dovrebbe seguire Bianchi per cognome.");
    }
    
    /**
     * @brief Testa l'ordinamento quando i cognomi sono uguali.
     *
     * L'ordinamento deve cadere sul nome.
     */
    @Test
    void testCompareTo_SameSurname_SortByName() {
        User userGiovanni = new User("Giovanni", "Rossi", "G00001", "g.rossi@unisa.it");
        
        // userGiovanni (Giovanni Rossi) vs userMario (Mario Rossi) -> Giovanni viene prima di Mario
        assertTrue(userGiovanni.compareTo(userMario) < 0, "Quando il cognome è uguale, Giovanni dovrebbe precedere Mario per nome.");
        assertTrue(userMario.compareTo(userGiovanni) > 0, "Quando il cognome è uguale, Mario dovrebbe seguire Giovanni per nome.");
        
        // Stesso cognome e stesso nome (dovrebbe essere 0)
        User userMario2 = new User("Mario", "Rossi", "A99999", "altro@unisa.it"); // ID diverso non conta
        assertEquals(0, userMario.compareTo(userMario2), "Utenti con stesso nome e cognome devono essere 0.");
    }


    /**
     * @brief Testa l'aggiunta di un prestito con findLoans().
     */
    @Test
    void testFindLoans_AddsToMap() {
        Book testBook = new Book("Test Book", "Author", "2023", "TEST-123", 1);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        
        userMario.findLoans(testBook, tomorrow);
        
        assertFalse(userMario.getBooksOnloan().isEmpty(), "La mappa dei prestiti non deve essere vuota.");
        assertTrue(userMario.getBooksOnloan().containsKey(testBook), "Il libro deve essere presente nella mappa.");
        assertEquals(tomorrow, userMario.getBooksOnloan().get(testBook), "La data di scadenza non è corretta.");
    }
    
    /**
     * @brief Testa la sovrascrittura in caso di prestito dello stesso libro.
     */
    @Test
    void testFindLoans_OverwriteSameBook() {
        Book testBook = new Book("Test Book", "Author", "2023", "TEST-123", 1);
        LocalDate originalDate = LocalDate.now().plusDays(1);
        LocalDate newDate = LocalDate.now().plusDays(30);

        userMario.findLoans(testBook, originalDate);
        userMario.findLoans(testBook, newDate); // Sovrascrive

        assertEquals(1, userMario.getBooksOnloan().size(), "La mappa deve contenere solo una voce per lo stesso libro.");
        assertEquals(newDate, userMario.getBooksOnloan().get(testBook), "La data di scadenza deve essere aggiornata.");
    }
    

    
    /**
     * @brief Testa i setter e getter di base.
     */
    @Test
    void testSettersAndGetters() {
        User u = new User("Old", "Name", "ID000", "old@mail.it");
        
        u.setName("New");
        u.setSurname("Surname");
        u.setEmail("new@mail.it");
        u.setNumberId("ID999");
        
        assertEquals("New", u.getName());
        assertEquals("Surname", u.getSurname());
        assertEquals("new@mail.it", u.getEmail());
        assertEquals("ID999", u.getNumberId());
        
        // Test setBooksOnLoan
        TreeMap<Book, LocalDate> newMap = new TreeMap<>();
        newMap.put(new Book("Test", "A", "2000", "X", 1), LocalDate.now());
        u.setBooksOnLoan(newMap);
        assertEquals(newMap, u.getBooksOnloan());
    }

}