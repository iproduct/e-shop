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
package org.iproduct.eshop.jsf.convertors;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import org.iproduct.eshop.ejb.PublisherEJB;
import org.iproduct.eshop.jpa.entity.Publisher;

/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@Named
@ApplicationScoped
public class PublisherConverter implements Converter {

    @EJB
    PublisherEJB publisherController;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String publisherIdStr) {
        if (publisherIdStr == null || publisherIdStr.equals("null")) {
            return null;
        }
        
        Long publisherId = null;
//        System.out.println("!!! Publisher converted: " + publisherIdStr);
        try {
            publisherId = Long.valueOf(publisherIdStr);
        } catch (NumberFormatException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Publisher conversion error.",
                    "Invalid Publisher. Please, select a valid one.");
            throw new ConverterException(msg);
        }

        Publisher publisher = publisherController.findById(publisherId);
        if (publisher == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Publisher not found.",
                    "Publisher with ID=" + publisherId + " not found.");
            throw new ConverterException(msg);
        }
//        System.out.println("!!! Conversion result: " + publisher);
        return publisher;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object publisher) {
//        System.out.println("!!! Converted back to String: " + publisher);
        if (publisher != null && publisher instanceof Publisher) {
            return ((Publisher) publisher).getId().toString();
        } else {
            return null;
        }
    }
}
