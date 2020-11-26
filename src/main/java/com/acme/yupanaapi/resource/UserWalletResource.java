package com.acme.yupanaapi.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

import com.acme.yupanaapi.domain.model.Subscription;
import com.acme.yupanaapi.domain.model.Transaction;

@Getter
@Setter
public class UserWalletResource {
    private int walletId;  // 
    private int userId;   // 
    private int flowId;// 
    private int subscriptionId; //
    private String documentNumber;
    private String name; //
    private String firstLastname;
    private String secondLastname;
    private String cellphone;
    private String description;
    private Date creationDate; // creacion del monedero 
    private Float maintenancePrice;
    private Float balance; // inutil ---- ???
    private String state; // activo/inactivo 
    private String type; // cliente basico, potente etc
    private Date deadlineDate; // vencimiento flow 
    private Date lastTransactionDate; // ultimo dia de transaccion 
    private Float currentInterestRate; // Tasa porcentaje actual 
    private int currentRatePeriod; // periodo de tasa 
    private int currentCapitalization; // periodo capitalizacion 
    private String rateType; // TEA etc
    private Float creditLine; // Inicial que te dan linea de credito 
    private Float currentCreditLine; // Actualizacion del la linea de credito 
    private String currencyType;
    private String capitalizationType;
    private Float totalDebt; // total de deuda 
    private Float sAmount; // delivery costo 
    private Date sCreationDate; // creacion del delivery 
    private Date sExpirationDate; // expiracion del delivery
    private String sType; // // tipo de delivery
    private Transaction transaction;
    private Subscription subscription;
    private String userDescription;
    private String transactionName;

}
