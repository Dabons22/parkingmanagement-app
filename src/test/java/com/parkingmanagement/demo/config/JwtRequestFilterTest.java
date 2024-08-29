package com.parkingmanagement.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidToken_whenDoFilterInternal_thenAuthenticationIsSet() throws Exception {
        // Arrange
        String token = "validToken";
        String username = "user";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(true);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
        assertEquals(username, authentication.getName());
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void givenInvalidToken_whenDoFilterInternal_thenAuthenticationIsNotSet() throws Exception {
        // Arrange
        String token = "invalidToken";
        String username = "user";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(false);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void givenNoToken_whenDoFilterInternal_thenAuthenticationIsNotSet() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void givenNoBearerToken_whenDoFilterInternal_thenAuthenticationIsNotSet() throws Exception {
        // Arrange
        String token = "someToken";
        when(request.getHeader("Authorization")).thenReturn(token);

        // Act
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
        verify(chain, times(1)).doFilter(request, response);
    }
}
