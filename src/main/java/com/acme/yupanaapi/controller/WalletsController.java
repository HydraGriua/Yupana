package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.resource.SaveUserResource;
import com.acme.yupanaapi.resource.SaveWalletResource;
import com.acme.yupanaapi.resource.UserResource;
import com.acme.yupanaapi.resource.WalletResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WalletsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WalletService walletService;

    @Operation(summary = "Get wallet by sellerId",description = "Get wallet by given sellerId",tags = {"wallets"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get wallet by given sellerId",content =@Content(mediaType = "application/json") )
    })
    @GetMapping("/sellers/{sellerId}/wallets")
    public List<WalletResource> getWalletBySellerId(@PathVariable(name = "id") Long userId){
        return walletService.getAllBySellerId(userId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    /*
    @PostMapping("sellers/id={sellerId}/userId={userId}")
    public WalletResource createWallet()
    */
    private Wallet convertToEntity(SaveWalletResource resource) {
        return mapper.map(resource, Wallet.class);
    }

    private WalletResource convertToResource(Wallet entity) {
        return mapper.map(entity, WalletResource.class);
    }
}
