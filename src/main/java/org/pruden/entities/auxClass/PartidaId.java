package org.pruden.entities.auxClass;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaId implements Serializable {

    @Column(name = "id_torneo", nullable = false)
    private String idTorneo;

    @Column(name = "id_jugador_blancas", nullable = false)
    private String idJugadorBlancas;

    @Column(name = "id_jugador_negras", nullable = false)
    private String idJugadorNegras;

    @Column(nullable = false)
    private int ronda;

    public PartidaId() {}

    public PartidaId(String idTorneo, String idJugadorBlancas, String idJugadorNegras, int ronda) {
        this.idTorneo = idTorneo;
        this.idJugadorBlancas = idJugadorBlancas;
        this.idJugadorNegras = idJugadorNegras;
        this.ronda = ronda;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getIdJugadorBlancas() {
        return idJugadorBlancas;
    }

    public void setIdJugadorBlancas(String idJugadorBlancas) {
        this.idJugadorBlancas = idJugadorBlancas;
    }

    public String getIdJugadorNegras() {
        return idJugadorNegras;
    }

    public void setIdJugadorNegras(String idJugadorNegras) {
        this.idJugadorNegras = idJugadorNegras;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaId partidaId = (PartidaId) o;
        return ronda == partidaId.ronda &&
                Objects.equals(idTorneo, partidaId.idTorneo) &&
                Objects.equals(idJugadorBlancas, partidaId.idJugadorBlancas) &&
                Objects.equals(idJugadorNegras, partidaId.idJugadorNegras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTorneo, idJugadorBlancas, idJugadorNegras, ronda);
    }
}
