package org.pruden.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ArbitroTorneoDao {

    public static void asociarArbitroConTorneo(String idArbitro, String idTorneo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ajedrezPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            guardarAsociacion(em, idArbitro, idTorneo);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void guardarAsociacion(EntityManager em, String idArbitro, String idTorneo) {
        em.createNativeQuery(
                        "INSERT INTO arbitro_torneo (id_arbitro, id_torneo) " +
                                "VALUES (:idArbitro, :idTorneo) " +
                                "ON DUPLICATE KEY UPDATE id_arbitro = id_arbitro"
                )
                .setParameter("idArbitro", idArbitro)
                .setParameter("idTorneo", idTorneo)
                .executeUpdate();
    }
}
