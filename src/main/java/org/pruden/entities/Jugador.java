package org.pruden.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jugadores") // Nombre de la tabla en la base de datos
public class Jugador {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "elo", nullable = false)
    private int elo;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "federacion", nullable = false)
    private String federacion;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "bYear", nullable = false)
    private int bYear;

    // Constructor vacío obligatorio para JPA
    public Jugador() {}

    // Constructor completo
    public Jugador(Long id, String nombre, String apellidos, int elo, String sexo, String federacion, String titulo, int bYear) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.elo = elo;
        this.sexo = sexo;
        this.federacion = federacion;
        this.titulo = titulo;
        this.bYear = bYear;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFederacion() {
        return federacion;
    }

    public void setFederacion(String federacion) {
        this.federacion = federacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getBYear() {
        return bYear;
    }

    public void setBYear(int bYear) {
        this.bYear = bYear;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", elo=" + elo +
                ", sexo='" + sexo + '\'' +
                ", federacion='" + federacion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", bYear=" + bYear +
                '}';
    }
}
