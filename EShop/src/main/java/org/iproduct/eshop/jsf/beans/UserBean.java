/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.jsf.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.iproduct.eshop.ejb.EShopBootstrap;
import org.iproduct.eshop.ejb.GroupEJB;
import org.iproduct.eshop.ejb.UserEJB;
import org.iproduct.eshop.exceptions.PreexistingEntityException;
import org.iproduct.eshop.jpa.entity.Book;
import org.iproduct.eshop.jpa.entity.Publisher;
import org.iproduct.eshop.jpa.entity.Users;
import org.iproduct.eshop.jsf.utils.JsfUtils;

/**
 *
 * @author admin
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    @Inject
    @New(org.iproduct.eshop.jpa.entity.Users.class)
    private Users user;

    @EJB
    private UserEJB userController;

    @EJB
    GroupEJB groupController;

    /**
     * Creates a new instance of Book
     */
    public UserBean() {
    }

    public Users getUser() {
        return user;
    }

    // Business methods
    public String addUser() {
        try {
            userController.create(user);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.addErrorMessage(ex.getMessage(), ex.getMessage());
            return null;
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            while (cause != null && !(cause instanceof ConstraintViolationException)) {
                cause = cause.getCause();
            }
            if (cause instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) cause;
                for (ConstraintViolation cv : cve.getConstraintViolations()) {
                    Logger.getLogger(EShopBootstrap.class.getName()).log(Level.SEVERE,
                            "Constaint violation: {0}: {1}",
                            new Object[]{cv.getMessage(), cv.getInvalidValue()});
                }
            }
            return null;
        }
        return "index";
    }

}
