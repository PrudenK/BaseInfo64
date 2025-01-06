package org.pruden.DAO;

import org.pruden.entities.Torneo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TorneoDao {

    public static void subirTorneoADB(Torneo torneo) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ajedrezPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Iniciar la transacción
            em.getTransaction().begin();

            // Guardar o actualizar el torneo
            guardarOActualizarTorneo(em, torneo);

            // Confirmar la transacción
            em.getTransaction().commit();
        } catch (Exception e) {
            // Manejar excepciones
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {

            em.close();
            emf.close();
        }
    }

    public static void guardarOActualizarTorneo(EntityManager em, Torneo torneo) {
        em.createNativeQuery(
                        "INSERT INTO torneos (id, nombre, fecha_inicio, fecha_fin, lugar, rondas, ritmo) " +
                                "VALUES (:id, :nombre, :fechaInicio, :fechaFin, :lugar, :rondas, :ritmo) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "nombre = VALUES(nombre), " +
                                "fecha_inicio = VALUES(fecha_inicio), " +
                                "fecha_fin = VALUES(fecha_fin), " +
                                "lugar = VALUES(lugar), " +
                                "rondas = VALUES(rondas), " +
                                "ritmo = VALUES(ritmo)")
                .setParameter("id", torneo.getId())
                .setParameter("nombre", torneo.getNombre())
                .setParameter("fechaInicio", torneo.getFechaInicio())
                .setParameter("fechaFin", torneo.getFechaFin())
                .setParameter("lugar", torneo.getLugar())
                .setParameter("rondas", torneo.getRondas())
                .setParameter("ritmo", torneo.getRitmo())
                .executeUpdate();
    }
}

