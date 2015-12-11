package com.mfuentes.hermesmatiasfuentes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.Helpers.DBHelper;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma.PictogramaEntry;

import java.util.ArrayList;
import java.util.List;

public class PictogramaDAO {

    private static PictogramaDAO pictogramaDAO;
    public static PictogramaDAO getInstance(){
        if (pictogramaDAO == null){
            pictogramaDAO = new PictogramaDAO();
        }
        return pictogramaDAO;
    }

    public List<Pictograma> getPictogramas(Context context, Categoria cat){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                PictogramaEntry._ID,
                PictogramaEntry.COLUMN_NAME_IMAGEN,
                PictogramaEntry.COLUMN_NAME_AUDIO,
                PictogramaEntry.COLUMN_NAME_CATEGORIA,
                PictogramaEntry.COLUMN_NAME_DESCRIPCION,
        };

        Cursor c = db.query(
                PictogramaEntry.TABLE_NAME,
                projection,
                PictogramaEntry.COLUMN_NAME_CATEGORIA +" = ?",
                new String[]{String.valueOf(cat.getNumero())},
                null,
                null,
                null
        );


        List<Pictograma> lista = new ArrayList<>();
        while (c.moveToNext()){
            Categoria categoria= Categoria.fromNumero(c.getInt(3));
            boolean seleccionado = CurrentUser.getInstance().estaVisible(c.getLong(0));
            lista.add( new Pictograma(c.getLong(0), c.getString(1), c.getString(2), c.getString(4), categoria,seleccionado));
        }
        db.close();
        return lista;
    }

    public List<Long> getVisibles(Context context, Alumno alumno){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                "pictograma_id",
        };

        Cursor c = db.query(
                "pictograma_alumno",
                projection,
                "alumno_id = ?",
                new String[]{String.valueOf(alumno.getId())},
                null,
                null,
                null
        );

        List<Long> lista = new ArrayList<>();
        while (c.moveToNext()){
            lista.add(c.getLong(0));
        }
        c.close();
        db.close();
        return lista;
    }

    public List<Pictograma> getVisibles(Context context, Alumno alumno,Categoria categoria){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String MY_QUERY = "SELECT * FROM pictograma a INNER JOIN pictograma_alumno p ON a._id=p.pictograma_id WHERE a.categoria=? AND p.alumno_id=?";

        Cursor c = db.rawQuery(MY_QUERY, new String[]{String.valueOf(categoria.getNumero()),String.valueOf(alumno.getId())});

        List<Pictograma> lista = new ArrayList<>();
        while (c.moveToNext()){
            lista.add( new Pictograma(c.getLong(0),c.getString(3),c.getString(4), c.getString(1), categoria, true));
        }
        c.close();
        db.close();
        return lista;
    }

    public Pictograma insertPictograma(Context context, Pictograma pictograma){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PictogramaEntry.COLUMN_NAME_IMAGEN, pictograma.getImagen());
        values.put(PictogramaEntry.COLUMN_NAME_AUDIO, pictograma.getAudio());
        values.put(PictogramaEntry.COLUMN_NAME_CATEGORIA, pictograma.getCategoria().getNumero());
        values.put(PictogramaEntry.COLUMN_NAME_DESCRIPCION, pictograma.getDescripcion());
        long newRowId = db.insert(PictogramaEntry.TABLE_NAME, null, values);
        pictograma.setId(newRowId);
        db.close();
        return pictograma;
    }

    public void addVisible(Context context, Long pictograma_id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alumno_id", CurrentUser.getInstance().getAlumno().getId());
        values.put("pictograma_id", pictograma_id);
        db.insert("pictograma_alumno", null, values);
        db.close();
    }

    public void removeVisible(Context context, Long pictograma_id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete("pictograma_alumno", "pictograma_id=" + pictograma_id + " AND alumno_id=" + CurrentUser.getInstance().getAlumno().getId(), null);
        db.close();
    }

    public Pictograma getPictograma(Context context, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                PictogramaEntry._ID,
                PictogramaEntry.COLUMN_NAME_IMAGEN,
                PictogramaEntry.COLUMN_NAME_AUDIO,
                PictogramaEntry.COLUMN_NAME_CATEGORIA,
                PictogramaEntry.COLUMN_NAME_DESCRIPCION,
        };

        Cursor c = db.query(
                PictogramaEntry.TABLE_NAME,
                projection,
                PictogramaEntry._ID +" = ?",
                new String[]{id.toString()},
                null,
                null,
                null
        );

        if (c.moveToFirst()){
            Categoria categoria= Categoria.fromNumero(c.getInt(3));
            boolean seleccionado = CurrentUser.getInstance().estaVisible(c.getLong(0));
            return new Pictograma(c.getLong(0), c.getString(1), c.getString(2), c.getString(4), categoria,seleccionado);
        }
        db.close();
        return null;
    }

    public List<Pictograma> getPictogramas(Context contexto, List<Long> ids){
        List<Pictograma> pictogramas = new ArrayList<>();
        for (Long id:ids){
            pictogramas.add(getPictograma(contexto,id));
        }
        return pictogramas;
    }

}
