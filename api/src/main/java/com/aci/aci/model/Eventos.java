package com.aci.aci.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "eventos")
public class Eventos {

    // para la tabla Eventos creo las columnas correspondientes
    @Id // el dato a continuación (eventoId) es la PK de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que este valor se genera autom. desde MySQL
    private int eventoId;

    private String nombre;
    
    // en la app quiero que se muestre el nombre de la sala, no el ID entonces hay que hacer un inner join con la tabla Salas para obtener el nombre
    @ManyToOne
    @JoinColumn(name = "salaId")
    private Salas sala;

    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private String categoria;
    private String descripcion;
    private LocalTime hora;

    //constructor sin argumentos
    public Eventos() {
    }

    //constructor con todos los argumentos menos el Id
    public Eventos(String nombre, Salas sala, LocalDate fechaInicio, LocalDate fechaFinal, String categoria, String descripcion, LocalTime hora) {
        this.nombre = nombre;
        this.sala = sala;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.hora = hora;
    }

    //getters y setters
    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId){
        this.eventoId = eventoId;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

}
