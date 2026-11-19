package com.aci.aci.controller;
import com.aci.aci.model.Eventos;
import com.aci.aci.service.EventosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@RestController 
@RequestMapping ("/api/eventos")
public class EventosController {

    @Autowired 
    private EventosService eventosService;

    @GetMapping 
    public List<Eventos> findAll() {
        return eventosService.getAllEventos();
    }

    @GetMapping("/evento/{eventoId}") //entre paréntesis va lo que aparecerá en el url
    public  ResponseEntity<Eventos> findById(@PathVariable Integer eventoId){
        Optional<Eventos> evento = eventosService.getById(eventoId); //si lo encuentra el controlador devuelve 200 o 404 not found
        return evento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{categoria}") //buscar eventos por categoría
    public  ResponseEntity<List<Eventos>> findByCategoria(@PathVariable String categoria){ //antes se buscaba un evento concreto, ahora una lista
        List<Eventos> eventos = eventosService.getByCategoria(categoria); 

        if (eventos.isEmpty()) { //se recoge la respuesta en un if else por si el filtro por categoría da una lista vacía
            return ResponseEntity.notFound().build(); 
        } else {
            return ResponseEntity.ok(eventos);
        }

        }

    @GetMapping("/sala/{salaId}") //buscar eventos por sala
    public  ResponseEntity<List<Eventos>> findBySalaId(@PathVariable Integer salaId){ 
        List<Eventos> eventos = eventosService.getBySalaId(salaId); 

        if (eventos.isEmpty()) { 
            return ResponseEntity.notFound().build(); 
        } else {
            return ResponseEntity.ok(eventos);
        }
    }

    @GetMapping("/fecha/{fechaIntoducida}") //buscar eventos por fecha
    public  ResponseEntity<List<Eventos>> findByFecha(@PathVariable LocalDate fechaIntroducida){
        List<Eventos> eventos = eventosService.getByFecha(fechaIntroducida); 

        if (eventos.isEmpty()) { 
            return ResponseEntity.notFound().build(); 
        } else {
            return ResponseEntity.ok(eventos);
        }

    }

}

