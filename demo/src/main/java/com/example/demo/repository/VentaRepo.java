package com.example.demo.repository;

import com.example.demo.entity.EstadoVenta;
import com.example.demo.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepo extends JpaRepository<Venta, Long> {

    // solo activas
    List<Venta> findByEstado(EstadoVenta estado);

    // reporte por rango de fechas
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // contar por estado
    long countByEstado(EstadoVenta estado);


    // ==============================
    // DASHBOARD (SIN FUNCIONES SQL)
    // ==============================

    @Query("""
        SELECT COUNT(v)
        FROM Venta v
        WHERE v.fecha BETWEEN :inicio AND :fin
    """)
    long countVentasEntre(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );


    @Query("""
        SELECT COALESCE(SUM(v.total),0)
        FROM Venta v
        WHERE v.fecha BETWEEN :inicio AND :fin
    """)
    double sumTotalEntre(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}
