package com.acme.yupanaapi.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String documentType;

    @NotNull
    private String documentNumber;

    @NotNull
    private String name;

    @NotNull
    private String firstLastname;

    @NotNull
    private String secondLastname;

    @NotNull
    private String cellphone;

    @Lob
    @NotNull
    private String description;

    public User(@NotNull String documentType, @NotNull String documentNumber, @NotNull String name, @NotNull String firstLastname, @NotNull String secondLastname, @NotNull String cellphone, @NotNull String description) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.name = name;
        this.firstLastname = firstLastname;
        this.secondLastname = secondLastname;
        this.cellphone = cellphone;
        this.description = description;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDocumentType() {
        return documentType;
    }

    public User setDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public User setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstLastname() {
        return firstLastname;
    }

    public User setFirstLastname(String firstLastname) {
        this.firstLastname = firstLastname;
        return this;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public User setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
        return this;
    }

    public String getCellphone() {
        return cellphone;
    }

    public User setCellphone(String cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }
}
