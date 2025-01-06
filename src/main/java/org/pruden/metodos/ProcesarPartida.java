package org.pruden.metodos;

import org.pruden.entities.JugadorTorneo;
import org.pruden.entities.Partida;
import org.pruden.entities.auxClass.PartidaId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ProcesarPartida {
    public static void procesarPartida(int rondas, String urlTorneo, ArrayList<JugadorTorneo> listaJugadoresTorneo, ArrayList<Partida> listaPartidasTorneo) throws IOException {
        for (int i = 1; i < rondas+1; i++) {
            Document docRondaTorneo = Jsoup.connect(urlTorneo + "/" + i).get();

            Element tabla = docRondaTorneo.selectFirst("div.main tbody");

            if (tabla != null) {
                Elements filas = tabla.select("tr");
                for (Element fila : filas) {
                    Elements celdas = fila.select("td");
                    if (celdas.size() >= 4) {
                        int mesa = Integer.parseInt(celdas.get(0).text().trim());

                        String resultado = celdas.get(6).text().trim();

                        int rankingBlancas = Integer.parseInt(celdas.get(2).text().trim()) -1;

                        String negras = celdas.get(8).text().trim();

                        if(negras.isEmpty()){
                            negras = "0";
                        }

                        int rankingNegras = Integer.parseInt(negras) -1;

                        String idJugadorNegras = (rankingNegras == -1) ? "-1" : listaJugadoresTorneo.get(rankingNegras).getIdJugador();

                        PartidaId partidaId = new PartidaId(
                                listaJugadoresTorneo.get(0).getIdTorneo(),
                                listaJugadoresTorneo.get(rankingBlancas).getIdJugador(),
                                idJugadorNegras,
                                i
                        );

                        Document docPgn = Jsoup.connect(urlTorneo + "/" + i+"/"+mesa).get();
                        System.out.println(urlTorneo + "/" + i+"/"+mesa);

                        Element pgnElement = docPgn.selectFirst("div.panel-body");

                        String pgn = pgnElement.text().trim();

                        String pgnProcesado;

                        if(pgn.equals("None Download - PGN viewer by pgn4web")){
                            pgnProcesado = null;
                        }else{
                            pgnProcesado = pgn.replaceAll("\\{.*?\\}", "").replaceAll("\\[.*?\\]", "")
                                    .replaceAll("\n", "").trim().replace(" Download - PGN viewer by pgn4web", "")
                                    .replaceAll("\\s{2,}", " ").trim();
                        }

                        System.out.println(pgnProcesado);

                        Partida partida = new Partida(partidaId, resultado, mesa, pgnProcesado);
                        listaPartidasTorneo.add(partida);

                    }
                }
                System.out.println("------------------");
            }else {
                System.out.println("------------Null------");
            }
        }
    }
}
