package com.parkingmanagement.demo.controller;

import com.parkingmanagement.demo.config.JwtUtil;
import com.parkingmanagement.demo.entities.AuthenticationRequest;
import com.parkingmanagement.demo.entities.StaticCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StaticCredentials staticCredentials;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        if (StaticCredentials.USERNAME.equals(authenticationRequest.getUsername()) &&
                StaticCredentials.PASSWORD.equals(authenticationRequest.getPassword())) {
            return jwtUtil.generateToken(authenticationRequest.getUsername());
        } else {
            throw new BadCredentialsException("Incorrect username or password");
        }
    }
}
