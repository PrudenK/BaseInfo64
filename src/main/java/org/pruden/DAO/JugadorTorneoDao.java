package org.pruden.DAO;

import org.pruden.entities.JugadorTorneo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JugadorTorneoDao {

    public static void subirJugadorTorneoADB(JugadorTorneo jugadorTorneo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ajedrezPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            guardarOActualizarJugadorTorneo(em, jugadorTorneo);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void guardarOActualizarJugadorTorneo(EntityManager em, JugadorTorneo jugadorTorneo) {
        em.createNativeQuery(
                        "INSERT INTO jugador_torneo (id_torneo, id_jugador, ranking_inicial) " +
                                "VALUES (:idTorneo, :idJugador, :rankingInicial) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "ranking_inicial = VALUES(ranking_inicial)")
                .setParameter("idTorneo", jugadorTorneo.getIdTorneo())
                .setParameter("idJugador", jugadorTorneo.getIdJugador())
                .setParameter("rankingInicial", jugadorTorneo.getRankingInicial())
                .executeUpdate();
    }
}
