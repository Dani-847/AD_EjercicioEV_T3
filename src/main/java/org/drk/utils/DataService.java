package org.drk.utils;

import org.drk.opinion.Opinion;
import org.drk.opinion.OpinionRepository;
import org.drk.pelicula.Pelicula;
import org.drk.pelicula.PeliculaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal donde se implementan TODAS las historias de usuario.
 *
 * Servicio de datos que proporciona funcionalidades para gestionar películas y opiniones.
 * Utiliza repositorios para interactuar con la base de datos y realizar operaciones
 * como registrar películas, obtener opiniones por usuario, añadir opiniones a películas,
 * y listar películas con baja puntuación.
 * Implementa la lógica necesaria para cumplir con los requisitos
 * de la aplicación.
 */

public class DataService {

    private final PeliculaRepository peliculaRepository;
    private final OpinionRepository opinionRepository;

    public DataService(PeliculaRepository peliculaRepository, OpinionRepository opinionRepository) {
        this.peliculaRepository = peliculaRepository;
        this.opinionRepository = opinionRepository;
    }

    /**
     * Historia de usuario 1: Registrar nueva película.
     * Crea y guarda una nueva película con el título proporcionado.
     */
    public Pelicula registrarPelicula(String titulo) {
        Pelicula p = new Pelicula();
        p.setTitulo(titulo);
        return peliculaRepository.save(p);
    }

    /**
     * Historia de usuario 2: Ver opiniones por correo.
     * Obtiene todas las opiniones asociadas al correo electrónico proporcionado.
     * Devuelve una lista vacía si no se encuentran opiniones o si el correo es nulo.
     * Usa repositorio de opiniones para obtener todas las opiniones y filtra
     * aquellas que coinciden con el correo dado (ignorando mayúsculas/minúsculas).
     */
    public List<Opinion> obtenerOpinionesDeUsuario(String correo) {
        List<Opinion> todas = opinionRepository.findAll();
        List<Opinion> resultado = new ArrayList<>();

        if (correo == null) {
            return resultado;
        }

        String correoLower = correo.toLowerCase();

        for (Opinion o : todas) {
            if (o.getUsuario() != null && o.getUsuario().toLowerCase().equals(correoLower)) {
                resultado.add(o);
            }
        }

        return resultado;
    }

    /**
     * Historia de usuario 3: Añadir opinión a una película.
     * Crea y guarda una nueva opinión asociada a la película con el ID proporcionado.
     * Lanza una IllegalArgumentException si no existe la película con el ID dado.
     * Utiliza el repositorio de películas para buscar la película objetivo
     * y el repositorio de opiniones para guardar la nueva opinión.
     * Devuelve la opinión creada.
     */
    public Opinion anadirOpinionAPelicula(Integer peliculaId, String descripcion, String usuario, Integer puntuacion) {

        var peliculaObjetivo = peliculaRepository.findById(peliculaId.longValue());
        if (peliculaObjetivo.isEmpty()) {
            throw new IllegalArgumentException("No existe pelicula con id=" + peliculaId);
        }

        Opinion op = new Opinion();
        op.setDescripcion(descripcion);
        op.setUsuario(usuario);
        op.setPuntuacion(puntuacion);
        op.setPelicula(peliculaObjetivo.get());

        return opinionRepository.save(op);
    }

    /**
     * Historia de usuario 4: Listar películas con baja puntuación.
     * Obtiene una lista de títulos de películas que tienen al menos una opinión
     * con una puntuación menor o igual a 3.
     * Utiliza los repositorios de películas y opiniones para obtener los datos necesarios.
     * Devuelve una lista de títulos de películas que cumplen con el criterio.
     */
    public List<String> peliculasConBajaPuntuacion() {
        List<Pelicula> todas = peliculaRepository.findAll();
        List<String> titulos = new ArrayList<>();

        for (Pelicula p : todas) {
            List<Opinion> opiniones = opinionRepository.findByPeliculaId(p.getId().longValue());
            if (opiniones == null || opiniones.isEmpty()) {
                continue;
            }

            boolean tieneOpinionBaja = false;
            int i = 0;

            while (i < opiniones.size() && !tieneOpinionBaja) {
                Opinion o = opiniones.get(i);
                Integer puntuacion = o.getPuntuacion();
                if (puntuacion != null && puntuacion <= 3) {
                    tieneOpinionBaja = true;
                }
                i++;
            }

            if (tieneOpinionBaja) {
                titulos.add(p.getTitulo());
            }
        }

        return titulos;
    }

    public List<Pelicula> listarPeliculas() {
        return peliculaRepository.findAll();
    }

    public List<Opinion> listarOpiniones() {
        return opinionRepository.findAll();
    }
}
