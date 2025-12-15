/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View;
import java.io.IOException;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.Model;
/**
 * @class homepageController
 * @brief Controller per la schermata principale (Homepage) dell'applicazione.
 *
 * Questa classe gestisce la barra dei menu e la navigazione principale del sistema.
 * Intercetta gli eventi di click sulle voci del menu (MenuItem) e invoca i metodi statici
 * della classe View per cambiare la scena visualizzata, permettendo l'accesso alle
 * sezioni di gestione Utenti, Libri e Prestiti.
 * * @see it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.View
 */
public class homepageController {
    
    @FXML
    private MenuItem aggiungiUtente; ///< Voce di menu per aggiungere un nuovo utente.
    
    @FXML
    private MenuItem modificaUtente; ///< Voce di menu per modificare un utente esistente.
    
    @FXML
    private MenuItem cancellaUtente; ///< Voce di menu per eliminare un utente.
    
    @FXML
    private MenuItem cercaUtente; ///< Voce di menu per ricercare un utente.
    
    @FXML
    private MenuItem aggiungiLibro; ///< Voce di menu per aggiungere un nuovo libro al catalogo.
    
    @FXML
    private MenuItem cancellaLibro; ///< Voce di menu per rimuovere un libro dal catalogo.
    
    @FXML
    private MenuItem modificaLibro; ///< Voce di menu per modificare i dati di un libro.
    
    @FXML
    private MenuItem cercaLibro; ///< Voce di menu per ricercare un libro.
    
    @FXML
    private MenuItem aggiungiPrestito; ///< Voce di menu per registrare un nuovo prestito.
    
    @FXML
    private MenuItem cancellaPrestito; ///< Voce di menu per eliminare (restituire) un prestito.
    
    @FXML
    private MenuItem visualizzaElencoPrestito; ///< Voce di menu per visualizzare tutti i prestiti attivi.
    
    @FXML
    private MenuItem cercaPrestito; ///< Voce di menu per cercare un prestito specifico.
    
    /** @brief Riferimento al modello dell'applicazione (non utilizzato direttamente in questa logica di navigazione pura). */
    private Model model;
    
    /**
     * @brief Gestisce il click su "Aggiungi Utente".
     * * Carica la scena per l'inserimento di un nuovo utente.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void addUser() throws IOException{
        View.addUser();
    }
    
    /**
     * @brief Gestisce il click su "Modifica Utente".
     * * Carica la scena per la modifica dei dati utente.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void modifyUser() throws IOException{
        View.modifyUser();
    }
    
    /**
     * @brief Gestisce il click su "Elimina Utente".
     * * Carica la scena per la rimozione di un utente.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML 
    public void deleteUser() throws IOException{
        View.deleteUser();
    }
    
    /**
     * @brief Gestisce il click su "Cerca Utente".
     * * Carica la scena per la ricerca degli utenti.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void searchUser() throws IOException{
        View.searchUser();
    }
    
    /**
     * @brief Gestisce il click su "Visualizza Elenco Utenti".
     * * Carica la scena che mostra la lista completa degli utenti.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void viewUser() throws IOException{
        View.viewUSers();
    }
    
    /**
     * @brief Gestisce il click su "Aggiungi Libro".
     * * Carica la scena per l'inserimento di un nuovo libro nel catalogo.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void addBook() throws IOException{
        View.addBook();
    }
    
    /**
     * @brief Gestisce il click su "Cancella Libro".
     * * Carica la scena per la rimozione di un libro.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void deleteBook() throws IOException{
        View.deleteBook();
    }
    
    /**
     * @brief Gestisce il click su "Modifica Libro".
     * * Carica la scena per la modifica dei dettagli di un libro.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void updateBook() throws IOException{
        View.updateBook();
    }
    
    /**
     * @brief Gestisce il click su "Cerca Libro".
     * * Carica la scena per la ricerca nel catalogo libri.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void searchBook() throws IOException{
        View.searchBook();
    }
    
    /**
     * @brief Gestisce il click su "Visualizza Elenco Libri".
     * * Carica la scena che mostra il catalogo completo.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void viewBooks() throws IOException{
        View.viewBooks();
    }
    
    /**
     * @brief Gestisce il click su "Aggiungi Prestito".
     * * Carica la scena per la creazione di un nuovo prestito.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void addLoan() throws IOException{
        View.addLoan();
    }
    
    /**
     * @brief Gestisce il click su "Cerca Prestito".
     * * Carica la scena per la ricerca tra i prestiti attivi.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void searchLoan() throws IOException{
        View.searchLoan();
    }
    
    /**
     * @brief Gestisce il click su "Elimina Prestito".
     * * Carica la scena per la restituzione o cancellazione di un prestito.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void deleteLoan() throws IOException{
        View.deleteLoan();
    }
    
    /**
     * @brief Gestisce il click su "Visualizza Elenco Prestiti".
     * * Carica la scena che mostra la lista di tutti i prestiti.
     * @throws IOException Se si verifica un errore nel caricamento del file FXML.
     */
    
    @FXML
    public void viewLoan() throws IOException{
        View.viewLoan();
    }
}
