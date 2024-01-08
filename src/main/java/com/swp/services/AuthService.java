package com.swp.services;

import com.swp.cms.reqDto.AuthenticationRequest;
import com.swp.cms.reqDto.RegisterRequest;
import com.swp.cms.resDto.AuthenticationResponse;
import com.swp.config.JwtService;
import com.swp.entity.User;
import com.swp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    public User register(RegisterRequest request) {

        // Create the User object with the associated Role
        var user = User.builder()
                .display_name(request.getDisplayName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role_id(request.getRole_id())
                .created_date(LocalDateTime.now())
                .build();

        // Save the User object to persist it in the database
        return userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
