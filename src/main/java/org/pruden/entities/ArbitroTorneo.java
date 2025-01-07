package org.pruden.entities;

import org.pruden.entities.auxClass.ArbitroTorneoId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "arbitro_torneo")
public class ArbitroTorneo {

    @EmbeddedId
    private ArbitroTorneoId id;

    @ManyToOne
    @MapsId("idArbitro")
    @JoinColumn(name = "id_arbitro", referencedColumnName = "codigo")
    private Arbitro arbitro;

    @ManyToOne
    @MapsId("idTorneo")
    @JoinColumn(name = "id_torneo", referencedColumnName = "id")
    private Torneo torneo;

    // Constructor vacío obligatorio para JPA
    public ArbitroTorneo() {}

    // Constructor completo
    public ArbitroTorneo(Arbitro arbitro, Torneo torneo) {
        this.id = new ArbitroTorneoId(arbitro.getCodigo(), torneo.getId());
        this.arbitro = arbitro;
        this.torneo = torneo;
    }

    // Getters y Setters
    public ArbitroTorneoId getId() {
        return id;
    }

    public void setId(ArbitroTorneoId id) {
        this.id = id;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    @Override
    public String toString() {
        return "ArbitroTorneo{" +
                "id=" + id +
                ", arbitro=" + arbitro +
                ", torneo=" + torneo +
                '}';
    }
}
