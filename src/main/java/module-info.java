module AD.EjercicioEV.T3 {
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires static lombok;
    requires java.naming;

    opens org.drk.pelicula;
    exports org.drk.pelicula;
    opens org.drk.opinion;
    exports org.drk.opinion;
    opens org.drk.utils;
    exports org.drk.utils;
}