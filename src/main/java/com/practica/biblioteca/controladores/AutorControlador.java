package com.practica.biblioteca.controladores;

import com.practica.biblioteca.entidades.Autor;
import com.practica.biblioteca.excepciones.MiException;
import com.practica.biblioteca.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;
    @GetMapping("/registrar") // petición get http -> localhost:8080/autor/registrar
    public String registrar(){
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try{
            autorServicio.crearAutor(nombre);
            modelo.put("exitoso", "El autor fue agregado correctamente");
        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "autor_form.html";
    }
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();

        modelo.addAttribute("autores", autores);
        return "autor_lista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombre, ModelMap modelo) {
        try{
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";

        } catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
}
