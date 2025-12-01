package org.drk.pelicula;

import org.drk.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Pelicula.
 * Proporciona métodos para realizar operaciones CRUD y consultas relacionadas con las películas.
 * Utiliza Hibernate para la gestión de la persistencia de datos.
 * Implementa la interfaz genérica Repository para definir las operaciones básicas.
 */

public class PeliculaRepository implements Repository<Pelicula> {

    private final SessionFactory sessionFactory;

    public PeliculaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Pelicula save(Pelicula entity) {
        try (Session s = sessionFactory.openSession()) {
            s.beginTransaction();
            Pelicula managed = s.merge(entity);
            s.getTransaction().commit();
            return managed;
        }
    }

    @Override
    public Optional<Pelicula> delete(Pelicula entity) {
        try (Session s = sessionFactory.openSession()) {
            s.beginTransaction();
            s.remove(entity);
            s.getTransaction().commit();
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Pelicula> deleteById(Long id) {
        try (Session s = sessionFactory.openSession()) {
            Pelicula p = s.find(Pelicula.class, id);
            if (p != null) {
                s.beginTransaction();
                s.remove(p);
                s.getTransaction().commit();
            }
            return Optional.ofNullable(p);
        }
    }

    @Override
    public Optional<Pelicula> findById(Long id) {
        try (Session s = sessionFactory.openSession()) {
            return Optional.ofNullable(s.find(Pelicula.class, id));
        }
    }

    @Override
    public List<Pelicula> findAll() {
        try (Session s = sessionFactory.openSession()) {
            return s.createQuery("from Pelicula", Pelicula.class).list();
        }
    }

    @Override
    public Long count() {
        try (Session s = sessionFactory.openSession()) {
            return s.createQuery("select count(p) from Pelicula p", Long.class).getSingleResult();
        }
    }
}
