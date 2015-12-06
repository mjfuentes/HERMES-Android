package com.mfuentes.hermesmatiasfuentes.model;

import android.provider.BaseColumns;

import com.mfuentes.hermesmatiasfuentes.enums.Categoria;

public class Pictograma {


    private Long id;
    private String imagen;
    private String audio;
    private String descripcion;
    private Categoria categoria;

    public Pictograma(Long id, String imagen, String audio, String descripcion, Categoria categoria) {
        this.id = id;
        this.imagen = imagen;
        this.audio = audio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Pictograma(String imagen, String audio, String descripcion, Categoria categoria) {
        this.imagen = imagen;
        this.audio = audio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }


    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }

    public String getAudio() {
        return audio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public static abstract class PictogramaEntry implements BaseColumns {
        public static final String TABLE_NAME = "pictograma";
        public static final String COLUMN_NAME_DESCRIPCION = "descripcion";
        public static final String COLUMN_NAME_IMAGEN = "imagen";
        public static final String COLUMN_NAME_AUDIO = "audio";
        public static final String COLUMN_NAME_CATEGORIA = "categoria";
    }
}
