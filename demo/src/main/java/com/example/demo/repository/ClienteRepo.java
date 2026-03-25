package com.example.demo.repository;
import com.example.demo.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Long> {

    @Query("""
        SELECT c FROM Cliente c
        WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
        OR LOWER(c.email) LIKE LOWER(CONCAT('%', :texto, '%'))""")
    List<Cliente> buscar(@Param("texto") String texto);

}
