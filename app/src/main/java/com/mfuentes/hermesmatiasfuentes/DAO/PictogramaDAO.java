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
            lista.add( new Pictograma(c.getLong(0), c.getString(1), c.getString(2), c.getString(4), categoria));

        }

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
        return pictograma;
    }

    public Pictograma getPictograma(Context context, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                PictogramaEntry._ID,
                PictogramaEntry.COLUMN_NAME_AUDIO,
                PictogramaEntry.COLUMN_NAME_IMAGEN,
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
            return new Pictograma(c.getLong(0), c.getString(1), c.getString(2), c.getString(4), categoria);
        }

        return null;

    }

}
