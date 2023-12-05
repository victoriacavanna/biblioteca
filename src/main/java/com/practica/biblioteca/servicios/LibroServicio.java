package com.practica.biblioteca.servicios;

import com.practica.biblioteca.entidades.Autor;
import com.practica.biblioteca.entidades.Editorial;
import com.practica.biblioteca.entidades.Libro;
import com.practica.biblioteca.excepciones.MiException;
import com.practica.biblioteca.repositorio.AutorRepositorio;
import com.practica.biblioteca.repositorio.EditorialRepositorio;
import com.practica.biblioteca.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {
    @Autowired //inyeccion de dependencia -> no hay que inicializar
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros(){
        List<Libro> libros = new ArrayList();

        libros = libroRepositorio.findAll();
        return libros;
    }

    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if(respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }

        if(respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }

        if (respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        if(isbn == null){
            throw new MiException("ISBN no puede ser nulo");
        }
        if(titulo == null || titulo.isEmpty()){
            throw new MiException("Titulo no puede ser nulo o estar vacío");
        }
        if(ejemplares == null){
            throw new MiException("Ejemplares no puede ser nulo");
        }
        if(idAutor == null || idAutor.isEmpty()){
            throw new MiException("El id de Autor no puede ser nulo o estar vacío");
        }
        if(idEditorial == null || idEditorial.isEmpty()){
            throw new MiException(" El id de Editorial no puede ser nulo o estar vacío");
        }
    }
}
