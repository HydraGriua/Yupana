package com.acme.yupanaapi.controller;

import com.acme.yupanaapi.domain.model.User;
import com.acme.yupanaapi.domain.service.UserService;
import com.acme.yupanaapi.resource.SaveUserResource;
import com.acme.yupanaapi.resource.UserResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsersController {

    //Mapeador de entidad con resource
    @Autowired
    private ModelMapper mapper;

    //Services
    @Autowired
    private UserService userService;




    ////////////////////////////////////////////////////////////
    //Metodos crud para las llamadas

    @Operation(summary = "Get user",description = "Get user by given id",tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user by given id",content =@Content(mediaType = "application/json") )
    })
    @GetMapping("/users/{id}")
    public UserResource getUserById(@PathVariable(name = "id") Long userId){
        return convertToResource(userService.getUserById(userId));
    }

    @Operation(summary = "Get user",description = "Get user by given document number",tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user by given document number",content =@Content(mediaType = "application/json") )
    })
    @GetMapping("/users/dni={dni}")
    public UserResource getUserByDocumentNumber(@PathVariable(name = "dni") String dni){
        return convertToResource(userService.getUserByDocumentNumber(dni));
    }

    @Operation(summary = "Create user",description = "Create user",tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create user",content =@Content(mediaType = "application/json") )
    })
    @PostMapping("/users")
    public UserResource createUser(@Valid @RequestBody SaveUserResource resource){
        return convertToResource(userService.createUser(convertToEntity(resource)));
    }

    @Operation(summary = "Update user",description = "Update user by given id",tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update user by given id",content =@Content(mediaType = "application/json") )
    })
    @PutMapping("/users/{id}")
    public UserResource updateUser(@PathVariable(name = "id") Long userId, @Valid @RequestBody SaveUserResource resource){
        return convertToResource(userService.updateUser(userId,convertToEntity(resource)));
    }

    @Operation(summary = "Delete user",description = "Delete user by given id",tags = {"users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete user by given id",content =@Content(mediaType = "application/json") )
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name="id") Long userId){
        return userService.deleteUser(userId);
    }
    /////////////////////////////////////////////////////////////////////////


    //Convertidores ->>>>> MUCHO OJO (analizar su funcionamiento)

    private User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }

    private UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }
}
