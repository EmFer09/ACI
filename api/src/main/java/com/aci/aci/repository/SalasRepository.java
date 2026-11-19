package com.aci.aci.repository;
import com.aci.aci.model.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalasRepository extends JpaRepository<Salas, Integer> {

      List<Salas> getByCategoria(String categoria);
  
}