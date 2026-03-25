package com.example.demo.repository;

import com.example.demo.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    @Query("""
        SELECT p FROM Producto p
        WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))""")
    List<Producto> buscar(@Param("texto") String texto);

}
