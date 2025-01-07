package org.pruden.metodos;

import org.pruden.entities.JugadorTorneo;
import org.pruden.entities.Torneo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ProcesarTorneo {

    public static Torneo procesarTorneo(String urlTorneo, Document docTorneo) throws IOException {

        String idTorneo = urlTorneo.split("https://info64.org/")[1];

        Element titleElement = docTorneo.selectFirst("h1");

        String lugarFechaIniYFin = docTorneo.select("div#dates").stream()
                .findFirst()
                .map(datesElement -> datesElement.ownText().trim() + " " +
                        datesElement.select("span.mommentdate.short").get(0).text() + " @@ " +
                        datesElement.select("span.mommentdate.short").get(1).text())
                .orElse("No se encontró la información.");


        String[] infoTorneo = lugarFechaIniYFin.split(", from to ");
        String lugar = infoTorneo[0];
        String fechaIni = infoTorneo[1].split(" @@ ")[0];
        String fechaFin = infoTorneo[1].split(" @@ ")[1];


        Element ritmoElement = docTorneo.selectFirst("span.rateofplay");

        Document docTorneoStats = Jsoup.connect(urlTorneo+"/stats").get();

        Element numeroRondasElement = docTorneoStats.selectFirst("table.table-striped tbody tr:has(th:containsOwn(Number of rounds)) td");


        String nombreTorneo = titleElement != null ? titleElement.text() : null;
        int numRondas = numeroRondasElement != null ? Integer.parseInt(numeroRondasElement.ownText().trim()) : 0;
        String ritmo = ritmoElement != null ? ritmoElement.text() : null;
        return new Torneo(idTorneo, nombreTorneo, fechaIni, fechaFin, lugar, numRondas, ritmo);

    }


    public static void cargarListaJugadoresTorneo(Document docTorneo, ArrayList<JugadorTorneo> listaJugadoresTorneo) throws IOException {
        Elements fideElements = docTorneo.select("td.playerfideratid a");
        String urlBase = docTorneo.baseUri();

        int ranking = 1;
        for (Element fideElement : fideElements) {
            String fideUrl = fideElement.attr("href");
            String fideNum = fideUrl.replace("https://ratings.fide.com/profile/", "");

            System.out.println(fideNum);

            System.out.println(urlBase.split("https://info64.org/")[1]);
            listaJugadoresTorneo.add(new JugadorTorneo(urlBase.split("https://info64.org/")[1], fideNum, ranking, -1, "-1"));
            ranking++;
        }
        listaJugadoresTorneo.sort(Comparator.comparingInt(JugadorTorneo::getRankingInicial));

        ArrayList<JugadorTorneo> listaJugadoresAux = new ArrayList<>();

        Document docClasificacion = Jsoup.connect(urlBase+"/standings").get();
        Element tabla = docClasificacion.selectFirst("div.main tbody");



        Elements filas = tabla.select("tr");
        for (Element fila : filas) {
            Elements celdas = fila.select("td");


            int rankingAux = Integer.parseInt(celdas.get(1).text());
            int rankingFinal = Integer.parseInt(celdas.get(0).text());
            System.out.println(celdas.text());
            String puntos = celdas.get(6).text();
            listaJugadoresAux.add(new JugadorTorneo("", "", rankingAux, rankingFinal, puntos));
        }

        listaJugadoresAux.sort(Comparator.comparingInt(JugadorTorneo::getRankingInicial));

        System.out.println(listaJugadoresAux);

        for (int i = 0; i < listaJugadoresTorneo.size(); i++) {
            listaJugadoresTorneo.get(i).setRankingFinal(listaJugadoresAux.get(i).getRankingFinal());
            listaJugadoresTorneo.get(i).setPuntos(listaJugadoresAux.get(i).getPuntos());
        }

    }
}
