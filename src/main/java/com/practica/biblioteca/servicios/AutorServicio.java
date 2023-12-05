package com.practica.biblioteca.servicios;

import com.practica.biblioteca.entidades.Autor;
import com.practica.biblioteca.entidades.Libro;
import com.practica.biblioteca.excepciones.MiException;
import com.practica.biblioteca.repositorio.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException{
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList();

        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException{
        validar(nombre);
        if (id == null || id.isEmpty()){
            throw new MiException("El id del autor no puede ser nulo o estar vacío");
        }

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }

    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre del autor no puede ser nulo o estar vacío");
        }
    }
}
