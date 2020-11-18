package com.acme.yupanaapi.resource;

import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.model.Wallet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWalletResource {
    private Wallet wallet;
    private User user;
}
