package com.mfuentes.hermesmatiasfuentes.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.model.Alumno.AlumnoEntry;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma.PictogramaEntry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String CREATE_TABLE_ALUMNO =
            "CREATE TABLE " + AlumnoEntry.TABLE_NAME + " (" +
                    AlumnoEntry._ID + " INTEGER PRIMARY KEY," +
                    AlumnoEntry.COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    AlumnoEntry.COLUMN_NAME_APELLIDO + TEXT_TYPE + COMMA_SEP +
                    AlumnoEntry.COLUMN_NAME_SEXO + INTEGER_TYPE + COMMA_SEP +
                    AlumnoEntry.COLUMN_NAME_TAMANO_PREFERIDO + INTEGER_TYPE +
                    " );";
    private static final String CREATE_TABLE_PICTOGRAMA =
            "CREATE TABLE " + PictogramaEntry.TABLE_NAME + " (" +
                    PictogramaEntry._ID + " INTEGER PRIMARY KEY," +
                    PictogramaEntry.COLUMN_NAME_DESCRIPCION + TEXT_TYPE + COMMA_SEP +
                    PictogramaEntry.COLUMN_NAME_CATEGORIA + INTEGER_TYPE + COMMA_SEP +
                    PictogramaEntry.COLUMN_NAME_IMAGEN + TEXT_TYPE + COMMA_SEP +
                    PictogramaEntry.COLUMN_NAME_AUDIO + TEXT_TYPE +
                    " ); ";

    private static final String CREATE_TABLE_PICTOGRAMA_ALUMNO =
            "CREATE TABLE pictograma_alumno (_id INTEGER PRIMARY KEY, pictograma_id INTEGER, alumno_id INTEGER);";

    private static final String CREATE_TABLE_CONFIGURACION =
            "CREATE TABLE configuracion (_id INTEGER PRIMARY KEY, ip TEXT, puerto TEXT);";

    private static final String CREATE_TABLE_CATEGORIA_ALUMNO =
            "CREATE TABLE categoria_alumno (_id INTEGER PRIMARY KEY, categoria_id INTEGER, alumno_id INTEGER);";

    public DBHelper(Context context) {
        super(context, "database.db", null, 3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALUMNO);
        db.execSQL(CREATE_TABLE_PICTOGRAMA);
        db.execSQL(CREATE_TABLE_PICTOGRAMA_ALUMNO);
        db.execSQL(CREATE_TABLE_CONFIGURACION);
        db.execSQL(CREATE_TABLE_CATEGORIA_ALUMNO);
        try {
            populateConfiguracion(db);
            populatePictogramas(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AlumnoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PictogramaEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS pictograma_alumno");
        db.execSQL("DROP TABLE IF EXISTS categoria_alumno");
        db.execSQL("DROP TABLE IF EXISTS configuracion");
        onCreate(db);
    }

    private void populatePictogramas(SQLiteDatabase db) throws IOException {
        InputStream csvStream = context.getAssets().open("pictogramas.csv");
        InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
        BufferedReader buffer = new BufferedReader(csvStreamReader);
        String line = "";
        String tableName = "pictograma";
        String columns = "descripcion, categoria, imagen, audio";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        while ((line = buffer.readLine()) != null) {
            StringBuilder sb = new StringBuilder(str1);
            String[] str = line.split(";");
            sb.append("'" + str[2] + "',");
            sb.append(str[3] + ",");
            sb.append("'" + str[0] + "',");
            sb.append("'" + str[1] + "'");
            sb.append(str2);
            db.execSQL(sb.toString());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void populateConfiguracion(SQLiteDatabase db){
        String query = "INSERT INTO configuracion (ip,puerto) values('0.0.0.0','8080')";
        db.execSQL(query);
    }
}
