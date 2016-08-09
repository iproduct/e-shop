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
package org.iproduct.eshop.jsf.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;


/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@FacesValidator("myUrlValidator")
public class MyUrlValidator implements Validator {
    private UrlValidator urlValidator = new UrlValidator();

    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object url)
            throws ValidatorException {
        
        if (url == null)
        {
            return;
        }

        //if URL is invalid
        if (!urlValidator.isValid(url.toString())) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "URL validation error.",
                            "Invalid URL format.");
            throw new ValidatorException(msg);
        }
    }
}
