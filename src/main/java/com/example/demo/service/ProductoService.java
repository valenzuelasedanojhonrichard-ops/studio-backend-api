package com.example.demo.service;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductoService {

    private final ProductoRepo repository;

    public List<Producto> listar() {
        return repository.findByActivoTrue();
    }

    public Producto guardar(Producto p) {
        p.setActivo(true);
        return repository.save(p);
    }
    public void eliminar(Long id){
        Producto p = repository.findById(id).orElseThrow();
        p.setActivo(false);
        repository.save(p);
    }
    public Producto actualizar(Long id, Producto nuevo){

        Producto p = repository.findById(id).orElseThrow();

        p.setNombre(nuevo.getNombre());
        p.setPrecio(nuevo.getPrecio());
        p.setStock(nuevo.getStock());
        p.setCategoria(nuevo.getCategoria());
        p.setMarca(nuevo.getMarca()); //

        return repository.save(p);
    }

    public List<Producto> buscar(String texto){
        return repository.buscar(texto);
    }
}
