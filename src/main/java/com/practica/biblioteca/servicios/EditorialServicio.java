package com.practica.biblioteca.servicios;

import com.practica.biblioteca.entidades.Autor;
import com.practica.biblioteca.entidades.Editorial;
import com.practica.biblioteca.excepciones.MiException;
import com.practica.biblioteca.repositorio.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList();

        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    @Transactional
    public void modificarEditorial (String id, String nombre) throws MiException{
        validar(nombre);
        if (id == null || id.isEmpty()){
            throw new MiException("El id de Editorial no puede ser nulo o estar vacío");
        }
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre de Editorial no puede ser nulo o estar vacío");
        }
    }
}
