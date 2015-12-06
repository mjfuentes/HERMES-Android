package com.mfuentes.hermesmatiasfuentes.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.adapters.AlumnosAdapter;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final ListView listview = (ListView) findViewById(R.id.alumnos_listview);
        final AlumnosAdapter alumnosAdapter = new AlumnosAdapter(this);
        AlumnoDAO.getInstance().addObserver(alumnosAdapter);
        listview.setAdapter(alumnosAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alumno alumno = (Alumno) alumnosAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ALUMNO_ID", alumno.getId());
                startActivity(intent);
            }
        });

        Button b = (Button) findViewById(R.id.nuevo_alumno_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlumnoDAO.getInstance().insertAlumno(getBaseContext(), new Alumno("Matias", "Fuentes", Sexo.MASCULINO, Tamaño.MEDIANO, new ArrayList<Solapa>()));
            }
        });
    }
}
