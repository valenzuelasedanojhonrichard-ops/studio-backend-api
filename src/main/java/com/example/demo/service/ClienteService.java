package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepo;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepo repo;

    public List<Cliente> listar(){
        return repo.findAll();
    }

    public Cliente guardar(Cliente c){
        return repo.save(c);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public Cliente actualizar(Long id, Cliente c){
        c.setId(id);
        return repo.save(c);
    }

    public List<Cliente> buscar(String texto){
        return repo.buscar(texto);
    }
}

