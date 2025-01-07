package org.pruden.metodos;

import org.jsoup.nodes.Element;
import org.pruden.DAO.ArbitroDao;
import org.pruden.DAO.ArbitroTorneoDao;
import org.pruden.entities.Arbitro;
import org.jsoup.nodes.Document;

import java.util.ArrayList;


public class ProcesarArbitro {
    public static void procesarArbitro(Document docTorneo, String idTorneo) {
        ArrayList<String> codigosArbitros = new ArrayList<>();


        Element arbitroPrincipal = docTorneo.select("span.arbiter.chief").first();

        Element extraInfo = docTorneo.getElementById("extra-information");

        String fullText = "";
        if (extraInfo != null) fullText = extraInfo.text();


        String arbitrosAdjuntos = arbitroPrincipal.text()+" "+fullText.split("Deputy Arbiters: ")[1].split("Rate of play:")[0];

        String[] arbitros = arbitrosAdjuntos.split("\\) ");

        for (int i = 0; i < arbitros.length; i++) {
            String titulo = null;
            if(arbitros[i].matches("^(IA|FA|NA).*")){
                titulo = arbitros[i].substring(0,2);
                arbitros[i] = arbitros[i].substring(2);
            }

            String nombre = arbitros[i].split(" \\(")[0].split(",")[1];
            String apellidos = arbitros[i].split(",")[0];
            String codigoFide = arbitros[i].split("\\(")[1].split(",")[0];
            String federacion = arbitros[i].split(", ")[2];

            codigosArbitros.add(codigoFide);

            ArbitroDao.guardarOActualizarArbitro(new Arbitro(codigoFide, nombre, apellidos, titulo, federacion));
        }


        for(String codigo :codigosArbitros) {
            ArbitroTorneoDao.asociarArbitroConTorneo(codigo, idTorneo);
        }

    }
}
