package com.example.demo.controller;

import com.example.demo.DTO.DashboardDTO;
import com.example.demo.DTO.ReporteVentaDTO;
import com.example.demo.DTO.ReporteVentaResponseDTO;
import com.example.demo.DTO.VentaDTO;
import com.example.demo.entity.EstadoVenta;
import com.example.demo.entity.Venta;
import com.example.demo.mapper.VentaMapper;
import com.example.demo.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VentaController {

    private final VentaService service;
    private final VentaMapper mapper;

    @GetMapping
    public List<VentaDTO> listar(){
        return service.listar()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @PostMapping
    public VentaDTO guardar(@RequestBody Venta v){
        Venta guardada = service.guardar(v);
        return mapper.toDTO(guardada);
    }

    @PutMapping("/{id}/cancelar")
    public VentaDTO cancelar(@PathVariable Long id){
        return mapper.toDTO(service.cancelar(id));
    }

    @GetMapping("/reporte")
    public ReporteVentaResponseDTO reportePorFecha(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin
    ){
        return service.reportePorFechas(inicio, fin);
    }

    @GetMapping("/reporte/estado")
    public List<VentaDTO> reportePorEstado(
            @RequestParam EstadoVenta estado
    ){
        return service.reportePorEstado(estado)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> descargarPDF(
            @RequestParam LocalDateTime inicio,
            @RequestParam LocalDateTime fin
    ) {

        byte[] pdf = service.generarReportePDF(inicio, fin);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=reporte-ventas.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }

    @GetMapping("/dashboard")
    public DashboardDTO resumen() {
        return service.obtenerResumen();
    }


}
