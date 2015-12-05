package com.mfuentes.hermesmatiasfuentes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mfuentes.hermesmatiasfuentes.Helpers.DBHelper;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Alumno.AlumnoEntry;

import java.util.ArrayList;

public class AlumnoDAO {

    private static AlumnoDAO alumnoDAO;
    public static AlumnoDAO getInstance(){
        if (alumnoDAO == null){
            alumnoDAO = new AlumnoDAO();
        }
        return alumnoDAO;
    }

    public Alumno insertAlumno(Context context, Alumno alumno){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlumnoEntry.COLUMN_NAME_NOMBRE, alumno.getNombre());
        values.put(AlumnoEntry.COLUMN_NAME_APELLIDO, alumno.getApellido());
        values.put(AlumnoEntry.COLUMN_NAME_SEXO, alumno.getSexo().getNumero());
        values.put(AlumnoEntry.COLUMN_NAME_TAMANO_PREFERIDO, alumno.getTamPreferido().getNumero());
        long newRowId = db.insert(AlumnoEntry.TABLE_NAME, null, values);
        alumno.setId(newRowId);
        return alumno;
    }

    public Alumno getAlumno(Long id){
        return new Alumno(Long.valueOf(1),"Matias","Fuentes", Sexo.MASCULINO, Tamaño.MEDIANO,new ArrayList<Solapa>());
    }

}
