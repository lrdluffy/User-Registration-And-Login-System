package com.strawhats.userregistrationloginsystem.controller;

import com.strawhats.userregistrationloginsystem.model.User;
import com.strawhats.userregistrationloginsystem.payload.UpdateUserRequest;
import com.strawhats.userregistrationloginsystem.payload.UserDTO;
import com.strawhats.userregistrationloginsystem.repository.UserRepository;
import com.strawhats.userregistrationloginsystem.security.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "User Related Apis", description = "User related apis : {Getting user profile, Updating user profile}")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    @Operation(summary = "Get User Profile", description = "Retrieve the authenticated user")
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDetails.getUserId());
        userDTO.setUsername(userDetails.getUsername());
        userDTO.setEmail(userDetails.getEmail());
        userDTO.setRoles(roles);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    @Operation(summary = "Update User Profile", description = "Update user details")
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            Authentication authentication,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User Object which replaces the old one.", required = true)
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                User user = userRepository.findUserByUsername(userDetails.getUsername()).get();

                user.setUsername(updateUserRequest.getUsername());
                user.setEmail(updateUserRequest.getEmail());
                user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

                User savedUser = userRepository.save(user);

                UserDTO savedUserDTO = modelMapper.map(savedUser, UserDTO.class);
                return new ResponseEntity<>(savedUserDTO, HttpStatus.OK);
    }
}
