package com.acme.yupanaapi.resource;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

public class SaveUserResource {
    //TODO: agregar el max length
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
}
