/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria.model;
import java.time.LocalDate;
        
/**
 *
 * @author maramariano
 */
public class Book implements Comparable<Book> {
    
    private String title;
    private String authors;
    private LocalDate publicationYear;
    private String ISBN;
    private int availableCopies;
    
    public Book(String title, String authors, LocalDate publicationYear, String ISBN, int availableCopies) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
        this.availableCopies = availableCopies;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setPublicationYear(LocalDate publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    
    
    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }
    
    @Override
    public int compareTo(Book other) {
    }
    
}
