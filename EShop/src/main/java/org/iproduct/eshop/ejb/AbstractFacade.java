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
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.iproduct.eshop.exceptions.NonexistentEntityException;
import org.iproduct.eshop.exceptions.PreexistingEntityException;
import org.iproduct.eshop.jpa.entity.Identifiable;

/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 * @param <T> type of entity managed
 */
public abstract class AbstractFacade<T extends Identifiable> {

    private Class<T> entityClass;

    public AbstractFacade() {
    }

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T create(T entity) throws PreexistingEntityException {
        if(entity.getId() != null && findById(entity.getId()) != null) {
            throw new PreexistingEntityException("Entity " + entityClass.getSimpleName() 
                    + " with ID=" + entity.getId() + " already exists.");
        }
        getEntityManager().persist(entity);
        return entity;
    }

    public T edit(T entity){
//        EntityManager em = getEntityManager();
//        T existingEntity = em.find(entityClass, entity.getId());
//        if (existingEntity == null) {
//            throw new NonexistentEntityException("Entity " + entityClass.getSimpleName() 
//                    + " with ID=" + entity.getId() + " does not exist.");
//        }
        return getEntityManager().merge(entity);
    }

    public T remove(long entityId) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, entityId);
        if (entity == null) {
            throw new NonexistentEntityException("The [" 
                    + entityClass.getSimpleName() + "] entity with id " 
                    + entityId + " no longer exists.");
        }
        em.remove(entity);
        return entity;
    }
    
    public T findById(Object entityId) {
        return getEntityManager().find(entityClass, entityId);
    }
    
    public List<T> findAll() {
        return find(true, -1, -1);
    }

    public List<T> findRange(int maxResults, int firstResult) {
        return find(false, maxResults, firstResult);
    }

    public List<T> find(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        CriteriaQuery criteriaQuery = getCriteriaBuilder().createQuery();
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select( root );
        Query query  = em.createQuery(criteriaQuery);
         if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
       return query.getResultList();
    }
    
    public long getCount() {
        EntityManager em = getEntityManager();
        CriteriaQuery criteriaQuery = getCriteriaBuilder().createQuery();
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(getCriteriaBuilder().count(root));
        Query query  = em.createQuery(criteriaQuery);
        return (Long) query.getSingleResult();
    }             
    
    public CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }
}
