package com.aci.aci.service;
import com.aci.aci.model.Eventos; //relaciono este service con su respectivo repositorio y modelo 
import com.aci.aci.repository.EventosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List; //para trabajar con la lista de eventos
import java.util.Optional;
import java.time.LocalDate;

@Service
public class EventosService {

    private final EventosRepository eventosRepository; //se declara el repositorio para que el service tenga acceso a él
    
    @Autowired 
    public EventosService(EventosRepository eventosRepository){
        this.eventosRepository = eventosRepository;
    }

    public List<Eventos> getAllEventos() {
        return eventosRepository.findAll();
    }

    public Optional<Eventos> getById(Integer eventoId) { //se añade esto optional por si se pide buscar un evento que no exista devuelva la opción vacía
        return eventosRepository.findById(eventoId);
    }

    public List<Eventos> getByCategoria(String categoria) { 
        return eventosRepository.findByCategoria(categoria);
    }

    public List<Eventos> getBySalaId(Integer salaId){
        return  eventosRepository.findBySala_SalaId(salaId);
    }

    public  List<Eventos> getByFecha(LocalDate fecha){
        return eventosRepository.findByFecha(fecha);
    }

}
