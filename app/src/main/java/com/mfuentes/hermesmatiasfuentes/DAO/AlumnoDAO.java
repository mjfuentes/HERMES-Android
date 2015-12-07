package com.mfuentes.hermesmatiasfuentes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mfuentes.hermesmatiasfuentes.Helpers.DBHelper;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Alumno.AlumnoEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AlumnoDAO extends Observable{

    private static AlumnoDAO alumnoDAO;
    public static AlumnoDAO getInstance(){
        if (alumnoDAO == null){
            alumnoDAO = new AlumnoDAO();
        }
        return alumnoDAO;
    }

    public List<Alumno> getAlumnos(Context context){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                AlumnoEntry._ID,
                AlumnoEntry.COLUMN_NAME_NOMBRE,
                AlumnoEntry.COLUMN_NAME_APELLIDO,
                AlumnoEntry.COLUMN_NAME_SEXO,
                AlumnoEntry.COLUMN_NAME_TAMANO_PREFERIDO,
        };

        Cursor c = db.query(
                AlumnoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<Alumno> lista = new ArrayList<>();
        while (c.moveToNext()){
            Sexo sexo = Sexo.fromNumero(c.getInt(3));
            Tamaño tam = Tamaño.fromNumero(c.getInt(4));
            lista.add(new Alumno(c.getLong(0), c.getString(1), c.getString(2), sexo, tam, new ArrayList<Solapa>()));
        }

        db.close();
        return lista;
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
        db.close();
        alumno.setId(newRowId);
        this.setChanged();
        this.notifyObservers();
        return alumno;
    }

    public Alumno getAlumno(Context context, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                AlumnoEntry._ID,
                AlumnoEntry.COLUMN_NAME_NOMBRE,
                AlumnoEntry.COLUMN_NAME_APELLIDO,
                AlumnoEntry.COLUMN_NAME_SEXO,
                AlumnoEntry.COLUMN_NAME_TAMANO_PREFERIDO,
        };

        Cursor c = db.query(
                AlumnoEntry.TABLE_NAME,
                projection,
                AlumnoEntry._ID +" = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );

        if (c.moveToFirst()){
            Sexo sexo = Sexo.fromNumero(c.getInt(3));
            Tamaño tam = Tamaño.fromNumero(c.getInt(4));
            db.close();
            return new Alumno(c.getLong(0), c.getString(1), c.getString(2), sexo, tam, new ArrayList<Solapa>());
        }

        return null;

    }

}
