package com.example.demo.service;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.JwtUtil;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepo;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepo repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest req){

        Usuario user = repo.findByUsername(req.username())
                .orElseThrow();

        if(!encoder.matches(req.password(), user.getPassword())){
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        String rol = user.getRoles()
                .iterator()
                .next()
                .getNombre();

        return new LoginResponse(token,rol);
    }

    @Transactional
    public void eliminar(String username){
        repo.deleteByUsername(username);
    }
}
