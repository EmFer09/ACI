package com.aci.aci.controller;
import com.aci.aci.model.Salas;
import com.aci.aci.service.SalasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController 
@RequestMapping ("/api/salas")
public class SalasController {

    @Autowired 
    private SalasService salasService;

    @GetMapping 
    public List<Salas> findAll() {
        return salasService.getAllSalas();
    }

    @GetMapping("/categoria/{categoria}") //buscar salas por categoría
    public  ResponseEntity<List<Salas>> findSalasByCategoria(@PathVariable String categoria){
        List<Salas> salas = salasService.findSalasByCategoria(categoria);
        if (salas.isEmpty()) { 
            return ResponseEntity.notFound().build(); 
        } else {
            return ResponseEntity.ok(salas);
        }

        }    

    @GetMapping ("/{id}") //por id para las fichas de sala
    public ResponseEntity<Salas> findById(@PathVariable Integer id) {
        Optional<Salas> salas = salasService.getById(id);
        return salas.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}