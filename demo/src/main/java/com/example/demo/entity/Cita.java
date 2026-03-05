package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nombre del cliente
    @Column(nullable = false, length = 100)
    private String clienteNombre;

    // servicio: uñas, corte, tinte, etc
    @Column(nullable = false, length = 100)
    private String servicio;

    // fecha y hora de la cita
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // precio del servicio
    private Double precio;

    // estado de la cita
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado = EstadoCita.RESERVADA;

    private Integer duracionMinutos;
}
