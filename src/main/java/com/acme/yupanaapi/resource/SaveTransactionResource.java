package com.acme.yupanaapi.resource;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class SaveTransactionResource {

    @NotNull
    @Size(max = 30)
    private String transactionName;

    @NotNull
    private Date transactionDate;

    @NotNull
    private Float amount;

    @NotNull
    private Float interestRate;

    @NotNull
    private int capitalization;

    @NotNull
    @Size(max = 30)
    private String currencyType;

    @NotNull
    private int ratePeriod;

    @NotNull
    @Size(max = 30)
    private String rateType;
}
