/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file FileManager.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @brief Scrive un oggetto serializzabile su file.
     *
     * @param object Oggetto da scrivere.
     * @param file File da modificare
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * 
     * @pre object !=0 , filename valido 
     * @post Il file è stato scritto correttamente 
     */

    public static <T extends Serializable> void writeToTextFileObject(T object, File file){
         if (object == null || file == null) {
            throw new IllegalArgumentException("Oggetto o nome file non valido");
        }
        try (ObjectOutputStream write = new ObjectOutputStream((new FileOutputStream(file)))){
            write.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    /**
     * @brief Aggiorna un oggetto nel file sostituendolo con uno nuovo.
     *
     * @param file File da modificare
     * @param newObj Nuovo oggetto da inserire.
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * @throws ClassNotFoundException Se la classe degli oggetti non viene trovata.
     * 
     * @pre filename valido 
     * @post Il file è stato aggiornamento correttamente 
     */

    
    public static <T extends Serializable> void updateFileObject(Set <T> newTree, File file){  
         
        if (file == null) {
            throw new IllegalArgumentException("Nome file non valido");
        }

        // Aprendo in modalità "write" senza append, il file viene svuotato
        try (FileOutputStream fos = new FileOutputStream(file)) {
            // Non scrivo nulla → file vuoto
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (ObjectOutputStream write = new ObjectOutputStream((new FileOutputStream(file)))){
            write.writeObject(newTree);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    
    /**
     * @brief Legge riga da un file di testo.
     *
     * @param file File da modificare
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * @pre filename valido 
     * @post Lettura avvenuta correttamente
     */

    public static void readLine(File file){
        
        String line;
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            line = reader.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * @brief Scrive riga su un file di testo.
     *
     * @param file File da modificare
     * @param line Stringa da scrivere nel file
     * 
     * @throws IOException Se si verifica un errore di I/O.
     * 
     * @pre filename valido, line != null
     * @post Lettura avvenuta correttamente
     */
    
    public static void writeLine(String line, File file){
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(line);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

