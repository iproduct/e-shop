/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.jsf.beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.iproduct.eshop.ejb.BookEJB;
import org.iproduct.eshop.ejb.PublisherEJB;
import org.iproduct.eshop.jpa.entity.Book;
import org.iproduct.eshop.jpa.entity.Publisher;

/**
 *
 * @author admin
 */
@Named(value = "bookstore")
@ApplicationScoped
public class Bookstore {

    @EJB
    BookEJB bookController;
    
    @EJB
    PublisherEJB publisherController;

    public Bookstore() {
    }

    public List<Book> getBooks() {
        return bookController.findAll();
    }

    public List<Publisher> getPublishers() {
        return publisherController.findAll();
    }
    
}
