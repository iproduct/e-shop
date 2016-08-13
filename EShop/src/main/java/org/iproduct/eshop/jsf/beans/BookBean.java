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
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.iproduct.eshop.ejb.BookEJB;
import org.iproduct.eshop.ejb.PublisherEJB;
import org.iproduct.eshop.exceptions.PreexistingEntityException;
import org.iproduct.eshop.jpa.entity.Book;
import org.iproduct.eshop.jpa.entity.Publisher;
import org.iproduct.eshop.jsf.utils.JsfUtils;

/**
 *
 * @author admin
 */
@Named(value = "bookBean")
@ConversationScoped
public class BookBean implements Serializable {

    @Inject
    private Conversation conversation;

    @Inject
    @New(org.iproduct.eshop.jpa.entity.Book.class)
    private Book book;

    @Inject
    @New(org.iproduct.eshop.jpa.entity.Publisher.class)
    private Publisher publisher;

    private boolean renderPublisherForm = false;

    @EJB
    private BookEJB bookController;

    @EJB
    PublisherEJB publisherController;

    /**
     * Creates a new instance of Book
     */
    public BookBean() {
    }

    @PostConstruct
    public void init() {
        book.getAuthors().add("");
    }

    public Book getBook() {
        return book;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public boolean isRenderPublisherForm() {
        return renderPublisherForm;
    }

    // Business methods
    public String addBook() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        try {
            bookController.create(book);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(BookBean.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtils.addErrorMessage(ex.getMessage(), ex.getMessage());
            return null;
        }
        return "index";
    }

    public String addAuthor() {
        if (conversation.getId() == null) {
            conversation.begin();
        }
        System.out.println("!!!! addAuthor called:" + book.getAuthors().size());
        book.getAuthors().add("");
        return null;
    }

    public String showPublisherForm() {
        if (conversation.getId() == null) {
            conversation.begin();
        }
        renderPublisherForm = true;
        return null;
    }

    public String addPublisher() {
        if (publisher.getName() != null && publisher.getUrl() != null) {
            publisher = publisherController.edit(publisher);
            book.setPublisher(publisher);

            UIInput comp = (UIInput) FacesContext.getCurrentInstance().getViewRoot().
                    findComponent("add-book-form:publisher");
            comp.setSubmittedValue(publisher);

            renderPublisherForm = false;
        }
        return null;
    }

    public String cancelPublisher() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> it = context.getMessages();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        renderPublisherForm = false;
        return null;
    }

    public void publisherNameChanged(ValueChangeEvent ev) {
        ((UIInput) ev.getComponent()).updateModel(FacesContext.getCurrentInstance());
    }

    public void publisherUrlChanged(ValueChangeEvent ev) {
        ((UIInput) ev.getComponent()).updateModel(FacesContext.getCurrentInstance());
    }

}
