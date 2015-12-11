package com.mfuentes.hermesmatiasfuentes.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.ArraySet;

import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.DAO.CategoriaDAO;
import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Configuracion;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class CurrentUser extends Observable implements Observer{

    private Alumno alumno;
    private Configuracion configuracion;
    private Context contexto;
    private List<Long> pictogramasVisibles;
    private static CurrentUser instance;

    public  List<Long> getPictogramasVisibles() {
        if (pictogramasVisibles == null){
            pictogramasVisibles = PictogramaDAO.getInstance().getVisibles(contexto,alumno);
        }
        return pictogramasVisibles;
    }

    public  List<Pictograma> getPictogramasVisibles(Categoria categoria) {
        return PictogramaDAO.getInstance().getVisibles(contexto,alumno,categoria);
    }

    public CurrentUser(){
        AlumnoDAO.getInstance().addObserver(this);
    }

    public static void setAlumno(Context contexto, Alumno alumno){
        instance = new CurrentUser();
        instance.contexto = contexto;
        instance.alumno = alumno;
    }


    public static CurrentUser getInstance(){
        return instance;
    }

    public void addPictogramaVisible(Long pictograma_id){
        getPictogramasVisibles().add(pictograma_id);
        PictogramaDAO.getInstance().addVisible(contexto, pictograma_id);
        this.setChanged();
        this.notifyObservers();
    }

    public void removePictogramaVisible(Long pictograma_id){
        getPictogramasVisibles().remove(pictograma_id);
        PictogramaDAO.getInstance().removeVisible(contexto, pictograma_id);
        this.setChanged();
        this.notifyObservers();
    }

    public boolean estaVisible(Long pictograma_id){
        return getPictogramasVisibles().contains(pictograma_id);
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Configuracion getConfiguracion() {
        if (configuracion == null){

        }
        return configuracion;
    }

    public void updateValue(String field, Object value){
        AlumnoDAO.getInstance().updateValue(contexto, field, value, alumno.getId());
    }

    public void updateConfig(String field, Object value){
        AlumnoDAO.getInstance().updateConfig(contexto, field, value);
    }

    public void updateCategoriasHabilitadas(Set<String> categorias){
        CategoriaDAO.getInstance().removeCategoriasHabilitadas(contexto, alumno.getId());
        CategoriaDAO.getInstance().addCategoriasHabilitadas(contexto, categorias, alumno.getId());
    }

    public void setConfiguracion(Configuracion configuracion){
        this.configuracion = configuracion;
    }

    public List<Categoria> getCategorias(){
        return AlumnoDAO.getInstance().getCategoriasHabilitadas(contexto, this.alumno.getId());
    }

    public Set<String> getCategoriasSetStrings(){
        List<Categoria> categorias =  AlumnoDAO.getInstance().getCategoriasHabilitadas(contexto, this.alumno.getId());
        Set<String> strings = new HashSet<>();
        for (Categoria c:categorias){
            strings.add(String.valueOf(c.getNumero()));
        }
        return strings;
    }

    public String getCategoriasString(){
        List<Categoria> categorias = getCategorias();
        StringBuilder sb = new StringBuilder();
        for (Categoria c:categorias){
            sb.append(c.toString());
            sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public void update(Observable observable, Object data) {
        this.alumno = AlumnoDAO.getInstance().getAlumno(contexto,this.alumno.getId());
        this.configuracion = AlumnoDAO.getInstance().getConfig(contexto);
    }
}
