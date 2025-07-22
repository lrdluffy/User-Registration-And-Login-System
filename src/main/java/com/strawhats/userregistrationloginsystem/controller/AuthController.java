package com.strawhats.userregistrationloginsystem.controller;

import com.strawhats.userregistrationloginsystem.payload.*;
import com.strawhats.userregistrationloginsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

@Tag(name = "Auth Related Apis", description = "Auth related apis : {Register, login}")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Couldn't register the user")
    })
    @Operation(summary = "Register User", description = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Register object which contains the user's details who requested registration", required = true)
            @RequestBody RegisterRequest registerRequest
    ) {
        UserDTO registeredUserDTO = null;
        try {
            registeredUserDTO = authService.signUp(registerRequest);
        } catch (RoleNotFoundException e) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Could not register the user");
            apiResponse.setSuccess(false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(registeredUserDTO, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success")
    })
    @Operation(summary = "Login User", description = "Authenticate user & return token")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Login object which contains the user's details who requested login", required = true)
            @RequestBody LoginRequest loginRequest
    ) {
        JwtResponse jwtResponse = authService.login(loginRequest.getUsername(),  loginRequest.getPassword());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

}
