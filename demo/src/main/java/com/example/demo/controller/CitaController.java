package com.example.demo.controller;

import com.example.demo.DTO.DashboardCitaDTO;
import com.example.demo.entity.Cita;
import com.example.demo.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.EstadoCita;


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

    // Buscar citas por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Cita> listarPorCliente(@PathVariable Long clienteId){
        return service.listarPorCliente(clienteId);
    }

    // Buscar citas por servicio
    @GetMapping("/servicio/{servicioId}")
    public List<Cita> listarPorServicio(@PathVariable Long servicioId){
        return service.listarPorServicio(servicioId);
    }

    // Buscar citas por cliente y estado
    @GetMapping("/cliente/{clienteId}/estado/{estado}")
    public List<Cita> listarPorClienteYEstado(
            @PathVariable Long clienteId,
            @PathVariable EstadoCita estado){
        return service.listarPorClienteYEstado(clienteId, estado);
    }

    @GetMapping("/estado/{estado}")
    public List<Cita> porEstado(@PathVariable EstadoCita estado){
        return service.listarPorEstado(estado);
    }

    @GetMapping("/buscar")
    public List<Cita> buscar(@RequestParam String texto){
        return service.buscar(texto);
    }



}

