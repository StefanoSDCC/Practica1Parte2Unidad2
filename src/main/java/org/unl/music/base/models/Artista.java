package org.unl.music.base.models;

public class Artista {
    private Integer id;
    private String nombres;
    private String nacionidad;
    private RolArtistaEnum roll;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNacionidad() {
        return this.nacionidad;
    }

    public void setNacionidad(String nacionidad) {
        this.nacionidad = nacionidad;
    }

    public RolArtistaEnum getRoll() {
        return this.roll;
    }

    public void setRoll(RolArtistaEnum roll) {
        this.roll = roll;
    }
}
