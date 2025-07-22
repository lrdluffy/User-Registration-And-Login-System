package com.strawhats.userregistrationloginsystem.service;

import com.strawhats.userregistrationloginsystem.exception.UserAlreadyExistsException;
import com.strawhats.userregistrationloginsystem.model.AppRole;
import com.strawhats.userregistrationloginsystem.model.Role;
import com.strawhats.userregistrationloginsystem.model.User;
import com.strawhats.userregistrationloginsystem.payload.JwtResponse;
import com.strawhats.userregistrationloginsystem.payload.RegisterRequest;
import com.strawhats.userregistrationloginsystem.payload.UserDTO;
import com.strawhats.userregistrationloginsystem.repository.RoleRepository;
import com.strawhats.userregistrationloginsystem.repository.UserRepository;
import com.strawhats.userregistrationloginsystem.security.jwt.JwtUtils;
import com.strawhats.userregistrationloginsystem.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public UserDTO signUp(RegisterRequest registerRequest) throws RoleNotFoundException {
        if (userRepository.existsUserByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username", registerRequest.getUsername());
        }

        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email", registerRequest.getEmail());
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException(String.format("Role : %s not found!", AppRole.ROLE_USER.name())));

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = modelMapper.map(savedUser, UserDTO.class);
        return savedUserDTO;
    }

    @Override
    public JwtResponse login(String username, String password) {
        Authentication authentication;
        try {
            authentication =   authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password))
            ;
        } catch (AuthenticationException e) {
            log.error("Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = generateToken(username);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(grantedAuthority ->
                        grantedAuthority.getAuthority()
                        ).toList();

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwtToken);
        jwtResponse.setUserId(userDetails.getUserId());
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setRoles(roles);

        return jwtResponse;
    }

    @Override
    public String generateToken(String username) {
        return jwtUtils.generateJwtTokenFromUserName(username);
    }
}
