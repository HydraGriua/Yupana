package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SaleResource {
    private Long id;
    private Date saleDate;
    private Date saleTime;
    private Float netAmount;
    private Float salePaid;
    private String paymentPay;
    private String description;
}
