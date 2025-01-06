package org.pruden.metodos;

import org.pruden.entities.JugadorTorneo;
import org.pruden.entities.Torneo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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


    public static void cargarListaJugadoresTorneo(Document docTorneo, ArrayList<JugadorTorneo> listaJugadoresTorneo){
        Elements fideElements = docTorneo.select("td.playerfideratid a");
        int ranking = 1;
        for (Element fideElement : fideElements) {
            String fideUrl = fideElement.attr("href");
            String fideNum = fideUrl.replace("https://ratings.fide.com/profile/", "");

            listaJugadoresTorneo.add(new JugadorTorneo(docTorneo.baseUri().split("https://info64.org/")[1], fideNum, ranking));
            ranking++;
        }
    }
}
