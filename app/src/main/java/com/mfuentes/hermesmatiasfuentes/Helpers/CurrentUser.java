package com.mfuentes.hermesmatiasfuentes.Helpers;

import android.content.Context;

import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.List;
import java.util.Observable;

public class CurrentUser extends Observable{

    private Alumno alumno;
    private Context contexto;
    private List<Long> pictogramasVisibles;
    private static CurrentUser instance;
    public  List<Long> getPictogramasVisibles() {
        if (pictogramasVisibles == null){
            pictogramasVisibles = PictogramaDAO.getInstance().getVisibles(contexto,alumno);
        }
        return pictogramasVisibles;
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

}
