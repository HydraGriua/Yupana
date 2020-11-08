package com.acme.yupanaapi.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SaveSellerResource {
	@NotNull
	@Size(max = 20)
    private String actualPassword;
	@NotNull
	@Size(max = 20)
    private String oldPassword;
	@NotNull
	@Size(max = 50)
    private String storeAdress;
	@NotNull
	@Size(max = 50)
    private String businessName;
	@NotNull
	@Size(max = 50)
    private String email;
}
