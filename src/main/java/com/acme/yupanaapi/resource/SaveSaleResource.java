package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SaveSaleResource {

    @NotNull
    private Date saleDate;

    @NotNull
    private Date saleTime;

    @NotNull
    private Float netAmount;

    @NotNull
    private Float salePaid;

    @NotNull
    @Size(max = 15)
    private String paymentPay;

    @NotNull
    @Size(max = 150)
    private String description;
}
