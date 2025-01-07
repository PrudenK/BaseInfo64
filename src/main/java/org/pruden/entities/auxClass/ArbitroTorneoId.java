package org.pruden.entities.auxClass;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ArbitroTorneoId implements Serializable {

    @Column(name = "id_arbitro")
    private String idArbitro;

    @Column(name = "id_torneo")
    private String idTorneo;

    // Constructor vacío obligatorio para JPA
    public ArbitroTorneoId() {}

    // Constructor completo
    public ArbitroTorneoId(String idArbitro, String idTorneo) {
        this.idArbitro = idArbitro;
        this.idTorneo = idTorneo;
    }

    // Getters y Setters
    public String getIdArbitro() {
        return idArbitro;
    }

    public void setIdArbitro(String idArbitro) {
        this.idArbitro = idArbitro;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    // Métodos equals y hashCode (obligatorios para claves primarias compuestas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArbitroTorneoId that = (ArbitroTorneoId) o;
        return Objects.equals(idArbitro, that.idArbitro) && Objects.equals(idTorneo, that.idTorneo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArbitro, idTorneo);
    }
}
