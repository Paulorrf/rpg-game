package com.evo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.evo.dtos.AuthDto;
import com.evo.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private AuthService authService;
    
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> auth(@RequestBody AuthDto authDto, HttpServletResponse res) {
        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        authenticationManager.authenticate(userAuthenticationToken);
        
        var accessToken = authService.getTokens(authDto);

        res.setHeader("Authorization", "Bearer " + accessToken);

        Map<String, String> response = new HashMap<>();
        response.put("access_token", accessToken);

        return ResponseEntity.ok().body(response);
    }
}
