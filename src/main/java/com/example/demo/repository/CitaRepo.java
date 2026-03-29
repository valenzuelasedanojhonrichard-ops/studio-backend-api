package com.example.demo.repository;

import com.example.demo.entity.Cita;
import com.example.demo.entity.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CitaRepo extends JpaRepository<Cita, Long>  {

    List<Cita> findByEstado(EstadoCita estado);

    List<Cita> findByFechaHoraBetween(LocalDateTime inicioDia, LocalDateTime finDia);

    long countByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    long countByFechaHoraBetweenAndEstado(LocalDateTime inicio,
                                          LocalDateTime fin,
                                          EstadoCita estado);


    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByServicioId(Long servicioId);

    List<Cita> findByClienteIdAndEstado(Long clienteId, EstadoCita estado);

    @Query("""
        SELECT c FROM Cita c
        JOIN c.cliente cl
        WHERE LOWER(cl.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))""")
    List<Cita> buscar(@Param("texto") String texto);


}