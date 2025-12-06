/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model.storage.FileManager;
import it.unisa.diem.oop.gruppo14bibliotecauniversitaria.view.*;
/**
 *
 * @author elisa
 */
/**
 * @class Controller
 * @brief Gestisce l'interazione tra il modello (FileManager) e la view grafica  (View).
 * 
 * La classe Controller si occupa di:
 * - inizializzare i collegamenti (binding) tra FileManager e View ;
 * - gestire gli eventi generati dall'interazione dell'utente con l'interfaccia grafica.
 * 
 * Segue il pattern MVC (Model-View-Controller).
 */
    public class Controller{
    //IL CONTROLLER HA COME ATTRIBUTI IL MODEL E IL VIEW DEL PROGETTO COSI' DA POTER COERENTEMENTE COLLEGARE IL TUTTO AL FINE UNICO DEL FUNZIONAMENTO
    View  model;  //@brief Modello dell'applicazione che contiene tutta la parte del progetto riguardante la logica  e i dati 
    View view;          //@brief View dell'applicazione che contiene tutta la parte del progetto che si occupa dell'interfaccia utente
    
    
     /**
     * @brief Costruttore della classe Controller.
     *
     * Inizializza il controller collegando il modello e la vista, e richiama i metodi
     * per impostare tutti i  binding e i gestori degli eventi.
     *
     * @param model Il modello dell'applicazione (View)
     * @param view La view associata all'interfaccia grafica (View)
     * @post I metodi initBindings() e initHandler() sono correttamente invocati
     */
     public Controller(View  model,View view){
         this.model=model;
         this.view=view;
         initBindings();
         initHandler();
     }
      /**
     * @brief Permette di collegare view (View) e model (FileManager) tramite i binding
     *
     * Questo metodo si occupa di sincronizzare i componenti grafici della view
     * (es. textfield, label, selettori) con i dati contenuti nel modello,
     * in questo modo eventuali dati inseriti tramite elementi grafici dell'interfaccia,aggiornano i campi corrispondenti nel model,e viceversa
     */
      public void initBindings(){
     
      }
     /**
     * @brief Inizializza i metodi che gestiscono  gli eventi generati dall'interazione dell'utente con l'interfaccia 
     *
     * Si occupa di collegare bottoni, menu e tutti gli altri componenti di tipo grafico contenuti nella View ai metodi
     * del FileManager tramite listener o lambda expression.
     *
     * @post Gli eventi scatenati della View sono correttamente associati alle azioni definite nel model
     */
    public void initHandler(){
     //metodo che gestisce gli eventi causati dall'interazione tra utente e view}
    }
}
