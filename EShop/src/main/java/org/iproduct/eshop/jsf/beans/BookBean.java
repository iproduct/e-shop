/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.jsf.beans;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import org.iproduct.eshop.jpa.entity.Book;

/**
 *
 * @author admin
 */
@Named(value = "bookBean")
@RequestScoped
public class BookBean {

    @Inject
    @New(org.iproduct.eshop.jpa.entity.Book.class)
    private Book book;
    
    /**
     * Creates a new instance of Book
     */
    public BookBean() {}
    
    @PostConstruct
    public void init() {
        book.getAuthors().add("");
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    public String addBook() {
        return "index";
    }
    
}
