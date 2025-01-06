package org.pruden.entities;


import org.pruden.entities.auxClass.PartidaId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "partidas")
public class Partida {

    @EmbeddedId
    private PartidaId id;

    @Column(nullable = false)
    private String resultado;

    @Column(nullable = false)
    private int mesa;

    @Column
    private String pgn;

    public Partida(PartidaId id, String resultado, int mesa, String pgn) {
        this.id = id;
        this.resultado = resultado;
        this.mesa = mesa;
        this.pgn = pgn;
    }

    public Partida() {}

    public PartidaId getId() {
        return id;
    }

    public void setId(PartidaId id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getPgn() {
        return pgn;
    }
    public void setPgn(String pgn) {}
}

