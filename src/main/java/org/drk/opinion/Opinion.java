package org.drk.opinion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.drk.pelicula.Pelicula;

import java.io.Serializable;

/**
 * Entidad que representa una opinión sobre una película.
 * Contiene la descripción de la opinión, el usuario que la emitió,
 * la puntuación otorgada y la película a la que se refiere.
 * Utiliza anotaciones de JPA para el mapeo a la base de datos.
 * Se implementa Serializable para permitir la serialización de objetos.
 * Utiliza Lombok para generar automáticamente constructores, getters, setters y otros métodos comunes.
 */

@Entity
@Table(name = "opinion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opinion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    //es el correo del usuario que da la opinion
    @Column(name = "usuario", nullable = false, length = 64)
    private String usuario;

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;
}
