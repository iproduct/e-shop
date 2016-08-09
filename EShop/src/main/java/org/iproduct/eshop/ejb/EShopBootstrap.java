/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.ejb;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.iproduct.eshop.jpa.entity.Book;
import org.iproduct.eshop.jpa.entity.Publisher;

/**
 *
 * @author admin
 */
@Startup
@Singleton
public class EShopBootstrap {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    BookEJB bookController;

    @PostConstruct
    public void init() {
//        em.persist(new Publisher("AddisonWesley", "https://www.pearsonhighered.com/"));
        Publisher p1 = new Publisher("Prentice Hall", "http://www.prenticehall.com/");
        Book b1 = new Book("Core JavaServer Faces (3rd Edition)", "978-0137012893",
            new String[]{"David Geary",  "Cay S. Horstmann"}, 
            "JavaServer Faces (JSF) is the standard Java EE technology for building web user interfaces. It provides a powerful framework for developing server-side applications, allowing you to cleanly separate visual presentation and application logic. JSF 2.0 is a major upgrade, which not only adds many useful features but also greatly simplifies the programming model by using annotations and “convention over configuration” for common tasks.", 
            p1, "https://images-na.ssl-images-amazon.com/images/I/51FMWNAMAgL._SX359_BO1,204,203,200_.jpg", 
            "http://corejsf.com/", 35, .1);
        bookController.create(b1);
        System.out.println("\nPublishers:");
        List<Publisher> publishers = em.createQuery("SELECT p FROM Publisher p")
                .getResultList();
        for (Publisher p : publishers) {
            System.out.println(p);
        }

        System.out.println("\nBooks:");
        List<Book> books = em.createQuery("SELECT b FROM Book b")
                .getResultList();
        for (Book b: books) {
            System.out.println(b);
        }
    }
}
