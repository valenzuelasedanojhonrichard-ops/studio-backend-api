package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaDTO {

        private Long id;
        private LocalDateTime fecha;
        private Double total;
        private String estado;
        private String cliente;

        private List<DetalleVentaDTO> detalles;

}
