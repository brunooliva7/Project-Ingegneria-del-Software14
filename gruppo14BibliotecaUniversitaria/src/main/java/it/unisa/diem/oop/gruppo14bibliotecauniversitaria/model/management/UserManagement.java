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
     *  @post L'HashSet che conterrà la lista di utenti è correttamente inizializzato 
     */
    
    
    public UserManagement(){   
        this.list = new HashSet<>(); 
        
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
        return list;
    }
    
    /**
     *  @brief Metodo che aggiunge un user all'elenco 
     *  @param u L'utente da aggiungere all'elenco 
     *  @throws IllegalArgumentException se l'utente passato è `null`.
     *  @return true se l'utente è correttamente inserito,altrimenti false
     *  @post L'user è correttamente inserito nell'HashSet
     */
    
    @Override
    public boolean add(User u) {
         //controllo se l'utente è null 
         if (u == null) 
         { throw new IllegalArgumentException();
            // esce senza aggiungere ritornando che non è andato a buon fine l'inserimento lanciando l'eccezione che specifica che l'argomento non è valido 
         }
         //l'hashset usa equals() e hashCode() per l'inserimento quindi non permetterà di aggiungere un nuovo utente se la sua matricola è già presente 
         if(list.add(u)){
         FileManager.writeToTextFileObject(list, this.userDatabase); //aggiorno il file d'archivio 
         return true;}
         
         return false; 
                        } 
    
    
    /**
     *  @brief Metodo che rimuove un user dall'elenco 
     *  @param u L'utente da eliminare dall'elenco 
     *  @return true se l'utente è stato rimosso correttamente,altrimenti false
     *  @throws IllegalArgumentException se l'utente passato è `null`.
     *  @post L'user è correttamente eliminato dall'HashSet
     */
    @Override
    public boolean remove(User u ) {
         if (u == null) 
         { throw new IllegalArgumentException();
            // esce senza rimuovere ritornando che non è andato a buon fine l'operazione lanciando l'eccezione che specifica che l'argomento non è valido 
         } 
         boolean removed = list.remove(u); // O(1)
         if(removed){
         FileManager.updateFileObject(list, this.userDatabase);  //aggiorno il file
         return true;
    }
     return removed; //se arruva qui significs che non ha trovato l'utente da rimuovere 
    }
    /**
     *  @brief Metodo che permette di aggiornare i dati di un utente
     *  @param u1 L'utente di cui bisogna modificare i dati 
     *  @param u2 L'utente creato a partire dai dati modificati di u1
     *  @return true se l'utente u1 è stato correttamente aggiornato,altrimenti false
     *  @throws IllegalArgumentException se anche solo uno tra gli utenti passato è  `null`.
     *  @post I dati dell'utente u1 sono correttamente aggiornati 
     */
    @Override
    public boolean update(User u1 ,User u2){   //deve prima inserire tutri i dati dell'utente di cui vuole cambiare le informazioni
       if (u1 == null || u2==null) 
         { throw new IllegalArgumentException();
            // esce senza aggiornare ritornando che non è andato a buon fine l'operazione lanciando l'eccezione che specifica che l'argomento non è valido 
         } 
        
        boolean delate=false;
        if(u1==null) return false;
        else if(remove(u1)){
          //rimuovo l'utente di cui saranno cambiati i dati
                  FileManager.updateFileObject(list, this.userDatabase);
                  return add(u2);  //aggiunge l'utente con i nuovi dati 
                }
         return false; //se arrivo a questo punto significa che non abbiamo trovato l'utente di cui volevamo aggiornare 
         }
    /**
     *  @brief Metodo che permette di visualizzare l'HashSet contenente l'elenco  
     *  @post Lelenco  è correttamente stampato
     */
    
    @Override
    public void viewSorted(){
          for(User u:list){
              System.out.println(u.toString()); //stampo per ogni utente appartenente all'elenco i corrispondenti   dati 
          }
    }
    /**
     *  @brief Metodo che permette di cercare un utente nell'elenco tramite cognome o matricola 
     *  @param u Utente creato a partire dalla stringa utilizzata per la search dal bibliotecario 
     *  @throws IllegalArgumentException se l'utente passato è `null`.
     *  @post L'utente viene mostrato 
     *  @return Lista di utenti che corrispondono ai criteri di ricerca
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

