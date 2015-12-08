package com.mfuentes.hermesmatiasfuentes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mfuentes.hermesmatiasfuentes.Helpers.DBHelper;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Alumno.AlumnoEntry;
import com.mfuentes.hermesmatiasfuentes.model.Configuracion;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        alumno.setId(newRowId);
        ContentValues config = new ContentValues();
        config.put("alumno_id", alumno.getId());
        db.insert("configuracion", null, config);
        db.close();
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

    public Configuracion getConfig(Context context, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                "ip",
                "puerto"
        };

        Cursor c = db.query(
                "configuracion",
                projection,
                "alumno_id = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );

        if (c.moveToFirst()){
            db.close();
            return new Configuracion(c.getString(0),c.getString(1));
        }

        return null;
    }

    public List<Categoria> getCategoriasHabilitadas(Context context, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                "categoria_id",
        };

        Cursor c = db.query(
                "categoria_alumno",
                projection,
                "alumno_id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        List<Categoria> lista = new ArrayList<>();
        while (c.moveToNext()){
            lista.add(Categoria.fromNumero(c.getInt(0)));
        }
        c.close();
        db.close();
        return lista;
    }



    public void updateValue(Context context, String field, Object value, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String valor = (String) value;
        String strFilter = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put(field, valor);
        db.update("alumno", args, strFilter, null);
        db.close();
        setChanged();
        notifyObservers();
    }

    public void updateConfig(Context context, String field, Object value, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String valor = (String) value;
        String strFilter = "alumno_id=" + id;
        ContentValues args = new ContentValues();
        args.put(field, valor);
        db.update("configuracion", args, strFilter, null);
        db.close();
        setChanged();
        notifyObservers();
    }

}
