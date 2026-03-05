package com.example.demo.controller;

import com.example.demo.DTO.DashboardCitaDTO;
import com.example.demo.entity.Cita;
import com.example.demo.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CitaController {

    private final CitaService service;

    @GetMapping
    public List<Cita> listar(){
        return service.listarActivas();
    }

    @GetMapping("/activas")
    public List<Cita> activas(){
        return service.listarActivas();
    }

    @PostMapping
    public Cita guardar(@RequestBody Cita c){
        return service.guardar(c);
    }

    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable Long id, @RequestBody Cita c){
        return service.actualizar(id, c);
    }

    @PutMapping("/{id}/cancelar")
    public Cita cancelar(@PathVariable Long id){
        return service.cancelar(id);
    }

    @PutMapping("/{id}/atender")
    public Cita atender(@PathVariable Long id){
        return service.atender(id);
    }

    @GetMapping("/dashboard")
    public DashboardCitaDTO dashboard(){
        return service.resumenCitas();
    }
}

