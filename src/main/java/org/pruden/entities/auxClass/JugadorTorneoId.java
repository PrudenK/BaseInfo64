package org.pruden.entities.auxClass;

import java.io.Serializable;
import java.util.Objects;

public class JugadorTorneoId implements Serializable {

    private String idTorneo;
    private String idJugador;

    public JugadorTorneoId() {}

    public JugadorTorneoId(String idTorneo, String idJugador) {
        this.idTorneo = idTorneo;
        this.idJugador = idJugador;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JugadorTorneoId that = (JugadorTorneoId) o;
        return Objects.equals(idTorneo, that.idTorneo) &&
                Objects.equals(idJugador, that.idJugador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTorneo, idJugador);
    }
}

