/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;

import java.util.Comparator;
import java.util.*;

/**
 *
 * @author maramariano
 * @param <T>
 */
public interface Functionality <T> {
    
    boolean add(T entity);
    
    boolean remove(T entity);
    
    boolean update(T entity);
    
    void viewSorted();
     
    T search(T entity);
}
