package com.mfuentes.hermesmatiasfuentes.Activity;


import android.app.Activity;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.adapters.PictogramaAdapter;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.enums.Sexo;
import com.mfuentes.hermesmatiasfuentes.enums.Solapa;
import com.mfuentes.hermesmatiasfuentes.enums.Tamaño;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        Intent intent = getIntent();
        Long alumno_id = intent.getLongExtra("ALUMNO_ID", (long) 0);
        Alumno alumno = AlumnoDAO.getInstance().getAlumno(this,alumno_id);
        actionBar.setTitle(alumno.getNombre() + " " + alumno.getApellido());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {}
            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {}

        };

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(tabListener));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private List<Pictograma> pictogramas;
        private Categoria categoria;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(Context context, int sectionNumber, Categoria categoria) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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
            GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
            gridview.setAdapter(new PictogramaAdapter(getActivity(),pictogramas));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Categoria[] categorias = Categoria.values();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(getBaseContext(),position + 1, categorias[position]);
        }

        @Override
        public int getCount() {
            return categorias.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categorias[position].toString();
        }

    }
}
