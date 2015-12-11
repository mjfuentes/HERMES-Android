package com.mfuentes.hermesmatiasfuentes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.Helpers.BitmapUtils;
import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class PictogramaAdapter extends BaseAdapter implements Observer{
    private Context context;
    private List<Pictograma> pictogramas;
    private Categoria categoria;
    private boolean alumno;
    private boolean edicion;

    public PictogramaAdapter(Context context, List<Pictograma> pictogramas,boolean alumno, Categoria categoria,boolean edicion){
        this.context = context;
        this.pictogramas = pictogramas;
        this.alumno = alumno;
        this.categoria = categoria;
        this.edicion = edicion;
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
        int size = 200;
        if (alumno){
            size = CurrentUser.getInstance().getAlumno().getTamPreferido().getSize();
            RelativeLayout.LayoutParams layoutParams  = new
                    RelativeLayout.LayoutParams(size, size);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            layoutParams.setMargins(10,10,10,10);
            imagen.setLayoutParams(layoutParams);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        imagen.setImageBitmap(BitmapUtils.resizeBitmap(bitmap,size,size));
        if (((Pictograma)getItem(position)).isSeleccionado() && !this.alumno && !this.edicion){
            view.setBackgroundColor(Color.BLUE);
        }
        return view;
    }

    @Override
    public void update(Observable observable, Object data) {
        if ((alumno && (categoria == null)) || edicion){
            this.pictogramas = PictogramaDAO.getInstance().getPictogramas(context,CurrentUser.getInstance().getPictogramasVisibles());
        } else if (alumno){
            this.pictogramas = CurrentUser.getInstance().getPictogramasVisibles(categoria);
        }
        notifyDataSetChanged();
    }
}
