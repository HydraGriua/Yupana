package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserResource {
    private Long id;
    private String documentType;
    private String documentNumber;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String cellphone;
    private String description;
}
