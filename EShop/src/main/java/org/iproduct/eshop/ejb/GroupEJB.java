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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.iproduct.eshop.exceptions.PreexistingEntityException;
import org.iproduct.eshop.jpa.entity.Groups;
import org.iproduct.eshop.jpa.entity.Groups_;


/**
 * 
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */

@Stateless
public class GroupEJB extends AbstractFacade<Groups>{
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB 
    PublisherEJB publisherController;

    public GroupEJB() {
        super(Groups.class);
    }
   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public Groups create(Groups group) throws PreexistingEntityException {
               if (group.getName() != null && findByName(group.getName()) != null) {
            throw new PreexistingEntityException("Group with name: "
                    + group.getName() + " already exists.");
        }
        return super.create(group);
    }

    public Groups findByName(String name) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery();
        Root<Groups> groupsRoot = criteriaQuery.from(Groups.class);
        criteriaQuery.where(builder.equal(
            groupsRoot.get(Groups_.name),  
            builder.parameter(String.class, "name")));
        
        //Escaping "name" parameter automatically
        Query query = em.createQuery(criteriaQuery).setParameter("name", name);
        List<Groups> publishers = query.getResultList();
        if (publishers.size() > 0) {
            return publishers.get(0);
        } else {
            return null;
        }
    }
      
}
