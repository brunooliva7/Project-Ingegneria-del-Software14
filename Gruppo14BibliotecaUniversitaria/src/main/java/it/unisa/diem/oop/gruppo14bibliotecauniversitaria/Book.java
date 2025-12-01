/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.oop.gruppo14bibliotecauniversitaria;

/**
 *
 * @author maramariano
 */
public class Book implements Comparable<Book> {
    
    private String title;
    private String authors;
    private int publicationYear;
    private String ISBN;
    private int availableCopies;
    
    public Book(String title, String authors, int publicationYear, String ISBN, int availableCopies) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
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
