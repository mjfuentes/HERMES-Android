package com.mfuentes.hermesmatiasfuentes.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mfuentes.hermesmatiasfuentes.model.Alumno;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Alumno.AlumnoEntry.TABLE_NAME + " (" +
                    Alumno.AlumnoEntry._ID + " INTEGER PRIMARY KEY," +
                    Alumno.AlumnoEntry.COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    Alumno.AlumnoEntry.COLUMN_NAME_APELLIDO + TEXT_TYPE + COMMA_SEP +
                    Alumno.AlumnoEntry.COLUMN_NAME_SEXO + INTEGER_TYPE + COMMA_SEP +
                    Alumno.AlumnoEntry.COLUMN_NAME_TAMANO_PREFERIDO + INTEGER_TYPE + COMMA_SEP +
            " )";

    public DBHelper(Context context) {
        super(context, "Hermes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
