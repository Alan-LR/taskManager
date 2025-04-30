package com.example.taskManager.controller;

import com.example.taskManager.dtos.login.LoginRequest;
import com.example.taskManager.dtos.login.LoginResponse;
import com.example.taskManager.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private TokenService tokenService;

    public TokenController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = tokenService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
