package com.mfuentes.hermesmatiasfuentes.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AlumnosAdapter extends BaseAdapter implements Observer {

    private List<Alumno> alumnos;
    private Context context;

    public AlumnosAdapter(Context context){
        this.context = context;
        this.alumnos = AlumnoDAO.getInstance().getAlumnos(context);
    }

    @Override
    public int getCount() {
        return alumnos.size();
    }

    @Override
    public Object getItem(int position) {
        return alumnos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alumnos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }
        text = (TextView) view.findViewById(android.R.id.text1);
        Alumno alumno = (Alumno) getItem(position);
        text.setText(alumno.toString());

        return view;
    }

    @Override
    public void update(Observable observable, Object data) {
        this.alumnos = AlumnoDAO.getInstance().getAlumnos(context);
        notifyDataSetChanged();
    }
}