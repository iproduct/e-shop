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

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.iproduct.eshop.jpa.entity.Publisher;


/**
 * 
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */

@Singleton
@Startup
public class PublisherDao {
    @PersistenceContext
    EntityManager em;
    
    @EJB
    PublisherEJB publisherController;

    public List<Publisher> listPublishers() {
        return em.createQuery("select p from Publisher p").getResultList();
    }

    @PostConstruct
    public void insertTestData() {
        Publisher p1 = new Publisher("test1", "http://test1.com");
        em.persist(p1);

        Publisher p2 = new Publisher("test2", "http://test2.com");
        em.persist(p2);

        Publisher p3 = new Publisher("test3", "http://test3.com");
        em.persist(p3);
    }
    
    public List<Publisher> findAll() {
        return publisherController.findAll();
    }
}