package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveUserResource {

    @NotNull
    @Size(max = 50)
    private String documentType;

    @NotNull
    @Size(max = 8)
    private String documentNumber;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 30)
    private String firstLastname;

    @NotNull
    @Size(max = 30)
    private String secondLastname;

    @NotNull
    @Size(max = 12)
    private String cellphone;

    @NotNull
    @Size(max = 200)
    private String description;
    
    private SaveUserResource saveUserResource;
}