package com.aci.aci.service;
import com.aci.aci.model.Salas;
import com.aci.aci.repository.SalasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SalasService {

    private final SalasRepository salasRepository;
    
    @Autowired 
    public SalasService(SalasRepository salasRepository){
        this.salasRepository = salasRepository;
    }

    public List<Salas> getAllSalas() {
        return salasRepository.findAll();
    }

    public List<Salas> getSalasByCategoria(String categoria) {
        return salasRepository.getByCategoria(categoria);
    }

    public Optional<Salas> getById(Integer id) { //filtrar por id para las fichas de las salas, igual que en eventos id
        return salasRepository.findById(id);
    }
}
