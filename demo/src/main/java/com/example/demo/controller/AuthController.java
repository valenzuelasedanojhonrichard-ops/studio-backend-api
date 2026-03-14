package com.example.demo.controller;

import com.example.demo.DTO.LoginRequest;
import com.example.demo.DTO.LoginResponse;
import com.example.demo.JwtUtil;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepo;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final UsuarioRepo usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody Usuario usuario){

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepo.save(usuario);

        return "Usuario creado";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req){

        Usuario user = usuarioRepo.findByUsername(req.username())
                .orElseThrow();

        if(!passwordEncoder.matches(req.password(), user.getPassword())){
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> eliminar(@PathVariable String username){
        service.eliminar(username);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(){
        return usuarioRepo.findAll();
    }
}
