/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (o) 2003-2014 IPT - Intellectual Products & Technologies.
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@Entity
@Table(name = "ORDERS")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.orderStatus = :status"),
    @NamedQuery(name = "Orders.findByCustomerId", query = "SELECT o FROM Orders o WHERE o.customer.id = :id"),
    @NamedQuery(name = "Orders.findByAmount", query = "SELECT o FROM Orders o WHERE o.amount = :amount"),
    @NamedQuery(name = "Orders.findByDateCreated", query = "SELECT o FROM Orders o WHERE o.dateCreated = :dateCreated")})
@XmlRootElement(name = "Orders")
public class Orders implements Serializable, Identifiable{
    
    private static final long serialVersionUID = 1L;
    
    @TableGenerator(name = "order_gen",
            table = "id_gen",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "order_id",
            allocationSize = 1
    )
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_gen")
    @Basic(optional = false)
    @Column(name = "ID", unique = true, updatable = false, insertable = true, nullable = false)
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Basic(optional = false)
    @DecimalMin(value="0.1", message="{o.order.amount}")
    @Column(name = "AMOUNT")
    private double amount;
    
    @Basic
    @Column(name = "STATUS", unique = false, updatable = true, insertable = true, nullable = true)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Customer customer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public Orders() {
    }

    public Orders(Long id) {
        this.id = id;
    }

    public Orders(Long id, double amount, Date dateCreated) {
        this.id = id;
        this.amount = amount;
        this.dateCreated = dateCreated;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Orders other = (Orders) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Orders{" + "id=" + id + ", dateCreated=" + dateCreated + ", amount=" + amount + ", orderStatus=" + orderStatus + ", customer=" + customer + ", orderDetails=" + orderDetails + '}';
    } 

}
