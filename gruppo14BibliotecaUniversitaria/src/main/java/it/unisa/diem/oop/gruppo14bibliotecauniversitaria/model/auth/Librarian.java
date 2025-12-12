/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file Librarian.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.auth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class Librarian
 * @brief Rappresenta il bibliotecario del sistema
 *
 * La classe Librarian gestisce le credenziali di accesso del bibliotecario,
 * permettendo di verificarle e modificarle. Le credenziali sono salvate
 * in un file esterno.
 */

public class Librarian {
    
    private String username; ///< nome utente del bibliotecario
    private String password;///< password del bibliotecario
    
    URL resourceUrl = getClass().getClassLoader().getResource("credentials.txt");
   private final File credentials = new File(resourceUrl.getFile());///< file esterno

    /**
     * @brief Verifica le credenziali inserite.
     * @param usernameInput Nome utente da verificare 
     * @param passwordInput Password da verificare 
     * @return true se le credenziali corrispondono, false altrimenti
     *
     * La funzione legge dal file di testo esterno le credenziali, decodifica la password e le confronta con quelle date in input
     * 
     * @pre usernameInput != null, passwordInput != null
     * @post Restituisce true se le credenziali sono valide, false altrimenti
     */

    public boolean checkCredentials(String usernameInput, String passwordInput){   
        
      if (usernameInput == null || passwordInput == null) {
        throw new IllegalArgumentException("Username e Password non possono essere null.");
    }

    String storedUsername = null;
    String storedPasswordHash = null;

    try (Scanner fileScanner = new Scanner(credentials);){
        
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(":");

            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();

                if (key.equalsIgnoreCase("username")) {
                    storedUsername = value;
                } else if (key.equalsIgnoreCase("password")) {
                    storedPasswordHash = value; // qui è già salvato l'hash
                }
            }
        }
        fileScanner.close();
         if (storedUsername != null && storedPasswordHash != null) {
            boolean userMatch = storedUsername.equals(usernameInput);
            boolean passMatch = storedPasswordHash.equals(hashPassword(passwordInput));

            return userMatch && passMatch;
        }

    } catch (FileNotFoundException ex) {
        Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }

    return false;
 }

 

     /**
        * @brief Calcola l'hash SHA-256 di una password.
        *
        * Questo metodo utilizza la classe MessageDigest per generare un digest della password fornita. L'hash risultante
        * è una stringa esadecimale di lunghezza fissa (64 caratteri) che rappresenta
        * la password in forma non reversibile.
        *
        * @param password La password in chiaro da trasformare in hash (non deve essere null).
        * @return Una stringa esadecimale che rappresenta l'hash SHA-256 della password.
        *
        * @throws RuntimeException Se l'algoritmo SHA-256 non è disponibile
         *                          nell'ambiente di esecuzione.
         *
        * @pre Il parametro `password` deve essere non null.
        *
        * 
        * @post Viene restituita una stringa esadecimale di 64 caratteri.
        * @post La stessa password produce sempre lo stesso hash 
        * 
        */

    private String hashPassword(String password) {
        try {
        // Ottieni un'istanza di MessageDigest configurata con l'algoritmo SHA-256
        //    Questo oggetto serve per applicare la funzione di hashing.
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); //classe di java.security che impelemnta algoritmo di ahshing
        
        // Converte la password in un array di byte 
        //    e calcola l'hash con digest().
        byte[] hashBytes = digest.digest(password.getBytes());

        // Costruisce una stringa esadecimale leggibile a partire dai byte dell'hash.
        //    Ogni byte viene trasformato in due caratteri esadecimali (00–ff).
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        // Restituisce la stringa esadecimale finale che rappresenta l'hash della password.
        return sb.toString();

    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Algoritmo di hashing non disponibile", e);
    }
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
            
        if (newUsername == null || newPassword == null) {
            throw new IllegalArgumentException();
        }

        String encryptedPassword = hashPassword(newPassword);

        // Usiamo FileWriter con 'false' per indicare che vogliamo SOVRASCRIVERE il file, non aggiungere in coda.
        try (PrintWriter writer = new PrintWriter(new FileWriter(credentials, false))) {
            
            // Scriviamo rispettando il formato che il tuo scanner si aspetta ("chiave : valore")
            writer.println("username : " + newUsername);
            writer.println("password : " + encryptedPassword);
            
            System.out.println("Credenziali aggiornate con successo.");

       
    }   catch (IOException ex) {
            Logger.getLogger(Librarian.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }       
    
    /**
     * @brief Restituisce il nome utente del bibliotecario.
     * @return Stringa contenente il nome utente
     *
     * @pre L'oggetto Librarian deve essere stato inizializzato
     * @post Restituisce il valore corrente di username
     */

    public String getUsername() {
        return username;
    }
    
    /**
     * @brief Restituisce il nome utente del bibliotecario.
     * @return Stringa contenente il nome utente
     *
     * @pre L'oggetto Librarian deve essere stato inizializzato
     * @post Restituisce il valore corrente di username
     */

    public String getPassword() {
        return password;
    }   
}

