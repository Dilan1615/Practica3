package com.tercerotest.models;

import com.tercerotest.models.enumerator.TipoEnergia;

public class Proyecto {
    private Integer id;
    private String nombre;
    private String inversionista;
    private Float inversion;
    private String tiempoVida;
    private String inicio;
    private String fin;
    private TipoEnergia tipo;
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getInversionista() {
        return inversionista;
    }
    public void setInversionista(String inversionista) {
        this.inversionista = inversionista;
    }
    public Float getInversion() {
        return inversion;
    }
    public void setInversion(Float inversion) {
        this.inversion = inversion;
    }
    public String getTiempoVida() {
        return tiempoVida;
    }
    public void setTiempoVida(String tiempoVida) {
        this.tiempoVida = tiempoVida;
    }
    public String getInicio() {
        return inicio;
    }
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }
    public String getFin() {
        return fin;
    }
    public void setFin(String fin) {
        this.fin = fin;
    }
    public TipoEnergia getTipo() {
        return tipo;
    }
    public void setTipo(TipoEnergia tipo) {
        this.tipo = tipo;
    }
    
    
    public String toString() {
        return inversionista+""+inversion;
    };
}

