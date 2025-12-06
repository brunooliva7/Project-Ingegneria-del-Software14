/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage;

import java.io.Serializable;

/**
 * @class FileManager
 * @brief Classe generica per la gestione di file contenenti oggetti serializzabili e linee di testo.
 *
 * Questa classe fornisce metodi statici per:
 * - Scrivere, leggere e aggiornare oggetti serializzabili su file (binario).
 * - Scrivere, leggere e aggiornare linee di testo su file (testuale).
 *
 * @tparam T Tipo generico che deve implementare Serializable.
 */

public class FileManager <T extends Serializable>{
    
    /**
     * @brief Elimina l'oggetto dal file
     *
     * @param filename Nome del file da cui leggere
     * @param object oggetto di tipo T da eliminare
     * 
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * @throws ClassNotFoundException Se la classe degli oggetti non viene trovata.
     * 
     * @pre filename valido e object != null
     * @post l'oggetto è stato cancellato correttamente e il file aggiornato
     */

    public static removeFromTextFileObject(T object, String filename){       
    }
    
    
    /**
     * @brief Scrive un oggetto serializzabile su file.
     *
     * @param object Oggetto da scrivere.
     * @param filename Nome del file di destinazione.
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * 
     * @pre object !=0 , filename valido 
     * @post il file è stato scritto correttamente 
     */

    public static writeToTextFileObject(T object, String filename){
        
    }
    
    /**
     * @brief Aggiorna un oggetto nel file sostituendolo con uno nuovo.
     *
     * @param filename Nome del file da aggiornare.
     * @param newObj Nuovo oggetto da inserire.
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * @throws ClassNotFoundException Se la classe degli oggetti non viene trovata.
     * 
     * @pre newObject !=0 , filename valido 
     * @post il file è stato aggiornamento correttamente 
     */

    
    public static updateFileObject(T newObject, String filename){
        
    }
    
    
    /**
     * @brief Legge riga da un file di testo.
     *
     * @param filename Nome del file da cui leggere.
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * @pre filename valido 
     * @post lettura avvenuta correttamente
     */

    public static readLine(String filename){
        
    }
    
     /**
     * @brief Scrive riga su un file di testo.
     *
     * @param filename Nome del file da cui leggere.
     * @param line stringa da scrivere nel file
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * @pre filename valido, line != null
     * @post lettura avvenuta correttamente
     */
    
    public static writeLine(String line,String filename){
        
    }
    
    /**
     * @brief Scrive riga su un file di testo.
     *
     * @param filename Nome del file da cui leggere.
     * @param line stringa da scrivere nel file
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * @pre filename valido, line != null
     * @post lettura avvenuta correttamente
     */
    public static updateLine(String line,String filename){
        
    }  
}

