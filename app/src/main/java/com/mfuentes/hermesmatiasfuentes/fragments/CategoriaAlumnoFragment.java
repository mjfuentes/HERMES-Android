package com.mfuentes.hermesmatiasfuentes.fragments;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.Helpers.JsonSender;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.adapters.PictogramaAdapter;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.List;

public class CategoriaAlumnoFragment extends Fragment {
    private List<Pictograma> pictogramas;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    private Categoria categoria;
    public CategoriaAlumnoFragment() {
    }

    public static CategoriaAlumnoFragment newInstance(Categoria categoria) {
        CategoriaAlumnoFragment fragment = new CategoriaAlumnoFragment();
        fragment.categoria = categoria;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.categoria != null){
            this.pictogramas = CurrentUser.getInstance().getPictogramasVisibles(categoria);
        } else {
            this.pictogramas = PictogramaDAO.getInstance().getPictogramas(getActivity(), CurrentUser.getInstance().getPictogramasVisibles());
        }
        GridView gridview;
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_alumno, container, false);
        gridview = (GridView) rootView.findViewById(R.id.pictogramas);
        gridview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        gridview.setNumColumns(CurrentUser.getInstance().getAlumno().getTamPreferido().getColumnas());
        final PictogramaAdapter adapter = new PictogramaAdapter(getActivity(), pictogramas,true,this.categoria,false);
        CurrentUser.getInstance().addObserver(adapter);
        gridview.setAdapter(adapter);
        ImageButton si = (ImageButton) rootView.findViewById(R.id.imagen_si);
        ImageButton no = (ImageButton) rootView.findViewById(R.id.imagen_no);
        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp;
                mp = MediaPlayer.create(getActivity(), R.raw.si);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                        mp=null;
                    }

                });
                mp.start();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp;
                mp = MediaPlayer.create(getActivity(), R.raw.no);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                        mp=null;
                    }

                });
                mp.start();
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // MANDAR DATOS AL SERVER
                Pictograma pictograma = (Pictograma)adapter.getItem(position);
                final int resourceId =  getActivity().getResources().getIdentifier(pictograma.getAudio(), "raw", getActivity().getPackageName());
                MediaPlayer mp;
                mp = MediaPlayer.create(getActivity(), resourceId);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                        mp = null;
                    }

                });
                mp.start();
                AsyncTask task = new JsonSender(pictograma.getDescripcion(),pictograma.getCategoria().toString(),pictograma.getCategoria().toString(),CurrentUser.getInstance().getAlumno().toString());
                task.execute();
            }
        });
        return rootView;
    }
}
