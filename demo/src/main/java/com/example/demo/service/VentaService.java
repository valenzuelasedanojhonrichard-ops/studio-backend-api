package com.example.demo.service;

import com.example.demo.DTO.DashboardDTO;
import com.example.demo.DTO.ReporteVentaDTO;
import com.example.demo.DTO.ReporteVentaResponseDTO;
import com.example.demo.entity.DetalleVenta;
import com.example.demo.entity.EstadoVenta;
import com.example.demo.entity.Producto;
import com.example.demo.entity.Venta;
import com.example.demo.repository.ProductoRepo;
import com.example.demo.repository.VentaRepo;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class VentaService {

    private final VentaRepo ventaRepo;
    private final ProductoRepo productoRepo;


    public Venta guardar(Venta venta){

        if(venta.getDetalles() == null || venta.getDetalles().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La venta no tiene productos");
        }

        double total = 0;

        for(DetalleVenta d : venta.getDetalles()){

            if(d.getCantidad() <= 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad inválida");
            }

            Producto p = productoRepo.findById(d.getProducto().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no existe"));

            if(p.getStock() < d.getCantidad()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
            }

            d.setPrecio(p.getPrecio());

            double subtotal = p.getPrecio() * d.getCantidad();
            d.setSubtotal(subtotal);

            total += subtotal;

            p.setStock(p.getStock() - d.getCantidad());
            productoRepo.save(p);

            d.setVenta(venta);
        }

        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setEstado(EstadoVenta.ACTIVA);

        return ventaRepo.save(venta);
    }

    public List<Venta> listar(){
        return ventaRepo.findByEstado(EstadoVenta.ACTIVA);
    }

    public Venta cancelar(Long id){

        Venta v = ventaRepo.findById(id).orElseThrow();

        if(v.getEstado() == EstadoVenta.CANCELADA){
            throw new RuntimeException("La venta ya está cancelada");
        }

        v.setEstado(EstadoVenta.CANCELADA);

        return ventaRepo.save(v);
    }

    public ReporteVentaResponseDTO reportePorFechas(LocalDateTime inicio, LocalDateTime fin){

        List<Venta> ventas = ventaRepo.findByFechaBetween(inicio, fin);

        List<ReporteVentaDTO> lista = ventas.stream()
                .map(v -> new ReporteVentaDTO(
                        v.getId(),
                        v.getFecha(),
                        v.getTotal()
                ))
                .toList();

        double totalGeneral = lista.stream()
                .mapToDouble(ReporteVentaDTO::getTotal)
                .sum();

        return new ReporteVentaResponseDTO(lista, totalGeneral);
    }

    public List<Venta> reportePorEstado(EstadoVenta estado){
        return ventaRepo.findByEstado(estado); // ✅ correcto
    }

    public byte[] generarReportePDF(LocalDateTime inicio, LocalDateTime fin) {

        // ✅ FALTA ESTO
        ReporteVentaResponseDTO reporte = reportePorFechas(inicio, fin);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("REPORTE DE VENTAS").setBold());
            document.add(new Paragraph("Desde: " + inicio));
            document.add(new Paragraph("Hasta: " + fin));
            document.add(new Paragraph(" "));

            Table table = new Table(3);

            table.addHeaderCell("ID");
            table.addHeaderCell("Fecha");
            table.addHeaderCell("Total");

            for (ReporteVentaDTO v : reporte.getVentas()) {
                table.addCell(v.getId().toString());
                table.addCell(v.getFecha().toString());
                table.addCell(v.getTotal().toString());
            }

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("TOTAL GENERAL: " + reporte.getTotalGeneral()).setBold());

            document.close();

            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }

    public DashboardDTO obtenerResumen() {

        LocalDate hoy = LocalDate.now();

        // HOY
        LocalDateTime inicioHoy = hoy.atStartOfDay();
        LocalDateTime finHoy = hoy.atTime(23, 59, 59);

        // MES
        LocalDateTime inicioMes = hoy.withDayOfMonth(1).atStartOfDay();
        LocalDateTime finMes = hoy.withDayOfMonth(hoy.lengthOfMonth()).atTime(23, 59, 59);

        long ventasHoy = ventaRepo.countVentasEntre(inicioHoy, finHoy);
        long ventasMes = ventaRepo.countVentasEntre(inicioMes, finMes);
        double totalMes = ventaRepo.sumTotalEntre(inicioMes, finMes);
        long canceladas = ventaRepo.countByEstado(EstadoVenta.CANCELADA);

        return new DashboardDTO(
                ventasHoy,
                ventasMes,
                totalMes,
                canceladas
        );
    }

    public List<Venta> buscar(String texto){
        return ventaRepo.buscar(texto);
    }


}
