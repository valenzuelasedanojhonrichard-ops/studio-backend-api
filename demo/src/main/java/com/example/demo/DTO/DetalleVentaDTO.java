package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDTO {

    private String producto;
    private Integer cantidad;
    private Double precio;
    private Double subtotal;
}
