/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iproduct.eshop.jsf.beans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author admin
 */
@Named(value = "navigation")
@RequestScoped
public class Navigation implements Serializable {

    /**
     * Creates a new instance of Navigation
     */
    public Navigation() {
    }

    public String getCurrentView() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewId().split("[/\\.]")[1];
    }
    
    public String getActiveClass(String view) {
        System.out.println(getCurrentView());
        return (getCurrentView().equals(view)) ? "active" : "";
    }
    
}
