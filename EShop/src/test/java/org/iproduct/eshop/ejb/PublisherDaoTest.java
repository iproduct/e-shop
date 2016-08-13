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

import java.io.File;
import java.util.List;
import javax.inject.Inject;
import org.iproduct.eshop.exceptions.IllegalOrphanException;
import org.iproduct.eshop.jpa.entity.Publisher;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@RunWith(Arquillian.class)
@Ignore
public class PublisherDaoTest {

    @Inject
    private PublisherDao publisherdao;

    private static final String WEBAPP_SRC = "src/main/webapp";
    private static final String RESOURCES_GF = "src/test/resources/resources-glassfish-embedded";
    private static final String RESOURCES_JBOSS = "src/test/resources/resources-jbossas-embedded";

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(PublisherDao.class, AbstractFacade.class, PublisherEJB.class)
                .addPackage(Publisher.class.getPackage())
                .addPackage(IllegalOrphanException.class.getPackage())
                //                .addAsManifestResource("test-persistence.xml", ArchivePaths.create("persistence.xml"))
                .addAsResource(new File(RESOURCES_JBOSS, "test-persistence.xml"), "META-INF/persistence.xml")
                //                .setWebXML("WEB-INF/web.xml")
//                .setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"))
                .addAsWebInfResource(new File(RESOURCES_JBOSS, "wildfly-ds.xml"), "wildfly-ds.xml")
//                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/glassfish-web.xml"), "glassfish-web.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void shouldBeDeployed() {
        Assert.assertNotNull(publisherdao);
    }
    
    @Test
    public void should_findAllPublishers_whenPublishersExist() {
        List<Publisher> result = publisherdao.findAll();
        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.size());
    }
    
}
