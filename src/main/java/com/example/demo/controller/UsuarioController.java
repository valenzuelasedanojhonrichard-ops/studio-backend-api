package com.example.demo.controller;


import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepo usuarioRepo;

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioRepo.findAll();
    }
}
