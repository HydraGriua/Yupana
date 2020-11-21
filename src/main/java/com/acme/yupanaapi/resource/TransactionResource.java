package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionResource {
    private int id;
    private String transactionName;
    private Date transactionDate;
    private Float amount;
    private Float interestRate;
    private int capitalization;
    private String currencyType;
    private int ratePeriod;
    private String rateType;
}
