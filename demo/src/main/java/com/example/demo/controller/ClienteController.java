package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import com.example.demo.entity.Cliente;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public List<Cliente> listar(){
        return service.listar();
    }

    @PostMapping
    public Cliente guardar(@RequestBody Cliente c){
        return service.guardar(c);
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente c){
        return service.actualizar(id, c);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<Cliente> buscar(@RequestParam String texto){
        return service.buscar(texto);
    }

}
