package com.example.av2.service;

import com.example.av2.dto.AuthResponse;
import com.example.av2.dto.LoginRequest;
import com.example.av2.dto.RegisterRequest;
import com.example.av2.model.Usuario;
import com.example.av2.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtService jwtService;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @InjectMocks
    private AuthService authService;
    
    private Usuario usuario;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    
    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .role(Usuario.Role.USER)
                .enabled(true)
                .build();
        
        registerRequest = new RegisterRequest("testuser", "password123", "test@example.com");
        loginRequest = new LoginRequest("testuser", "password123");
    }
    
    @Test
    void register_ShouldCreateNewUser_WhenValidRequest() {
        // Arrange
        when(usuarioRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
        when(usuarioRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(jwtService.generateToken(any(Usuario.class))).thenReturn("jwtToken");
        
        // Act
        AuthResponse response = authService.register(registerRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("USER", response.getRole());
        
        verify(usuarioRepository).save(any(Usuario.class));
        verify(jwtService).generateToken(any(Usuario.class));
    }
    
    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        // Arrange
        when(usuarioRepository.existsByUsername(registerRequest.getUsername())).thenReturn(true);
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    
    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        // Arrange
        when(usuarioRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
        when(usuarioRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    
    @Test
    void login_ShouldReturnToken_WhenValidCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn("jwtToken");
        
        // Act
        AuthResponse response = authService.login(loginRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("USER", response.getRole());
        
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(usuario);
    }
    
    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        verify(jwtService, never()).generateToken(any(Usuario.class));
    }
} 