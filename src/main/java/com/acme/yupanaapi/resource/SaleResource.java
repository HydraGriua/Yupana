package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaleResource {
    private int id;
    private Date saleDate;
    private Date saleTime;
    private Float netAmount;
    private Float salePaid;
    private String paymentPay;
    private String description;
}
