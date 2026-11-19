package com.aci.aci.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private int salaId;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private String categoria;
    private String descripcion;
    private LocalTime hora;

    //constructor sin argumentos
    public Eventos() {
    }

    //constructor con todos los argumentos menos el Id
    public Eventos(String nombre, int salaId, LocalDate fechaInicio, LocalDate fechaFinal, String categoria, String descripcion, LocalTime hora) {
        this.nombre = nombre;
        this.salaId = salaId;
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

        public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
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
