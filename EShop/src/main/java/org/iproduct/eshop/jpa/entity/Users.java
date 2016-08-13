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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 *
 * @author Trayan Iliev, IPT [http://iproduct.org]
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT p FROM Users p"),
    @NamedQuery(name = "Users.findById", query = "SELECT p FROM Users p WHERE p.id = :id"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT p FROM Users p WHERE p.firstname = :firstname"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT p FROM Users p WHERE p.lastname = :lastname"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT p FROM Users p WHERE p.email = :email"),
    @NamedQuery(name = "Users.findByAddress", query = "SELECT p FROM Users p WHERE p.address = :address"),
    @NamedQuery(name = "Users.findByCity", query = "SELECT p FROM Users p WHERE p.city = :city")})
public class Users implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L;
    
    @TableGenerator(name = "users_gen",
            table = "id_gen",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "users_id",
            allocationSize = 1
    )
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "users_gen")
    @Basic(optional = false)
    @Column(name = "ID", unique = true, updatable = false, insertable = true, nullable = false)
    protected Long id;
    
    @JoinTable(name = "USERS_GROUPS", joinColumns = {
        @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")}, inverseJoinColumns = {
        @JoinColumn(name = "GROUPS_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.EAGER)
    protected List<Groups> groups = new ArrayList<>();
    
    @Basic(optional = false)
    @Size(min=2, max=20, message="{user.firstname}")
    @Column(name = "FIRSTNAME")
    protected String firstname;
    
    @Basic(optional = false)
    @Size(min=2, max=20, message="{user.lastname}")
    @Column(name = "LASTNAME")
    protected String lastname;
    
    @Pattern(regexp = ".+@.+\\.[a-z]+", message= "{users.email}")
    @Size(min=5, max=50, message= "{user.email}")
    @Basic(optional = false)
    @Column(name = "EMAIL")
    protected String email;
    
    @Basic(optional = false)
    @Size(min=32, max=32, message= "{user.password}")
    @Column(name = "PASSWORD")
    protected String password;
    
    @Basic(optional = false)
    @Size(min=2, max=40, message= "{user.city}")
    @Column(name = "CITY")
    protected String city;
    
    @Basic(optional = false)
    @Size(min=2, max=80, message= "{user.address}")
    @Column(name = "ADDRESS")
    protected String address;

    public Users() {}
    
    public Users(Long id) {
        this.id = id;
    }
    
    public Users( 
            String firstName, 
            String lastName, 
            String email, 
            String address, 
            String city) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.address = address;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * XmlTransient for security reasons. 
     * @return password
     */
    @XmlTransient  
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groupssList) {
        this.groups = groupssList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Users other = (Users) obj;
        return Objects.equals(this.id, other.id);
    }

    
    @Override
    public String toString() {
        return "groups=" + groups + ", id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", address=" + address + ", city=" + city + ", password=" + password;
    }

}
