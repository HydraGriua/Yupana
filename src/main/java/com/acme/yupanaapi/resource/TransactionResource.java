package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class TransactionResource {
    private Integer id;
    private String transactionName;
    private Date transactionDate;
    private Float amount;
    private Float interestRate;
    private int capitalization;
    private String currencyType;
    private int ratePeriod;
    private String rateType;
}
