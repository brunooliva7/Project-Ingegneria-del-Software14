/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.control;

/**
 *
 * @author maramariano
 */
public class Control {
     Model model;
      View view;
    
     public Controller(Model model,View view){
         this.model=model;
         this.view=view;
         initBindings();
         initHandler();
     }
     
      public void initBindings(){
      // Prima cosa: collego textfield(view) ↔ mode
      }
      //metodo che gestisce gli eventi causati dall'interazione tra utente e view
    public void initHandler(){}
}
