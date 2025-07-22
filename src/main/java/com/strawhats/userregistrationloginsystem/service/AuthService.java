package com.strawhats.userregistrationloginsystem.service;

import com.strawhats.userregistrationloginsystem.payload.JwtResponse;
import com.strawhats.userregistrationloginsystem.payload.UserDTO;

import javax.management.relation.RoleNotFoundException;

public interface AuthService {

    UserDTO signUp(UserDTO userDTO) throws RoleNotFoundException;

    JwtResponse login(String username, String password);

    String generateToken(String username);
}
