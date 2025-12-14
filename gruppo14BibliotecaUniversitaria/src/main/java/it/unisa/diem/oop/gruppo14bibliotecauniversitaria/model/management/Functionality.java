/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file Functionality.java
 * 
 * @author bruno
 * @date 04-12-2025
 * @version 1.0
 *  
 */

package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import java.util.Comparator;
import java.util.*;

/**
 * @interface Functionality
 * @brief Interfaccia generica per la gestione di entità.
 *
 * Definisce le operazioni fondamentali che un modulo di gestione deve implementare:
 * aggiunta, rimozione, aggiornamento, visualizzazione ordinata e ricerca.
 * L'interfaccia è parametrizzata con un tipo generico T, che rappresenta il tipo
 * da gestire.
 *
 * @tparam T Tipo dell'entità gestita
 */

public interface Functionality <T> {
    
     /**
     * @brief Aggiunge un'entità.
     * @param entity Entità da aggiungere 
     * @return true se l'entità è stata aggiunta, false altrimenti
     *
     * @pre entity != null
     * @post L'entità è aggiunta alla collezione se non già presente
     */
    boolean add(T entity);
    
    /**
     * @brief Rimuove un'entità.
     * @param entity Entità da rimuovere (non deve essere null)
     * @return true se l'entità è stata rimossa, false altrimenti
     *
     * @pre entity != null
     * @post L'entità è rimossa dalla collezione se presente
     */

    boolean remove(T entity);
    
    /**
     * @brief Aggiorna un'entità.
     * 
     * @param entity1 Entità da aggiornare (non deve essere null)
     * @param entity2 Nuova entità
     * 
     * @return true se l'aggiornamento è avvenuto con successo, false altrimenti
     *
     * @pre entity != null
     * @post I dati dell'entità sono aggiornati nella collezione
     */
    boolean update(T entity1, T entity2);
    
    /**
     * @brief Visualizza le entità ordinate.
     *
     * @pre La collezione deve essere stata inizializzata
     * @post Le entità vengono mostrate secondo l'ordinamento definito
     */

    
    void viewSorted();
    
     /**
     * @brief Cerca un'entità nella collezione.
     * @param entity1 Entità da cercare (non deve essere null)
     * @return L'entità trovata, oppure null se non presente
     *
     * @pre entity != null
     * @post Restituisce l'entità corrispondente se presente, altrimenti null
     */
   
    List<T> search(T entity1);
}
