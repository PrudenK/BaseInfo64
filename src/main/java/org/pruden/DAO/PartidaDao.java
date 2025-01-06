package org.pruden.DAO;

import org.pruden.entities.Partida;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PartidaDao {

    public static void subirPartidaADB(Partida partida) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ajedrezPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            guardarOActualizarPartida(em, partida);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void guardarOActualizarPartida(EntityManager em, Partida partida) {
        em.createNativeQuery(
                        "INSERT INTO partidas (id_torneo, id_jugador_blancas, id_jugador_negras, ronda, resultado, mesa, pgn) " +
                                "VALUES (:idTorneo, :idJugadorBlancas, :idJugadorNegras, :ronda, :resultado, :mesa, :pgn) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "resultado = VALUES(resultado), " +
                                "mesa = VALUES(mesa)," +
                                "pgn = VALUES(pgn)")
                .setParameter("idTorneo", partida.getId().getIdTorneo())
                .setParameter("idJugadorBlancas", partida.getId().getIdJugadorBlancas())
                .setParameter("idJugadorNegras", partida.getId().getIdJugadorNegras())
                .setParameter("ronda", partida.getId().getRonda())
                .setParameter("resultado", partida.getResultado())
                .setParameter("mesa", partida.getMesa())
                .setParameter("pgn", partida.getPgn())
                .executeUpdate();
    }
}
