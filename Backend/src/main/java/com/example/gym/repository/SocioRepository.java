package com.example.gym.repository;

import com.example.gym.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    Optional<Socio> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);

    @Query("SELECT s FROM Socio s WHERE " +
           "LOWER(s.nombre) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.apellido) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(s.dni) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Socio> buscarPorTermino(@Param("query") String query);
}