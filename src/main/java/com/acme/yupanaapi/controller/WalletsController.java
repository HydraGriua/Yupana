package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.Wallet;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.domain.service.WalletService;
import com.acme.yupanaapi.resource.SaveWalletResource;
import com.acme.yupanaapi.resource.UserWalletResource;
import com.acme.yupanaapi.resource.WalletResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class WalletsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all wallets by sellerId",description = "Get All wallets by given sellerId",tags = {"wallets"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all wallet by given sellerId",content =@Content(mediaType = "application/json") )
    })
    @GetMapping("/sellers/{sellerId}/wallets")
    public List<Wallet> getAllWalletBySellerId(@PathVariable(name = "sellerId") Long sellerId){
        return walletService.getAllBySellerId(sellerId);//.stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @Operation(summary = "Get wallet by Id",description = "Get wallet by given Id",tags = {"wallets"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get wallet by given Id",content =@Content(mediaType = "application/json") )
    })
    @GetMapping("/wallets/{walletId}")
    public UserWalletResource getWalletById(@PathVariable(name = "walletId") Long walletId){
        UserWalletResource resource = new UserWalletResource();
        Wallet wallet = walletService.getWalletById(walletId);
        resource.setWallet(wallet);
        resource.setUser(userService.getUserById(wallet.getIdOfUser()));
        return resource;
    }

    @Operation(summary = "Create Wallet",description = "Create Wallet by user and seller",tags = {"wallets"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Wallet by user and seller",content =@Content(mediaType = "application/json"))
    })
    @PostMapping("users/{userId}/sellers/{sellerId}/wallets")
    public WalletResource createWallet(@PathVariable(name = "sellerId") Long sellerId, @PathVariable(name = "userId") Long userId, @Valid @RequestBody SaveWalletResource resource){
        return convertToResource(walletService.createWallet(convertToEntity(resource), sellerId, userId));
    }

    @Operation(summary = "Update Wallet",description = "Update Wallet By Id and Seller Id",tags = {"wallets"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Wallet By Id and Seller Id",content =@Content(mediaType = "application/json"))
    })
    @PutMapping("/sellers/{sellerId}/wallets/{walletId}")
    public WalletResource updateWallet(@PathVariable(name= "sellerId") Long sellerId, @PathVariable(name = "walletId") Long walletId, @Valid @RequestBody SaveWalletResource resource){
        return  convertToResource(walletService.updateWallet(convertToEntity(resource),walletId,sellerId));
    }

    @Operation(summary = "Delete Wallet",description = "Delete Wallet By Id",tags = {"wallets"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Wallet By Id",content =@Content(mediaType = "application/json"))
    })
    @DeleteMapping("/wallets/{walletId}")
    public ResponseEntity<?> deleteWallet(@PathVariable(name ="walletId") Long walletId){
        return walletService.deleteWallet(walletId);
    }

    private Wallet convertToEntity(SaveWalletResource resource) {
        return mapper.map(resource, Wallet.class);
    }

    private WalletResource convertToResource(Wallet entity) {
        return mapper.map(entity, WalletResource.class);
    }
}
