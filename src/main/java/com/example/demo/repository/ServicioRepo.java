package com.example.demo.repository;
import com.example.demo.entity.Servicio;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ServicioRepo extends JpaRepository<Servicio, Long> {
}
