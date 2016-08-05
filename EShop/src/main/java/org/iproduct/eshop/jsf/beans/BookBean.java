/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.jsf.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import org.iproduct.eshop.ejb.BookEJB;
import org.iproduct.eshop.jpa.entity.Book;

/**
 *
 * @author admin
 */
@Named(value = "bookBean")
@ConversationScoped
public class BookBean implements Serializable{

    @Inject
    private Conversation conversation;
    
    @Inject
    @New(org.iproduct.eshop.jpa.entity.Book.class)
    private Book book;
    
    @EJB
    private BookEJB bookController;
    
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
    
    public String addBook() {
        if(!conversation.isTransient())
            conversation.end();
        bookController.create(book);
        return "index";
    }
    
    public String addAuthor() {
        if(conversation.getId() == null)
            conversation.begin("add-authors");
        System.out.println("!!!! addAuthor called:" + book.getAuthors().size());
        book.getAuthors().add("aaaaa");
        return  "";
    }
    
       
}
