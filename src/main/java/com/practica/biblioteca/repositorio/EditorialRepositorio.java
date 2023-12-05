package com.practica.biblioteca.repositorio;

import com.practica.biblioteca.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("Select e from Editorial e Where e.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre")String nombre);
}
