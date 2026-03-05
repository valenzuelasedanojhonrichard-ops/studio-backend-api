package com.example.demo.mapper;

import com.example.demo.DTO.DetalleVentaDTO;
import com.example.demo.DTO.VentaDTO;
import com.example.demo.entity.Venta;
import org.springframework.stereotype.Component;

@Component
public class VentaMapper {

    public VentaDTO toDTO(Venta v){

        VentaDTO dto = new VentaDTO();
        dto.setId(v.getId());
        dto.setFecha(v.getFecha());
        dto.setTotal(v.getTotal());

        dto.setDetalles(
                v.getDetalles()
                        .stream()
                        .map(d -> new DetalleVentaDTO(
                                d.getProducto().getNombre(),
                                d.getCantidad(),
                                d.getPrecio(),
                                d.getCantidad() * d.getPrecio()
                        ))
                        .toList()
        );

        return dto;
    }

}
