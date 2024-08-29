package com.parkingmanagement.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
    }

    @Test
    void givenUsername_whenGenerateToken_thenTokenIsGenerated() {
        // Arrange
        String username = "testUser";

        // Act
        String token = jwtUtil.generateToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ")); // Basic check to ensure token starts with JWT prefix
    }

    @Test
    void givenToken_whenExtractUsername_thenUsernameIsExtracted() {
        // Arrange
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        // Act
        String extractedUsername = jwtUtil.extractUsername(token);

        // Assert
        assertEquals(username, extractedUsername);
    }

    @Test
    void givenToken_whenExtractExpiration_thenExpirationDateIsExtracted() {
        // Arrange
        String token = jwtUtil.generateToken("testUser");
        Date expirationDate = jwtUtil.extractExpiration(token);

        // Act & Assert
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date())); // Ensure expiration date is in the future
    }

    @Test
    void givenValidToken_whenValidateToken_thenTokenIsValid() {
        // Arrange
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        // Act
        Boolean isValid = jwtUtil.validateToken(token, username);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void givenTokenWithIncorrectUsername_whenValidateToken_thenTokenIsInvalid() {
        // Arrange
        String username = "testUser";
        String token = jwtUtil.generateToken(username);
        String incorrectUsername = "wrongUser";

        // Act
        Boolean isValid = jwtUtil.validateToken(token, incorrectUsername);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void givenExpiredToken_whenValidateToken_thenTokenIsInvalid() {
        // Arrange
        String username = "testUser";
        JwtUtil expiredJwtUtil = new JwtUtil() {
            @Override
            public Date extractExpiration(String token) {
                return new Date(System.currentTimeMillis() - 1000 * 60 * 60); // 1 hour ago
            }
        };
        String token = expiredJwtUtil.generateToken(username);

        // Act
        Boolean isValid = expiredJwtUtil.validateToken(token, username);

        // Assert
        assertFalse(isValid);
    }
}
