package com.example.demo.controller;

import com.example.demo.entity.Producto;
import com.example.demo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin("*")

public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return service.guardar(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto p){
        return service.actualizar(id, p);
    }

    @GetMapping("/buscar")
    public List<Producto> buscar(@RequestParam String texto){
        return service.buscar(texto);
    }



}
