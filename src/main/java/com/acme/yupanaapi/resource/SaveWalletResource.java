package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SaveWalletResource {
    @NotNull
    @Size(max = 30)
    private String type;

    @NotNull
    @Size(max = 30)
    private String currencyType;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date creationDate;

    @NotNull
    @Size(max = 30)
    private String state;

    @NotNull
    private Float balance;

    @NotNull
    private Float maintenancePrice;
}
