package com.mfuentes.hermesmatiasfuentes.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;

public class NuevoAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_alumno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button save = (Button) findViewById(R.id.save_button);
        final EditText nombre = (EditText) findViewById(R.id.nombre);
        final EditText apellido = (EditText) findViewById(R.id.apellido);
        final RadioButton masculino = (RadioButton) findViewById(R.id.masculino);
        masculino.setChecked(true);
        final RadioButton femenino = (RadioButton) findViewById(R.id.femenino);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().trim().equals("") && !apellido.getText().toString().equals("") && (masculino.isChecked() || femenino.isChecked())){
                    Sexo sexo = (masculino.isChecked()) ? Sexo.MASCULINO : Sexo.FEMENINO;
                    AlumnoDAO.getInstance().insertAlumno(getBaseContext(),new Alumno(nombre.getText().toString(),apellido.getText().toString(),sexo));
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Faltan completar campos!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

}
