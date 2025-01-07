package org.pruden.DAO;

import org.pruden.entities.Arbitro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ArbitroDao {

    public static void guardarOActualizarArbitro(Arbitro arbitro) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ajedrezPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            guardarOActualizarArbitro(em, arbitro);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void guardarOActualizarArbitro(EntityManager em, Arbitro arbitro) {
        em.createNativeQuery(
                        "INSERT INTO arbitro (codigo, nombre, apellidos, titulo, federacion) " +
                                "VALUES (:codigo, :nombre, :apellidos, :titulo, :federacion) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "nombre = VALUES(nombre), " +
                                "apellidos = VALUES(apellidos), " +
                                "titulo = VALUES(titulo), " +
                                "federacion = VALUES(federacion)")
                .setParameter("codigo", arbitro.getCodigo())
                .setParameter("nombre", arbitro.getNombre())
                .setParameter("apellidos", arbitro.getApellidos())
                .setParameter("titulo", arbitro.getTitulo())
                .setParameter("federacion", arbitro.getFederacion())
                .executeUpdate();
    }
}

