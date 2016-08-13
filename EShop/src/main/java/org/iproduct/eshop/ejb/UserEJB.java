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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.iproduct.eshop.exceptions.PreexistingEntityException;
import org.iproduct.eshop.jpa.entity.Groups;
import org.iproduct.eshop.jpa.entity.Users;
import org.iproduct.eshop.jpa.entity.Users_;
import org.iproduct.eshop.jsf.utils.UserUtils;

/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@Stateless
public class UserEJB extends AbstractFacade<Users> {

    @PersistenceContext
    private EntityManager em;

    @EJB
    UserEJB userController;

    @EJB
    GroupEJB groupController;

    public UserEJB() {
        super(Users.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void createUser(String fname, String lname, String email, String password, 
            String city, String address, String[] groupNames) {
        Users user = new Users(fname, lname, email, city, address);
        for (String gName : groupNames) {
            Groups group = groupController.findByName(gName);
            if (group != null) {
                user.getGroups().add(group);
            }
        }
        user.setPassword(UserUtils.getMD5Hash(password));
        try {
            userController.create(user);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(UserEJB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            while (cause != null && !(cause instanceof ConstraintViolationException)) {
                cause = cause.getCause();
            }
            if (cause instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) cause;
                for (ConstraintViolation cv : cve.getConstraintViolations()) {
                    Logger.getLogger(UserEJB.class.getName()).log(Level.SEVERE,
                            "Constaint violation: {0}: {1}",
                            new Object[]{cv.getMessage(), cv.getInvalidValue()});
                }
            } else {
                Logger.getLogger(UserEJB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Users create(Users user) throws PreexistingEntityException {
        if (user.getEmail() != null && findByEmail(user.getEmail()) != null) {
            throw new PreexistingEntityException("User with email: "
                    + user.getEmail() + " already exists.");
        }
        List<Groups> existingGroups = new ArrayList<>();
        for (Groups group : user.getGroups()) {
            Groups foundGroup = groupController.findById(group.getId());
            if (foundGroup != null) {
                existingGroups.add(foundGroup);

            } else {
                Logger.getLogger(UserEJB.class
                        .getName()).log(Level.SEVERE,
                                "Group {0} does not exist when creating user {1}",
                                new Object[]{group, user});
//                throw new NonexistentEntityException("Group " 
//                        + group.getId() + ":" + group.getName() 
//                        + " does not exist when creating user "
//                        + user.getEmail());
            }
        }
        user.setGroups(existingGroups);
        return super.create(user);
    }

    @Override
    public Users edit(Users user) {
        Users existingUser = null;

        if (user.getId() != null) {
            existingUser = findById(user.getId());
        }
        if (existingUser == null) {
            existingUser = findByEmail(user.getEmail());
//            System.out.println("Found by name: " + existingUser);
        }
        if (existingUser == null) {
            try {
                existingUser = super.create(user);

            } catch (PreexistingEntityException ex) {
                Logger.getLogger(UserEJB.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
//        System.out.println("To be set: " + existingUser);
        return existingUser;
    }

    public Users findByEmail(String email) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery();
        Root<Users> usersRoot = criteriaQuery.from(Users.class
        );
        criteriaQuery
                .where(builder.equal(
                        usersRoot.get(Users_.email),
                        builder.parameter(String.class, "email")));

        //Escaping "name" parameter automatically
        Query query = em.createQuery(criteriaQuery).setParameter("email", email);
        List<Users> users = query.getResultList();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

}
