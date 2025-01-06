package org.pruden.DAO;

import org.pruden.entities.Jugador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

public class JugadorDao {

    public static void subirJugadorADB(ArrayList<Jugador> listaJugadores) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ajedrezPU");
        EntityManager em = emf.createEntityManager();

        int batchSize = 50;
        int count = 0;

        em.getTransaction().begin();

        for (Jugador jugador : listaJugadores) {
            JugadorDao.guardarOActualizarJugador(em, jugador);

            count++;

            if (count % batchSize == 0) {
                em.flush();
                em.clear();
            }
        }
        em.getTransaction().commit();

        em.close();
        emf.close();
    }

    public static void guardarOActualizarJugador(EntityManager em, Jugador jugador) {
        em.createNativeQuery(
                        "INSERT INTO jugadores (id, nombre, apellidos, elo, sexo, federacion, titulo, bYear) " +
                                "VALUES (:id, :nombre, :apellidos, :elo, :sexo, :federacion, :titulo, :bYear) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "nombre = VALUES(nombre), " +
                                "apellidos = VALUES(apellidos), " +
                                "elo = VALUES(elo), " +
                                "sexo = VALUES(sexo), " +
                                "federacion = VALUES(federacion), " +
                                "titulo = VALUES(titulo), " +
                                "bYear = VALUES(bYear)")
                .setParameter("id", jugador.getId())
                .setParameter("nombre", jugador.getNombre())
                .setParameter("apellidos", jugador.getApellidos())
                .setParameter("elo", jugador.getElo())
                .setParameter("sexo", jugador.getSexo())
                .setParameter("federacion", jugador.getFederacion())
                .setParameter("titulo", jugador.getTitulo())
                .setParameter("bYear", jugador.getBYear())
                .executeUpdate();
    }
}
