/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2003-2014 IPT - Intellectual Products & Technologies.
 * All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 with Classpath Exception only ("GPL"). 
 * You may use this file only in compliance with GPL. You can find a copy 
 * of GPL in the root directory of this project in the file named LICENSE.txt.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the GPL file named LICENSE.txt in the root directory of 
 * the project.
 *
 * GPL Classpath Exception:
 * IPT - Intellectual Products & Technologies (IPT) designates this particular 
 * file as subject to the "Classpath" exception as provided by IPT in the GPL 
 * Version 2 License file that accompanies this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 */
package org.iproduct.eshop.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.iproduct.eshop.jpa.controller.exceptions.NonexistentEntityException;
import org.iproduct.eshop.jpa.entity.Book;
import org.iproduct.eshop.jpa.entity.Publisher;


/**
 * 
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */

@Stateless
public class BookEJB extends AbstractFacade<Book>{
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB 
    PublisherEJB publisherController;

    public BookEJB() {
        super(Book.class);
    }
   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    

    
    @Override
    public Book create(Book book) {
        Publisher publisher = book.getPublisher(),
            existingPublisher = null;
        
        if(publisher.getId()!= null) {
            existingPublisher = publisherController.findById(publisher.getId());
        } 
        if(existingPublisher == null) {  
            existingPublisher = publisherController.findByName(publisher.getName());
//            System.out.println("Found by name: " + existingPublisher);
        }
        if(existingPublisher == null) {
            existingPublisher = publisherController.create(publisher);
        }
//        System.out.println("To be set: " + existingPublisher);
        book.setPublisher(existingPublisher);
        return super.create(book);
    }
    
    
}
