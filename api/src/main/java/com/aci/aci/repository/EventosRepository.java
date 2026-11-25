package com.aci.aci.repository;
import com.aci.aci.model.Eventos; //importo el modelo al que corresponde este repositorio
import org.springframework.data.jpa.repository.JpaRepository; //Jpa se encarga de cargar las ops básicas de consulta en la BD
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Integer> { //<Eventos, int> porque coge el eventosID que es de tipo int
    //declaro métodos para hacer las búsquedas que voy a necesitar que no son operaciones básicas ya cargadas porel JpaRepository
    List<Eventos> findByCategoria(String categoria);
    List<Eventos> findBySala_SalaId(Integer salaId); //porque ahora salaId pertenece a Salas, no a Eventos para que se muestre el nombre en la app
    
    /*la consulta de fecha es más complicada porque las películas no tienn fecha final y no se puede solo filtrar por fechaInicio 
    porque si se selecciona una fecha entre fechaInicio y fechaFinal no se devolverá ese evento (exposiciones)
    para ello primero declaro una query en JQL para aplicar todas las condiciones (las películas tienen fechaFinal NULL*/
    @Query("""
        SELECT e FROM Eventos e
        WHERE 
            (e.fechaFinal IS NOT NULL
            AND e.fechaInicio <= :fecha
            AND e.fechaFinal >= :fecha)
            OR
            (e.fechaFinal IS NULL
            AND e.fechaInicio = :fecha)
    """)
    List<Eventos> findByFecha(@Param("fecha") LocalDate fecha);
} 