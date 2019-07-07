package com.bookstore.domain;

import javax.persistence.*;

@Entity
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String shippingAddressName;
    private String shippingAddressStreet1;
    private String shippingAddresstreet2;
    private String shippingAddressCity;
    private String shippingAddressState;
    private String shippingAddressCountry;
    private String shippingAddressZipcode;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public String getShippingAddressStreet1() {
        return shippingAddressStreet1;
    }

    public void setShippingAddressStreet1(String shippingAddressStreet1) {
        this.shippingAddressStreet1 = shippingAddressStreet1;
    }

    public String getShippingAddresstreet2() {
        return shippingAddresstreet2;
    }

    public void setShippingAddresstreet2(String shippingAddresstreet2) {
        this.shippingAddresstreet2 = shippingAddresstreet2;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressState() {
        return shippingAddressState;
    }

    public void setShippingAddressState(String shippingAddressState) {
        this.shippingAddressState = shippingAddressState;
    }

    public String getShippingAddressCountry() {
        return shippingAddressCountry;
    }

    public void setShippingAddressCountry(String shippingAddressCountry) {
        this.shippingAddressCountry = shippingAddressCountry;
    }

    public String getShippingAddressZipcode() {
        return shippingAddressZipcode;
    }

    public void setShippingAddressZipcode(String shippingAddressZipcode) {
        this.shippingAddressZipcode = shippingAddressZipcode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
