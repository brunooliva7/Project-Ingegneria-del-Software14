/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;

import java.time.LocalDate;

/**
 *
 * @author maramariano
 */
public class Loan{
    
    private Book book;
    private User user;
    private LocalDate dueDate;

    public Loan(Book book, User user, LocalDate dueDate) {
        this.book = book;
        this.user = user;
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
