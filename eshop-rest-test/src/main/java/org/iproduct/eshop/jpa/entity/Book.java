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

package org.iproduct.eshop.jpa.entity;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@XmlRootElement
public class Book implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String title;

    private double percentage_off = 0;

     private double price;

     private List<String> authors = new ArrayList<>();

    private String isbn;

    private String book_url;

    private String description;

    private Publisher publisher;

    private boolean onsale = false;

    private String photo_url;

    public Book() {
    }

    public Book(String title, String author, Publisher publisher, double price) {
        this.price = price;
        this.authors = new ArrayList<>();
        this.authors.add(author);
        this.title = title;
        this.publisher = publisher;
    }

    public Book(String title, String[] authors, Publisher publisher, double price) {
        this.price = price;
        this.authors = Arrays.asList(authors);
        this.title = title;
        this.publisher = publisher;
    }

    public Book(String title, String isbn, String[] authors, String description,
            Publisher publisher, String photoUrl, String bookUrl, double price, double percentage_off) {
        this.percentage_off = percentage_off;
        this.price = price;
        this.authors = Arrays.asList(authors);
        this.isbn = isbn;
        this.title = title;
        this.book_url = bookUrl;
        this.description = description;
        this.publisher = publisher;
        this.photo_url = photoUrl;
        this.book_url = bookUrl;
    }

    public double getPercentage_off() {
        return this.percentage_off;
    }

    public void setPercentage_off(double percentage_off) {
        this.percentage_off = percentage_off;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookUrl() {
        return this.book_url;
    }

    public void setBookUrl(String book_url) {
        this.book_url = book_url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        System.out.println("!!! Setting book publisher: " + publisher);
        this.publisher = publisher;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOnsale() {
        return this.onsale;
    }

    public void setOnsale(boolean onsale) {
        this.onsale = onsale;
    }

    public String getPhotoUrl() {
        return this.photo_url;
    }

    public void setPhotoUrl(String photo_url) {
        this.photo_url = photo_url;
    }

//    @Override
//    public String toString() {
//        return "Book{" + "percentage_off=" + percentage_off + ", price=" + price + ", author=" + authors + ", isbn=" + isbn + ", title=" + title + ", book_url=" + book_url + ", description=" + description + ", publisher=" + publisher + ", id=" + id + ", onsale=" + onsale + ", photo_url=" + photo_url + '}';
//    }
}
