package com.mfuentes.hermesmatiasfuentes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.List;

public class PictogramaAdapter extends BaseAdapter {
    private Context context;
    private List<Pictograma> pictogramas;

    public PictogramaAdapter(Context context, List<Pictograma> pictogramas){
        this.context = context;
        this.pictogramas = pictogramas;
    }

    @Override
    public int getCount() {
        return pictogramas.size();
    }

    @Override
    public Object getItem(int position) {
        return pictogramas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pictogramas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            view = inflater.inflate(R.layout.pictograma, parent, false);
        } else {
            view = convertView;
        }
        ImageView imagen = (ImageView) view.findViewById(R.id.imagen_pictograma);
        final int resourceId =  context.getResources().getIdentifier(pictogramas.get(position).getImagen(), "drawable", context.getPackageName());
        imagen.setImageResource(resourceId);
        return view;
    }
}
