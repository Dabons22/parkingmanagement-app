package com.parkingmanagement.demo.controller;

import com.parkingmanagement.demo.config.JwtUtil;
import com.parkingmanagement.demo.entities.AuthenticationRequest;
import com.parkingmanagement.demo.entities.StaticCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        if (StaticCredentials.USERNAME.equals(authenticationRequest.getUsername()) &&
                StaticCredentials.PASSWORD.equals(authenticationRequest.getPassword())) {
            String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt));
        } else {
            return ResponseEntity.status(401).body(new ErrorResponse("Incorrect username or password"));
        }
    }


    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }


    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
