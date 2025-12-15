/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @file UserManagement.java
 * 
 * @author elisa
 * @date 04-12-2025
 * @version 1.0
 *  
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.management;

import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.data.User;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.*;
/**
 *
 * @class UserManagement 
 * @brief  UserManagement è una classe che si occupa essenzialmente delle funzionalità messe a disposizione per l'elenco degli utenti e di modifica dei dati dei singoli user  
 * 
 * 
 */
public class UserManagement implements Functionality<User>,Serializable{
    private Set<User> list; ///<  Lista di iscritti
    
    
    private final File userDatabase = new File("archivio_utenti.dat"); //<file database dei prestiti
    /**
     *  @brief Costruttore UserManagement 
     * 
     *  @post Il TreeSet(scelto così che potrà mantenere l'ordinamento definito da compareTo nella classe User) che conterrà la lista di utenti è correttamente inizializzato 
     */
    
    
    public UserManagement(){   
        this.list = new TreeSet<>(); 
        
        if (userDatabase.exists() && userDatabase.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userDatabase))) {
                
                Object letto = ois.readObject();
                
                if (letto instanceof Set) {
                    this.list = (Set<User>) letto;
                    System.out.println("Caricamento riuscito: " + list.size() + " utenti.");
                } else {
                    System.err.println("ERRORE FILE: Il file contiene un oggetto " + letto.getClass().getName() + " invece di un Set. Il file verrà sovrascritto al prossimo salvataggio.");
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Attenzione: Impossibile caricare il database utenti (verrà creato nuovo). Dettaglio: " + e.getMessage());
            }
        } else {
            System.out.println("File database non trovato o vuoto. Avvio con lista vuota.");
        }
    }
    
     /**
     * @brief Getter dell'elenco (list )degli utenti iscritti 
     *
     * @pre esiste un elenco list
     * @return elenco degli utenti
     */
    
    public Set<User> getList() {
    return list;}
    
    /**
     *  @brief Metodo che aggiunge un user all'elenco 
     *  @param u L'utente da aggiungere all'elenco 
     *  @pre u != null
     *  @post L'user è correttamente inserito nel TreeSet
     */
    
    @Override
    public boolean add(User u) {
         //controllo se l'utente è null 
         if (u == null) 
         { throw new IllegalArgumentException();
            // esce senza aggiungere ritornando che non è andato a buon fine l'inserimento lanciando l'eccezione che specifica che l'argomento non è valido 
         }
    if(list.add(u)){
      FileManager.writeToTextFileObject(list, this.userDatabase); //aggiorno il file d'archivio 
       return true;}
         return false;
                        }
    
    
    /**
     *  @brief Metodo che rimuove un user dall'elenco 
     *  @param u L'utente da eliminare dall'elenco 
     *  @pre u != null
     *  @post L'user è correttamente eliminato dal TreeSet
     */
    @Override
    public boolean remove(User u ) {
         if (u == null) 
         { throw new IllegalArgumentException();
            // esce senza rimuovere ritornando che non è andato a buon fine l'operazione lanciando l'eccezione che specifica che l'argomento non è valido 
         } 
         for(User utente: list){  //scorro tutta la nostra lista 
             if(utente.equals(u))  //se l'elemento della lista ha matricola uguale a quella dell'utente da rimuovere allora è quello cercato
             { list.remove(utente);
               FileManager.updateFileObject(list, this.userDatabase); //aggiorno il file d'archivio 
               return true; //la rimozione è stata effettuata 
              }}
          return false; //se arrivo a questo punto significa che non ho trovato nella lista l'utente da eliminare quindi l'operazione non è andata a buon fine
    }
    /**
     *  @brief Metodo che permette di aggiornare i dati di un utente
     *  @param u L'utente di cui bisogna modificare i dati 
     *  @pre u != null
     *  @post I dati dell'utente u sono correttamente aggiornati 
     */
    @Override
    public boolean update(User u1 ,User u2){   //deve prima inserire tutri i dati dell'utente di cui vuole cambiare le informazioni
       if (u1 == null) 
         { throw new IllegalArgumentException();
            // esce senza aggiornare ritornando che non è andato a buon fine l'operazione lanciando l'eccezione che specifica che l'argomento non è valido 
         } 
        
        boolean delate=false;
        if(u1==null) return false;
        else{
            for(User u: list){
                if(u.equals(u1)){
                    delate=remove(u1);  //rimuovo l'utente di cui saranno cambiati i dati
                  FileManager.updateFileObject(list, this.userDatabase);
                    return add(u2);  //aggiunge l'utente con i nuovi dati 
                }}
        } return false; //se arrivo a questo punto significa che non abbiamo trovato l'utente di cui volevamo aggiornare 
        
        
    }
    /**
     *  @brief Metodo che permette di visualizzare il TreeSet contenente l'elenco ordinato in base al cognome e  nome degli utenti  
     *  @post Lelenco opportunamente ordinato è correttamente visualizzato 
     */
    
    @Override
    public void viewSorted(){
          for(User u:list){
              System.out.println(u.toString()); //stampo per ogni utente appartenente all'elenco i corrispondenti   dati 
          }
    }
    /**
     *  @brief Metodo che permette di cercare un utente nell'elenco tramite cognome o matricola 
     *  @param s  Dato utilizzato dal bibliotecario per cercare l'utente 
     *  @pre s != null
     *  @post L'utente viene mostrato 
     *  @return L'utente trovato
     */
    @Override
    public List <User> search(User u ){
        List<User> lista=new ArrayList<>();  //usiamo una lista dato che se la ricerca viene fatta per cognome possono esserci più utenti nell'elenco corrispondenti 
         if (u == null ) throw new IllegalArgumentException();
            //esce dato che l'utente da cercare non è valido e lancia l'eccezione adeguata 
        if (u.getNumberId() != null) {  //se l'utente è stato cercato tramite matricola solo quel campo non sarà nullo grazie all'utilizzo del secondo costruttore 
            for (User us : list) {
                if (us.equals(u)) { //l'uguaglianza deve essere sulla base della matricola esattamente come il metodo equals() di User quindi lo uso direttamente 
                    lista.add(us);
                }
            } 
        } else if (u.getSurname() != null) {  //se l'utente è stato cercato tramite cognome solo quel campo non sarà nullo grazie all'utilizzo del secondo costruttore 
            for (User us : list) {
                if (us.getSurname().equalsIgnoreCase(u.getSurname())) {   //se trovo nell'elenco un User che ha cognome corrispondente a quello cercato allora lo ritorno 
                   lista.add(us);
                }
            }
        }
        return lista ; 
     }
    
}

