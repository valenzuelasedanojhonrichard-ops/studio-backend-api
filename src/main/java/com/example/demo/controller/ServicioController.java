package com.example.demo.controller;

import com.example.demo.entity.Servicio;
import com.example.demo.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService service;

    @GetMapping
    public List<Servicio> listar(){
        return service.listar();
    }

    @PostMapping
    public Servicio guardar(@RequestBody Servicio s){
        return service.guardar(s);
    }

    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable Long id, @RequestBody Servicio s){
        return service.actualizar(id, s);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
