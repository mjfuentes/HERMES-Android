package com.mfuentes.hermesmatiasfuentes.model;

import android.provider.BaseColumns;

import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;

import java.util.ArrayList;
import java.util.List;

public class Alumno {


    private Long id;
    private String nombre;
    private String apellido;
    private Sexo sexo;
    private Tamaño tamPreferido;
    private List<Solapa> solapasHabilitadas;

    public Alumno(String nombre, String apellido,Sexo sexo){
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.tamPreferido = Tamaño.MEDIANO;
        this.solapasHabilitadas = new ArrayList<>();
    }

    public Alumno(Long id, String nombre, String apellido,Sexo sexo, Tamaño tamPreferido, List<Solapa> solapas){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.tamPreferido = tamPreferido;
        this.solapasHabilitadas = solapas;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Tamaño getTamPreferido() {
        return tamPreferido;
    }

    public List<Solapa> getSolapasHabilitadas() {
        return solapasHabilitadas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }

    public static abstract class AlumnoEntry implements BaseColumns {
        public static final String TABLE_NAME = "alumno";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_APELLIDO = "apellido";
        public static final String COLUMN_NAME_SEXO = "sexo";
        public static final String COLUMN_NAME_TAMANO_PREFERIDO = "tamano_preferido";
    }

}
