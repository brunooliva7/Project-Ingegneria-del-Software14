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
    private final BookManagement bookManagement;   ///< Gestore dei libri
    private final UserManagement userManagement;   ///< Gestore degli utenti
    private final LoanManagement loanManagement;   ///< Gestore dei prestiti
   
    /**
     * @brief Costruttore della classe Model
     *
     * Inizializza gli attributi della classe 
     *
     * @pre bookManagement != null && userManagement != null && loanManagement != null && librarian != null
     * @post Oggetto pronto all'uso
     */
    public Model(BookManagement bookManagement, UserManagement userManagement, LoanManagement loanManagement) {
        this.bookManagement =  bookManagement;
        this.userManagement =  userManagement;
        this.loanManagement = loanManagement;
        
    }

    //Getter e Setter

  /*  public Model(UserManagement userManagement, BookManagement bookManagement, LoanManagement loanManagement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
   
    

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
     * @brief Restituisce il gestore dei prestiti
     * @return Oggetto LoanManagement
     * 
     * @pre  Il modello deve essere stato inizializzato
     * @post Restituisce il riferimento al gestore dei prestiti
     */
    public LoanManagement getLoanManagement() {
        return loanManagement;
    }

  

    
}