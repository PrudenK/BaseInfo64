package org.pruden;

import org.pruden.DAO.JugadorDao;
import org.pruden.DAO.JugadorTorneoDao;
import org.pruden.DAO.PartidaDao;
import org.pruden.DAO.TorneoDao;
import org.pruden.entities.*;
import org.pruden.metodos.ProcesarJugador;
import org.pruden.metodos.ProcesarPartida;
import org.pruden.metodos.ProcesarTorneo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String urlTorneo = "https://info64.org/campeonato-de-espana-sub-18-2024";
        Document docTorneo = Jsoup.connect(urlTorneo).get();


        ArrayList<JugadorTorneo> listaJugadoresTorneo = new ArrayList<>();
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        ArrayList<Partida> listaPartidasTorneo = new ArrayList<>();

        ProcesarTorneo.cargarListaJugadoresTorneo(docTorneo, listaJugadoresTorneo);



        // Subir torneo
        Torneo torneo = ProcesarTorneo.procesarTorneo(urlTorneo, docTorneo);
        TorneoDao.subirTorneoADB(torneo);


        //Subir Jugador
        ProcesarJugador.procesarJugadores(listaJugadoresTorneo, listaJugadores);
        JugadorDao.subirJugadorADB(listaJugadores);


        //Subir jugadores torneo
        for (JugadorTorneo jugadorTorneo : listaJugadoresTorneo) {
            JugadorTorneoDao.subirJugadorTorneoADB(jugadorTorneo);
        }


        // Subir partidas torneo
        ProcesarPartida.procesarPartida(torneo.getRondas(), urlTorneo, listaJugadoresTorneo, listaPartidasTorneo);
        for(Partida partida : listaPartidasTorneo) {
            PartidaDao.subirPartidaADB(partida);
        }







    }
}


