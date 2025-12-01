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
 */
public interface Functionality <T> {
    
    void add(T entity);
    
    void remove(T entity);
    
    void update(T entity);
    
     Set<T> viewSorted(Comparator<T> comparator);
     
     T search(T entity);
}
