package com.example.demo.service;

import com.example.demo.entity.Categoria;
import com.example.demo.repository.CategoriaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoriaService {

    private final CategoriaRepo repository;

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Categoria guardar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria nueva){

        Categoria c = repository.findById(id).orElseThrow();
        c.setNombre(nueva.getNombre());

        return repository.save(c);
    }

    public void eliminar(Long id){
        repository.deleteById(id);
    }
}
