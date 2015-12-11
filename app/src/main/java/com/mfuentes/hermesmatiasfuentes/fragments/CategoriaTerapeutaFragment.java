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

public class CategoriaTerapeutaFragment extends Fragment {
    private List<Pictograma> pictogramas;
    public CategoriaTerapeutaFragment() {
    }

    public static CategoriaTerapeutaFragment newInstance() {
        CategoriaTerapeutaFragment fragment = new CategoriaTerapeutaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.pictogramas = PictogramaDAO.getInstance().getPictogramas(getActivity(), CurrentUser.getInstance().getPictogramasVisibles());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
        gridview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        final PictogramaAdapter adapter = new PictogramaAdapter(getActivity(), pictogramas,false,null,true);
        CurrentUser.getInstance().addObserver(adapter);
        gridview.setAdapter(adapter);
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CurrentUser.getInstance().removePictogramaVisible(id);
                return true;
            }
        });
        return rootView;
    }
}
