package com.mfuentes.hermesmatiasfuentes.Activity;

import android.os.Bundle;
import android.app.Activity;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.BaseAdapter;

import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Configuracion;

import java.util.prefs.PreferenceChangeListener;

public class AjustesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (preference.getKey().equals("puerto") || preference.getKey().equals("ip")){
                    CurrentUser.getInstance().updateConfig(preference.getKey(),newValue);
                } else {
                    CurrentUser.getInstance().updateValue(preference.getKey(), newValue);
                }
                if (preference instanceof EditTextPreference){
                    preference.setSummary(((EditTextPreference) preference).getText());
                }
                recreate();
                return true;
            }
        };

        findPreference("sexo").setOnPreferenceChangeListener(listener);
        findPreference("tamano_preferido").setOnPreferenceChangeListener(listener);
        findPreference("nombre").setOnPreferenceChangeListener(listener);
        findPreference("apellido").setOnPreferenceChangeListener(listener);
        findPreference("ip").setOnPreferenceChangeListener(listener);
        findPreference("puerto").setOnPreferenceChangeListener(listener);

        Alumno alumno = CurrentUser.getInstance().getAlumno();
        Configuracion configuracion = CurrentUser.getInstance().getConfiguracion();

        ((ListPreference) findPreference("sexo")).setValue(String.valueOf(alumno.getSexo().getNumero()));
        ((ListPreference) findPreference("tamano_preferido")).setValue(String.valueOf(alumno.getTamPreferido().getNumero()));
        ((EditTextPreference) findPreference("nombre")).setText(alumno.getNombre());
        (findPreference("nombre")).setSummary(alumno.getNombre());
        ((EditTextPreference) findPreference("apellido")).setText(alumno.getApellido());
        (findPreference("apellido")).setSummary(alumno.getApellido());
        ((EditTextPreference) findPreference("ip")).setText(configuracion.getIp());
        (findPreference("ip")).setSummary(configuracion.getIp());
        ((EditTextPreference) findPreference("puerto")).setText(configuracion.getPuerto());
        (findPreference("puerto")).setSummary(configuracion.getPuerto());


    }


}
