package com.mfuentes.hermesmatiasfuentes.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.adapters.PictogramaAdapter;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.List;

public class PlaceholderFragment extends Fragment {
    private List<Pictograma> pictogramas;
    private GridView gridview;

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(Categoria categoria) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt("CATEGORIA",categoria.getNumero());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        Categoria categoria = Categoria.fromNumero(args.getInt("CATEGORIA", 1));
        this.pictogramas = PictogramaDAO.getInstance().getPictogramas(getActivity(), categoria);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridview = (GridView) rootView.findViewById(R.id.gridView);
        gridview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        final PictogramaAdapter adapter = new PictogramaAdapter(getActivity(), pictogramas,false);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!CurrentUser.getInstance().estaVisible(id)) {
                    view.setBackgroundColor(Color.BLUE);
                    CurrentUser.getInstance().addPictogramaVisible(id);
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    CurrentUser.getInstance().removePictogramaVisible(id);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        gridview.invalidate();
        super.onResume();
    }
}
