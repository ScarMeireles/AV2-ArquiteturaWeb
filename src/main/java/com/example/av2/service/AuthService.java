package com.example.av2.service;

import com.example.av2.dto.AuthResponse;
import com.example.av2.dto.LoginRequest;
import com.example.av2.dto.RegisterRequest;
import com.example.av2.model.Usuario;
import com.example.av2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já existe");
        }
        
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        
        var usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Usuario.Role.USER)
                .enabled(true)
                .build();
        
        usuarioRepository.save(usuario);
        
        var jwtToken = jwtService.generateToken(usuario);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .username(usuario.getUsername())
                .role(usuario.getRole().name())
                .build();
    }
    
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        var usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        var jwtToken = jwtService.generateToken(usuario);
        
        return AuthResponse.builder()
                .token(jwtToken)
                .username(usuario.getUsername())
                .role(usuario.getRole().name())
                .build();
    }
} 