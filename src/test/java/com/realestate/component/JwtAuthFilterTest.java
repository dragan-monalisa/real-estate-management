package com.realestate.component;

import com.realestate.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternalTest_NoAuthHeader() throws ServletException, IOException {

        // given
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternalTest_InvalidAuthHeader() throws ServletException, IOException {

        // given
        when(request.getHeader("Authorization")).thenReturn("invalid_header");

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void doFilterInternalTest_ValidTokenAuthenticationSet() throws ServletException, IOException {

        // given
        when(request.getHeader("Authorization")).thenReturn("Bearer " + "random_jwt");
        when(jwtService.extractUsername("random_jwt")).thenReturn("test@email.com");
        when(userDetailsService.loadUserByUsername("test@email.com")).thenReturn(userDetails);
        when(jwtService.isTokenValid("random_jwt", userDetails)).thenReturn(true);

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(jwtService).extractUsername("random_jwt");
        verify(userDetailsService).loadUserByUsername("test@email.com");
        verify(jwtService).isTokenValid("random_jwt", userDetails);
        verify(filterChain).doFilter(request, response);

        SecurityContext securityContext = SecurityContextHolder.getContext();

        UsernamePasswordAuthenticationToken authToken =
                (UsernamePasswordAuthenticationToken) securityContext.getAuthentication();

        assertThat(userDetails).isEqualTo(authToken.getPrincipal());
        assertThat(userDetails.getAuthorities()).isEqualTo(authToken.getAuthorities());
    }

    @Test
    void doFilterInternalTest_ValidTokenInvalidAuthentication() throws ServletException, IOException {

        // given
        when(request.getHeader("Authorization")).thenReturn("Bearer " + "random_jwt");
        when(jwtService.extractUsername("random_jwt")).thenReturn("test@email.com");
        when(userDetailsService.loadUserByUsername("test@email.com")).thenReturn(userDetails);
        when(jwtService.isTokenValid("random_jwt", userDetails)).thenReturn(false);

        // when
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(jwtService).extractUsername("random_jwt");
        verify(userDetailsService).loadUserByUsername("test@email.com");
        verify(jwtService).isTokenValid("random_jwt", userDetails);
        verify(filterChain).doFilter(request, response);

        SecurityContext securityContext = SecurityContextHolder.getContext();

        assertThat(securityContext.getAuthentication()).isNull();
    }

}
