/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.jsf.beans;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.iproduct.eshop.ejb.BookEJB;
import org.iproduct.eshop.jpa.entity.Book;

/**
 *
 * @author admin
 */
@Named(value = "bookstore")
@ApplicationScoped
public class Bookstore {

    @EJB 
    BookEJB bookController;
   
    public Bookstore() {
    }
    
    public List<Book> getBooks() {
        return bookController.findAll();
    }
    
    
}
