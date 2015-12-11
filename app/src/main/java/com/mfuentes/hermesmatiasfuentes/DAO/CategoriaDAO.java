package com.mfuentes.hermesmatiasfuentes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mfuentes.hermesmatiasfuentes.Helpers.DBHelper;

import java.util.Observable;
import java.util.Set;

public class CategoriaDAO{

    private static CategoriaDAO instance;
    public static CategoriaDAO getInstance(){
        if (instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

    public void removeCategoriasHabilitadas(Context context,Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete("categoria_alumno", "alumno_id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void addCategoriaHabilitada(Context context,Integer categoria, Long id){
        DBHelper mDBHelper = new DBHelper(context);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("categoria_id", categoria);
        values.put("alumno_id", id);
        db.insert("categoria_alumno", null, values);
        db.close();

    }

    public void addCategoriasHabilitadas(Context contexto, Set<String> categorias, Long id){
        for (String s:categorias){
            this.addCategoriaHabilitada(contexto, Integer.valueOf(s), id);
        }
    }
}
