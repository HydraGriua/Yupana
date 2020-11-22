package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResource {
    private int id;
    private String documentType;
    private String documentNumber;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String cellphone;
    private String description;
}
