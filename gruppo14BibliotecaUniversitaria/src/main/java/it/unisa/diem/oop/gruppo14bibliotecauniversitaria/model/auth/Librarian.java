/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth;

/**
 * @class Librarian
 * @brief Rappresenta il bibliotecario del sistema.2
 *
 * La classe Librarian gestisce le credenziali di accesso del bibliotecario,
 * permettendo di verificarle e modificarle. Le credenziali sono salvate
 * in un file esterno.
 */

public class Librarian {
    
    private String username; ///< nome utente del bibliotecario
    private String password;///< password del bibliotecario
    private final String filepath = "credentials.txt"; ///< file esterno

        /**
     * @brief Verifica le credenziali inserite.
     * @param usernameInput Nome utente da verificare 
     * @param passwordInput Password da verificare 
     * @return true se le credenziali corrispondono, false altrimenti
     *
     * la funzione legge dal file di testo esterno le credenziali, decodifica la password e le confronta con quelle date in input
     * 
     * @pre usernameInput != null, passwordInput != null
     * @post Restituisce true se le credenziali sono valide, false altrimenti
     */

    public boolean checkCredentials(String usernameInput, String passwordInput){   
    }
    
    /**
     * @brief Modifica le credenziali del bibliotecario.
     * @param newUsername Nuovo nome utente 
     * @param newPassword Nuova password 
     *
     * @pre newUsername != null, newPassword != null
     * @post Le credenziali del bibliotecario sono aggiornate e salvate su file
     */

    public void modifyCredentials(String newUsername, String newPassword){
    }
    
    
    /**
     * @brief Restituisce il nome utente del bibliotecario.
     * @return Stringa contenente il nome utente
     *
     * @pre L'oggetto Librarian deve essere stato inizializzato
     * @post Restituisce il valore corrente di username
     */

    public String getUsername() {
        return Username;
    }
    
    /**
     * @brief Restituisce il nome utente del bibliotecario.
     * @return Stringa contenente il nome utente
     *
     * @pre L'oggetto Librarian deve essere stato inizializzato
     * @post Restituisce il valore corrente di username
     */

    public String getPassword() {
        return Password;
    }   
}

