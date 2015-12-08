package com.mfuentes.hermesmatiasfuentes.fragments;

import android.app.Fragment;
import android.media.MediaPlayer;
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
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.adapters.PictogramaAdapter;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.List;

public class AlumnoFragment extends Fragment {
    private List<Pictograma> pictogramas;
    private boolean modoAlumno;
    public AlumnoFragment() {
    }

    public static AlumnoFragment newInstance(boolean modoAlumno) {
        AlumnoFragment fragment = new AlumnoFragment();
        fragment.modoAlumno = modoAlumno;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.pictogramas = PictogramaDAO.getInstance().getPictogramas(getActivity(), CurrentUser.getInstance().getPictogramasVisibles());
        GridView gridview;
        View rootView;
        if (modoAlumno){
            rootView = inflater.inflate(R.layout.fragment_alumno, container, false);
            gridview = (GridView) rootView.findViewById(R.id.pictogramas);
        } else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            gridview = (GridView) rootView.findViewById(R.id.gridView);

        }
        gridview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        final PictogramaAdapter adapter = new PictogramaAdapter(getActivity(), pictogramas,true);
        CurrentUser.getInstance().addObserver(adapter);
        gridview.setAdapter(adapter);
        if (!modoAlumno){
            gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    CurrentUser.getInstance().removePictogramaVisible(id);
                    return true;
                }
            });
        } else {
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
                    final int resourceId =  getActivity().getResources().getIdentifier(((Pictograma)adapter.getItem(position)).getAudio(), "raw", getActivity().getPackageName());
                    MediaPlayer mp;
                    mp = MediaPlayer.create(getActivity(), resourceId);
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            mp.reset();
                            mp.release();
                            mp=null;
                        }

                    });
                    mp.start();
                }
            });
        }
        return rootView;
    }
}
