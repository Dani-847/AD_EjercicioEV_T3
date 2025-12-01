package org.drk.opinion;

import org.drk.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Opinion.
 * Proporciona métodos para realizar operaciones CRUD y consultas específicas
 * relacionadas con las opiniones sobre películas.
 * Utiliza Hibernate para la gestión de la persistencia de datos.
 * Implementa la interfaz genérica Repository para definir las operaciones básicas.
 */

public class OpinionRepository implements Repository<Opinion> {

    private final SessionFactory sessionFactory;

    public OpinionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Opinion save(Opinion entity) {
        try (Session s = sessionFactory.openSession()) {
            s.beginTransaction();
            Opinion managed = s.merge(entity);
            s.getTransaction().commit();
            return managed;
        }
    }

    @Override
    public Optional<Opinion> delete(Opinion entity) {
        try (Session s = sessionFactory.openSession()) {
            s.beginTransaction();
            s.remove(entity);
            s.getTransaction().commit();
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Opinion> deleteById(Long id) {
        try (Session s = sessionFactory.openSession()) {
            Opinion o = s.find(Opinion.class, id);
            if (o != null) {
                s.beginTransaction();
                s.remove(o);
                s.getTransaction().commit();
            }
            return Optional.ofNullable(o);
        }
    }

    @Override
    public Optional<Opinion> findById(Long id) {
        try (Session s = sessionFactory.openSession()) {
            return Optional.ofNullable(s.find(Opinion.class, id));
        }
    }

    @Override
    public List<Opinion> findAll() {
        try (Session s = sessionFactory.openSession()) {
            return s.createQuery("from Opinion", Opinion.class).list();
        }
    }

    @Override
    public Long count() {
        try (Session s = sessionFactory.openSession()) {
            return s.createQuery("select count(o) from Opinion o", Long.class).getSingleResult();
        }
    }

    public List<Opinion> findByPeliculaId(Long peliculaId) {
        try (Session s = sessionFactory.openSession()) {
            return s.createQuery(
                            "from Opinion o where o.pelicula.id = :id",
                            Opinion.class
                    )
                    .setParameter("id", peliculaId)
                    .getResultList();
        }
    }
}
