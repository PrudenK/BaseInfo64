package org.pruden.metodos;

import org.pruden.entities.Jugador;
import org.pruden.entities.JugadorTorneo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class ProcesarJugador {

    public static void procesarJugadores(ArrayList<JugadorTorneo> listaJugadoresTorneo, ArrayList<Jugador> listaJugadores) throws IOException {
        for (JugadorTorneo jugador : listaJugadoresTorneo) {
            System.out.println("Procesando jugador con ID FIDE: " + jugador.getIdJugador());

            Document docJugador = Jsoup.connect("https://ratings.fide.com/profile/" + jugador.getIdJugador()).get();

            Long id = Long.parseLong(String.valueOf(jugador.getIdJugador()));

            String[] nombreCompleto = ProcesarJugador.devolverNombreCompleto(docJugador);
            String nombre = nombreCompleto[0];
            String apellidos = nombreCompleto[1];

            int elo = ProcesarJugador.procesarElo(docJugador);

            String federacion = ProcesarJugador.devolverParametrosPorCssQuery(docJugador,
                    "div.profile-top-info__block__row:has(div.profile-top-info__block__row__header:contains(Federation)) div.profile-top-info__block__row__data");
            String titulo = ProcesarJugador.devolverParametrosPorCssQuery(docJugador,
                    "div.profile-top-info__block__row:has(div.profile-top-info__block__row__header:contains(Title)) div.profile-top-info__block__row__data");
            String sexo = ProcesarJugador.devolverParametrosPorCssQuery(docJugador,
                    "div.profile-top-info__block__row:has(div.profile-top-info__block__row__header:contains(Sex)) div.profile-top-info__block__row__data");
            String bYearText = ProcesarJugador.devolverParametrosPorCssQuery(docJugador,
                    "div.profile-top-info__block__row:has(div.profile-top-info__block__row__header:contains(B-Year)) div.profile-top-info__block__row__data");

            int bYear = bYearText.isEmpty() ? 0 : Integer.parseInt(bYearText);

            listaJugadores.add(new Jugador(id, nombre, apellidos, elo, sexo, federacion, titulo, bYear));
        }
    }

    public static int procesarElo(Document docJugador){
        int elo = 0;
        Element eloElement = docJugador.selectFirst("div.profile-top-rating-data.profile-top-rating-data_gray");
        if (eloElement != null) {
            String eloText = eloElement.ownText().trim();
            if (!eloText.isEmpty() && !eloText.equals("Not rated")) {

                elo = Integer.parseInt(eloText.replaceAll("[^0-9]", ""));
            }
        }
        return elo;
    }

    public static String devolverParametrosPorCssQuery(Document docJugador, String cssQuery){
        return docJugador.select(cssQuery).text();
    }

    public static String[] devolverNombreCompleto(Document docJugador){
        String nombreCompleto = docJugador.select("div.col-lg-8.profile-top-title").text();
        String nombre;
        String apellidos;
        if (nombreCompleto.contains(",")) {
            String[] nombreDividido = nombreCompleto.split(", ");
            apellidos = nombreDividido.length > 0 ? nombreDividido[0].trim() : "";
            nombre = nombreDividido.length > 1 ? nombreDividido[1].trim() : "";
        } else {
            String[] nombreDividido = nombreCompleto.split(" ", 2);
            nombre = nombreDividido.length > 0 ? nombreDividido[0].trim() : "";
            apellidos = nombreDividido.length > 1 ? nombreDividido[1].trim() : "";
        }
        return new String[]{nombre, apellidos};
    }
}
