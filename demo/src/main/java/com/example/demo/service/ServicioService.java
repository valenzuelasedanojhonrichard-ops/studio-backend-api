package com.example.demo.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ServicioRepo;
import com.example.demo.entity.Servicio;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepo repo;

    public List<Servicio> listar(){
        return repo.findAll();
    }

    public Servicio guardar(Servicio s){
        return repo.save(s);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public Servicio actualizar(Long id, Servicio s){
        s.setId(id);
        return repo.save(s);
    }
}
