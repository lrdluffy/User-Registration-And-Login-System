package com.strawhats.userregistrationloginsystem.controller;

import com.strawhats.userregistrationloginsystem.payload.*;
import com.strawhats.userregistrationloginsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.login(loginRequest.getUsername(),  loginRequest.getPassword());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

}
