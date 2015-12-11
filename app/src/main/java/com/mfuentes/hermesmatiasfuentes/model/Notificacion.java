package com.mfuentes.hermesmatiasfuentes.model;


import java.util.Date;

public class Notificacion {

    public String contenido;

    public String contexto;

    public String categoria;

    public Date fecha_envio;

    public String nene;

    public Notificacion(){};

    public Notificacion(String contenido, String contexto, String categoria, Date fecha_envio, String nene) {
        this.contenido = contenido;
        this.contexto = contexto;
        this.categoria = categoria;
        this.fecha_envio = fecha_envio;
        this.nene = nene;
    }

    public String getContenido() {
        return contenido;
    }

    public String getContexto() {
        return contexto;
    }

    public String getCategoria() {
        return categoria;
    }

    public Date getFecha_envio() {
        return fecha_envio;
    }

    public String getNene() {
        return nene;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public void setNene(String nene) {
        this.nene = nene;
    }
}