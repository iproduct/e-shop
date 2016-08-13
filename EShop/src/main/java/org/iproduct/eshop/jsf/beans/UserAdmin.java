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
import org.iproduct.eshop.ejb.GroupEJB;
import org.iproduct.eshop.ejb.UserEJB;
import org.iproduct.eshop.jpa.entity.Groups;
import org.iproduct.eshop.jpa.entity.Users;

/**
 *
 * @author admin
 */
@Named(value = "userAdmin")
@ApplicationScoped
public class UserAdmin {

    @EJB
    UserEJB userController;
    
    @EJB
    GroupEJB groupController;

    public UserAdmin() {
    }

    public List<Users> getUsers() {
        return userController.findAll();
    }

    public List<Groups> getGroups() {
        return groupController.findAll();
    }
    
}
