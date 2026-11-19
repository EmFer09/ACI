package com.aci.agendacultural.model;

public class Eventos {
    private int eventoId;
    private String nombre;
    private int salaId;
    private String fechaInicio;
    private String fechaFinal;
    private String categoria;
    private String descripcion;
    private String hora;

    //constructor sin argumentos
    public Eventos() {
    }

    public Eventos(String nombre, int salaId, String fechaInicio, String fechaFinal, String categoria, String descripcion, String hora) {
        this.nombre = nombre;
        this.salaId = salaId;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.hora = hora;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId ( int eventoId){
        this.eventoId = eventoId;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public int getSalaId () {
        return salaId;
    }

    public void setSalaId ( int salaId){
        this.salaId = salaId;
    }

    public String getFechaInicio () {
        return fechaInicio;
    }

    public void setFechaInicio (String fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal () {
        return fechaFinal;
    }

    public void setFechaFinal (String fechaFinal){
        this.fechaFinal = fechaFinal;
    }

    public String getCategoria () {
        return categoria;
    }

    public void setCategoria (String categoria){
        this.categoria = categoria;
    }

    public String getDescripcion () {
        return descripcion;
    }

    public void setDescripcion (String descripcion){
        this.descripcion = descripcion;
    }

    public String getHora () {
        return hora;
    }

    public void setHora (String hora){
        this.hora = hora;
    }
}
