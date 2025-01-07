package org.pruden;

import org.jsoup.nodes.Element;
import org.pruden.DAO.*;
import org.pruden.entities.*;
import org.pruden.metodos.ProcesarArbitro;
import org.pruden.metodos.ProcesarJugador;
import org.pruden.metodos.ProcesarPartida;
import org.pruden.metodos.ProcesarTorneo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<JugadorTorneo> listaJugadoresTorneo = new ArrayList<>();
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        ArrayList<Partida> listaPartidasTorneo = new ArrayList<>();

        String urlTorneo = "https://info64.org/ce-sub18-2023";
        Document docTorneo = Jsoup.connect(urlTorneo).get();

        // Subir torneo
        Torneo torneo = ProcesarTorneo.procesarTorneo(urlTorneo, docTorneo);
        TorneoDao.subirTorneoADB(torneo);

        // Subir Arbitros y arbitros_Torneo
        ProcesarArbitro.procesarArbitro(docTorneo, urlTorneo.split("https://info64.org/")[1]);

        ProcesarTorneo.cargarListaJugadoresTorneo(docTorneo, listaJugadoresTorneo);
        listaJugadoresTorneo.sort(Comparator.comparingInt(JugadorTorneo::getRankingInicial));

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


