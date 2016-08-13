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
package org.iproduct.eshop.mockito;

import javax.persistence.EntityManager;
import org.iproduct.eshop.ejb.PublisherEJB;
import org.iproduct.eshop.jpa.entity.Publisher;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@RunWith(MockitoJUnitRunner.class)
public class PublisherEJBTest {

    @Mock(name = "database")
    private EntityManager em;

//    @Spy
//    private UserProvider userProvider = new ConsumerUserProvider();
    @InjectMocks
    private PublisherEJB publisherController;

    @Test
    public void shouldDoSomething() {
        Publisher dummyPublisher = mock(Publisher.class);
        when(em.find(Publisher.class, 1L)).thenReturn(dummyPublisher);
        Publisher result = publisherController.findById(1L);
        verify(em).find(Publisher.class, 1L);
        // did it return the result list of the named query?
        assertSame(dummyPublisher, result);
    }
}

