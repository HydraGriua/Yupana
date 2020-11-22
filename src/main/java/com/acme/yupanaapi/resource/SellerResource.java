package com.acme.yupanaapi.resource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerResource {
	private Integer id;
    private String actualPassword;
    private String oldPassword;
    private String storeAdress;
    private String businessName;
    private String email;
}
