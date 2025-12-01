package org.drk.pelicula;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.drk.opinion.Opinion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una película.
 * Contiene el título de la película y una lista de opiniones asociadas.
 * Utiliza anotaciones de JPA para el mapeo a la base de datos.
 * Se implementa Serializable para permitir la serialización de objetos.
 * Utiliza Lombok para generar automáticamente constructores, getters, setters y otros métodos comunes.
 */

@Entity
@Table(name = "pelicula")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @OneToMany(mappedBy = "pelicula", fetch = FetchType.LAZY)
    private List<Opinion> opiniones = new ArrayList<>();
}
