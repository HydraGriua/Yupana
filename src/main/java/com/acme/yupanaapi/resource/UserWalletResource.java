package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.model.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserWalletResource {
    private Long walletId;
    private Long userId;
    private Long flowId;
    private Long subscriptionId;
    private String documentNumber;
    private String name;
    private String firstLastname;
    private String secondLastname;
    private String cellphone;
    private Date creationDate;
    private Float maintenancePrice;
    private Float balance;
    private String state;
    private String type;
    private Date deadlineDate;
    private Date lastTransactionDate;
    private Float currentInterestRate;
    private int currentRatePeriod;
    private int currentCapitalization;
    private String currentRateType;
    private Float creditLine;
    private Float currentCreditLine;
    private Float totalDebt;
    private Float sAmount;
    private Date sCreationDate;
    private Date sExpirationDate;
    private String sType;
}
