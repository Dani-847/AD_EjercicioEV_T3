package org.drk;

import org.drk.opinion.Opinion;
import org.drk.opinion.OpinionRepository;
import org.drk.pelicula.Pelicula;
import org.drk.pelicula.PeliculaRepository;
import org.drk.utils.DataProvider;
import org.drk.utils.DataService;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

/**
 * Aplicación de consola para la gestión de críticas de películas.
 * Permite listar películas y opiniones, registrar nuevas películas,
 * ver opiniones por usuario, añadir opiniones a películas y listar
 * películas con baja puntuación.
 * La aplicación utiliza Hibernate para la persistencia de datos.
 * El usuario interactúa a través de un menú en la consola.
 * Las funcionalidades principales se gestionan mediante la clase DataService.
 *
 * Uso:
 */

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = DataProvider.getSessionFactory();
        PeliculaRepository peliculaRepository = new PeliculaRepository(sessionFactory);
        OpinionRepository opinionRepository = new OpinionRepository(sessionFactory);
        DataService dataService = new DataService(peliculaRepository, opinionRepository);

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("===== ===== ===== Gestor de críticas ===== ===== =====");
            System.out.println("1. Listar todas las peliculas");
            System.out.println("2. Listar todas las opiniones");
            System.out.println("3 (HU1). Registrar nueva pelicula");
            System.out.println("4 (HU2). Ver opiniones por correo");
            System.out.println("5 (HU3). Añadir opinion a una pelicula");
            System.out.println("6 (HU4). Listar peliculas con baja puntuacion (<= 3)");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    List<Pelicula> peliculas = dataService.listarPeliculas();
                    System.out.println("--- PELICULAS ---");
                    for (Pelicula p : peliculas) {
                        System.out.println("Pelicula [id=" + p.getId() + ", titulo=" + p.getTitulo() + "]");
                    }
                }

                case 2 -> {
                    List<Opinion> opiniones = dataService.listarOpiniones();
                    System.out.println("--- OPINIONES ---");
                    listarOpiniones(opiniones);
                }

                case 3 -> {
                    System.out.print("Titulo de la nueva pelicula: ");
                    String titulo = sc.nextLine();
                    Pelicula nueva = dataService.registrarPelicula(titulo);
                    System.out.println("Pelicula creada con id=" + nueva.getId()
                            + ", titulo=" + nueva.getTitulo());
                }

                case 4 -> {
                    System.out.print("Correo del usuario: ");
                    String correo = sc.nextLine();
                    List<Opinion> opinionesUser = dataService.obtenerOpinionesDeUsuario(correo);
                    System.out.println("--- OPINIONES DE " + correo + " ---");
                    listarOpiniones(opinionesUser);
                }

                case 5 -> {
                    System.out.print("Id de la pelicula: ");
                    int idPeli;
                    try {
                        idPeli = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Id no valido.");
                        break;
                    }
                    System.out.print("Descripcion de la opinion: ");
                    String desc = sc.nextLine();
                    System.out.print("Correo del usuario: ");
                    String usu = sc.nextLine();
                    System.out.print("Puntuacion (0-10): ");
                    int punt;
                    try {
                        punt = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Puntuacion no valida.");
                        break;
                    }
                    try {
                        Opinion opCreada = dataService.anadirOpinionAPelicula(
                                idPeli, desc, usu, punt);
                        System.out.println("Opinion creada con id=" + opCreada.getId());
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }

                case 6 -> {
                    List<String> malas = dataService.peliculasConBajaPuntuacion();
                    System.out.println("--- PELICULAS CON PUNTUACION MEDIA <= 3 ---");
                    for (String titulo : malas) {
                        System.out.println("Pelicula [titulo=" + titulo + "]");
                    }
                }

                case 0 -> System.out.println("Cerrando aplicacion...");


                default -> System.out.println("Opcion no valida.");
            }
        }
        sc.close();
    }


    private static void listarOpiniones(List<Opinion> opiniones) {
        for (Opinion o : opiniones) {
            Integer peliId = (o.getPelicula() != null) ? o.getPelicula().getId() : null;
            System.out.println("Opinion [id=" + o.getId() + ", usuario=" + o.getUsuario() + ", puntuacion=" + o.getPuntuacion() + ", peliculaId=" + peliId + "]");
        }
    }
}
