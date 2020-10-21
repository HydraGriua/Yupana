package com.acme.yupanaapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String actualPassword;

    @NotNull
    private String oldPassword;

    @NotNull
    private String storeAdress;

    @NotNull
    private String businessName;

    @NotNull
    private String email;

    public Seller(@NotNull String actualPassword, @NotNull String oldPassword, @NotNull String storeAdress, @NotNull String businessName, @NotNull String email) {
        this.actualPassword = actualPassword;
        this.oldPassword = oldPassword;
        this.storeAdress = storeAdress;
        this.businessName = businessName;
        this.email = email;
    }

    public Seller(){}

    public Long getId() {
        return id;
    }

    public Seller setId(Long id) {
        this.id = id;
        return this;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public Seller setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public Seller setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getStoreAdress() {
        return storeAdress;
    }

    public Seller setStoreAdress(String storeAdress) {
        this.storeAdress = storeAdress;
        return this;
    }

    public String getBusinessName() {
        return businessName;
    }

    public Seller setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Seller setEmail(String email) {
        this.email = email;
        return this;
    }
    /*
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;
    */
}
