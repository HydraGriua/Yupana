package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WalletResource {
    private Integer id;
    private String type;
    private String currencyType;
    private Date creationDate;
    private String state;
    private Float balance;
    private Float maintenancePrice;
    private Long idOfUser;
}
