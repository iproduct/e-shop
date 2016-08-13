/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.ejb;

import static java.util.concurrent.ThreadLocalRandom.current;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.iproduct.eshop.exceptions.PreexistingEntityException;
import org.iproduct.eshop.jpa.entity.Groups;
import org.iproduct.eshop.jpa.entity.Users;
import org.iproduct.eshop.jsf.utils.UserUtils;

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

    @EJB
    GroupEJB groupController;

    @EJB
    UserEJB userController;

    @PostConstruct
    public void init() {
//        em.persist(new Publisher("AddisonWesley", "https://www.pearsonhighered.com/"));
//        Publisher p1 = new Publisher("Prentice Hall", "http://www.prenticehall.com/");
//        Book b1 = new Book("Core JavaServer Faces (3rd Edition)", "978-0137012893",
//            new String[]{"David Geary",  "Cay S. Horstmann"}, 
//            "JavaServer Faces (JSF) is the standard Java EE technology for building web user interfaces. It provides a powerful framework for developing server-side applications, allowing you to cleanly separate visual presentation and application logic. JSF 2.0 is a major upgrade, which not only adds many useful features but also greatly simplifies the programming model by using annotations and “convention over configuration” for common tasks.", 
//            p1, "https://images-na.ssl-images-amazon.com/images/I/51FMWNAMAgL._SX359_BO1,204,203,200_.jpg", 
//            "http://corejsf.com/", 35, .1);
//        bookController.create(b1);

        if (groupController.getCount() == 0) {
            Groups[] defaultGroups = {
                new Groups("CUSTOMERS", "EShop customers."),
                new Groups("SELLERS", "EShop sellers."),
                new Groups("ADMINS", "EShop admins."),};
            for (Groups g : defaultGroups) {
                try {
                    groupController.create(g);
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(EShopBootstrap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (userController.getCount() == 0) {
            userController.createUser("Super", "Admin", "admin@eshop.com", 
                    "admin", "Sofia", "Sofia", new String[]{"ADMINS"});
            userController.createUser("Default", "Seller", "seller@eshop.com", 
                    "seller", "Sofia", "Sofia", new String[]{"SELLERS"});
            userController.createUser("Default", "Customer", "customer@eshop.com", 
                    "customer", "Sofia", "Sofia", new String[]{"CUSTOMERS"});
        }

//        System.out.println("\nBooks:");
//        List<Book> books = em.createQuery("SELECT b FROM Book b")
//                .getResultList();
//        for (Book b: books) {
//            System.out.println(b);
//        }
    }
}
