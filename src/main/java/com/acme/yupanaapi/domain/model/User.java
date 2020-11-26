package com.acme.yupanaapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

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

    @NotNull
    private String description;
}
