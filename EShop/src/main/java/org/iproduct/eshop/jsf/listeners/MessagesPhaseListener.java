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
package org.iproduct.eshop.jsf.listeners;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.jboss.logging.Logger;

/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
public class MessagesPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    // The phase where the listener is going to be called
    private final PhaseId phaseId = PhaseId.RENDER_RESPONSE;
    private final Logger logger = Logger.getLogger(MessagesPhaseListener.class.getSimpleName());

    @Override
    public void beforePhase(PhaseEvent event) {
        List<FacesMessage> messages = event.getFacesContext().getMessageList();
        messages.stream().forEach(m -> {
            logger.log(Logger.Level.INFO, m.getSummary() + " : " + m.getDetail());
        });
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        // Do nothing here	
    }

    @Override
    public PhaseId getPhaseId() {
        return phaseId;
    }

    private void processViewTree(UIComponent component) {
        // Go to every child
        for (UIComponent child : component.getChildren()) {
            // Display component ID and its type
            System.out.println("+ " + child.getId() + " [" + child.getClass() + "]");

            // If component has a given ID then check if you can hide it
            if ("dummy-text-id".equals(child.getId())) {
                // Get the input text
                HtmlInputText inputText = (HtmlInputText) child;
                // Get input text value
                String inputTextValue = (String) inputText.getValue();

                // Hide the field when its value match the "hide me!" string
                if ("hide me!".equals(inputTextValue)) {
                    inputText.setRendered(false);
                }

                // Disable the field
                if ("disable me!".equals(inputTextValue)) {
                    inputText.setReadonly(true);
                }

            }

            // Process next node
            processViewTree(child);
        }
    }

}
