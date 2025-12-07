/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file Model.java
 * 
 * @author maramariano
 * @date 04-12-2025
 * @version 1.0
 *  
 */


package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth.Librarian;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management.*;


/**
 * @class Model
 * @brief Gestisce i vari manager del sistema biblioteca
 *
 * La classe Model funge da punto di accesso ai dati e coordina le operazioni tra BookManagement, UserManagement e LoanManagement
 */
public class Model {
    private BookManagement bookManagement;   ///< Gestore dei libri
    private UserManagement userManagement;   ///< Gestore degli utenti
    private LoanManagement loanManagement;   ///< Gestore dei prestiti
    private Librarian librarian;             ///< Bibliotecario                                                                         

    /**
     * @brief Costruttore della classe Model
     *
     * Inizializza la classe
     *
     * @param bookManagement Oggetto BookManagement che gestisce i libri
     * @param userManagement Oggetto UserManagement che gestisce gli utenti
     * @param loanManagement Oggetto LoanManagement che gestisce i prestiti
     * @param librarian Oggetto Librarian responsabile del sistema
     *
     * @pre bookManagement != null && userManagement != null && loanManagement != null && librarian != null
     * @post Oggetto pronto all'uso
     */
    public Model(BookManagement bookManagement, UserManagement userManagement, LoanManagement loanManagement, Librarian librarian) {
        this.bookManagement = bookManagement;
        this.userManagement = userManagement;
        this.loanManagement = loanManagement;
        this.librarian = librarian;
    }

    //Getter e Setter
    
    /**
     * @brief Restituisce il bibliotecario responsabile del sistema
     * @return Oggetto Librarian
     *
     * @pre Il bibliotecario deve essere stato inizializzato
     * @post Restituisce il riferimento al bibliotecario
     */
    public Librarian getLibrarian() {
        return librarian;
    }

    /**
     * @brief Imposta un nuovo bibliotecario responsabile del sistema
     * @param librarian Nuovo oggetto Librarian
     *
     * @pre librarian != null
     * @post Il bibliotecario è aggiornato con il nuovo valore
     */
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    /**
     * @brief Restituisce il gestore dei libri
     * @return Oggetto BookManagement
     * 
     * @pre  Il modello deve essere stato inizializzato
     * @post Restituisce il riferimento al gestore dei libri
     */
    public BookManagement getBookManagement() {
        return bookManagement;
    }

    /**
     * @brief Imposta un nuovo gestore dei libri
     * @param bookManagement Nuovo oggetto BookManagement
     *
     * @pre bookManagement != null
     * @post Il gestore dei libri è aggiornato
     */
    public void setBookManagement(BookManagement bookManagement) {
        this.bookManagement = bookManagement;
    }

    /**
     * @brief Restituisce il gestore degli utenti
     * @return Oggetto UserManagement
     * 
     * @pre  Il modello deve essere stato inizializzato
     * @post Restituisce il riferimento al gestore degli utenti
     */
    public UserManagement getUserManagement() {
        return userManagement;
    }

    /**
     * @brief Imposta un nuovo gestore degli utenti
     * @param userManagement Nuovo oggetto UserManagement
     *
     * @pre userManagement != null
     * @post Il gestore degli utenti è aggiornato
     */
    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
    
    /**
     * @brief Restituisce il gestore dei prestiti
     * @return Oggetto LoanManagement
     * 
     * @pre  Il modello deve essere stato inizializzato
     * @post Restituisce il riferimento al gestore dei prestiti
     */
    public LoanManagement getLoanManagement() {
        return loanManagement;
    }

    /**
     * @brief Imposta un nuovo gestore dei prestiti
     * @param loanManagement Nuovo oggetto LoanManagement
     *
     * @pre loanManagement != null
     * @post Il gestore dei prestiti è aggiornato
     */
    public void setLoanManagement(LoanManagement loanManagement) {
        this.loanManagement = loanManagement;
    }

    
}