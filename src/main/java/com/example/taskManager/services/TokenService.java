package com.example.taskManager.services;

import com.example.taskManager.dtos.login.LoginRequest;
import com.example.taskManager.dtos.login.LoginResponse;
import com.example.taskManager.entities.role.Role;
import com.example.taskManager.entities.users.User;
import com.example.taskManager.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private JwtEncoder jwtEncoder;

    private UserRepository repository;

    private BCryptPasswordEncoder passwordEncoder;

    public TokenService(JwtEncoder jwtEncoder,
                        UserRepository repository,
                        BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder, User user){
        return passwordEncoder.matches(loginRequest.password(), user.getPassword());
    }

    public LoginResponse login(LoginRequest loginRequest){
        Optional<User> existUser = repository.findByEmail(loginRequest.email());
        User user = existUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user or password is invalid!"));

        if (!isLoginCorrect(loginRequest, passwordEncoder, user)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(""));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }
}
