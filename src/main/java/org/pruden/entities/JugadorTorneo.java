package org.pruden.entities;

import org.pruden.entities.auxClass.JugadorTorneoId;

import javax.persistence.*;

@Entity
@Table(name = "jugador_torneo")
@IdClass(JugadorTorneoId.class)
public class JugadorTorneo {

    @Id
    @Column(name = "id_torneo", nullable = false)
    private String idTorneo;

    @Id
    @Column(name = "id_jugador", nullable = false)
    private String idJugador;

    @Column(name = "ranking_inicial", nullable = false)
    private int rankingInicial;

    @Column(name = "ranking_final", nullable = false)
    private int rankingFinal;

    @Column
    private String puntos;

    public JugadorTorneo() {}

    public JugadorTorneo(String idTorneo, String idJugador, int rankingInicial, int rankingFinal, String puntos) {
        this.idTorneo = idTorneo;
        this.idJugador = idJugador;
        this.rankingInicial = rankingInicial;
        this.rankingFinal = rankingFinal;
        this.puntos = puntos;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public int getRankingInicial() {
        return rankingInicial;
    }

    public void setRankingInicial(int rankingInicial) {
        this.rankingInicial = rankingInicial;
    }
    public int getRankingFinal() {
        return rankingFinal;
    }
    public void setRankingFinal(int rankingFinal) {
        this.rankingFinal = rankingFinal;
    }
    public String getPuntos() {
        return puntos;
    }
    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }
}
