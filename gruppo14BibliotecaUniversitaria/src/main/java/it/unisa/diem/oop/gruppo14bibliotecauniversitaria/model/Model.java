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

    /**
     * @brief Costruttore della classe Model
     *
     * Inizializza la classe
     *
     * @param bookManagement Oggetto BookManagement che gestisce i libri
     * @param userManagement Oggetto UserManagement che gestisce gli utenti
     * @param loanManagement Oggetto LoanManagement che gestisce i prestiti
     *
     * @pre bookManagement != null && userManagement != null && loanManagement != null
     * @post Oggetto pronto all'uso
     */
    public Model(BookManagement bookManagement, UserManagement userManagement, LoanManagement loanManagement) {
        this.bookManagement = bookManagement;
        this.userManagement = userManagement;
        this.loanManagement = loanManagement;
    }

    //Getter e Setter

    /**
     * @brief Restituisce il gestore dei libri
     * @return Oggetto BookManagement
     */
    public BookManagement getBookManagement() {
        return bookManagement;
    }

    /**
     * @brief Imposta un nuovo gestore dei libri
     * @param bookManagement Nuovo oggetto BookManagement
     *
     * @pre bookManagement != null
     * @post Il gestore dei ibri è aggiornata 
     */
    public void setBookManagement(BookManagement bookManagement) {
        this.bookManagement = bookManagement;
    }

    /**
     * @brief Restituisce il gestore degli utenti
     * @return Oggetto UserManagement
     */
    public UserManagement getUserManagement() {
        return userManagement;
    }

    /**
     * @brief Imposta un nuovo gestore degli utenti
     * @param userManagement Nuovo oggetto UserManagement
     *
     * @pre bookManagement != null
     * @post Il gestore degli utenti è aggiornato
     */
    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
    
    /**
     * @brief Restituisce il gestore dei prestiti
     * @return Oggetto LoanManagement
     */
    public LoanManagement getLoanManagement() {
        return loanManagement;
    }

    /**
     * @brief Imposta un nuovo gestore dei prestiti
     * @param loanManagement Nuovo oggetto LoanManagement
     *
     * @pre loanManagement != null
     * @post Il gestore dei prestiti è aggiornata
     */
    public void setLoanManagement(LoanManagement loanManagement) {
        this.loanManagement = loanManagement;
    }

    
}