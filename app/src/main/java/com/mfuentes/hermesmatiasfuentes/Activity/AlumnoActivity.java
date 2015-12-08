package com.mfuentes.hermesmatiasfuentes.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.DAO.CategoriaDAO;
import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.fragments.CategoriaAlumnoFragment;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Configuracion;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AlumnoActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ActionBar.TabListener tabListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Long alumno_id = intent.getLongExtra("ALUMNO_ID", (long) 0);
        Alumno alumno = AlumnoDAO.getInstance().getAlumno(this, alumno_id);
        Configuracion configuracion = AlumnoDAO.getInstance().getConfig(this,alumno_id);
        CurrentUser.setAlumno(getBaseContext(), alumno);
        CurrentUser.getInstance().setConfiguracion(configuracion);
        actionBar.setTitle(alumno.getNombre() + " " + alumno.getApellido());
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        CategoriaDAO.getInstance().addObserver(mSectionsPagerAdapter);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tabListener = new ActionBar.TabListener() {
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
    protected void onPostResume() {
        super.onPostResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.removeAllTabs();
        Alumno alumno = CurrentUser.getInstance().getAlumno();
        getSupportActionBar().setTitle(alumno.getNombre() + " " + alumno.getApellido());
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
        if (id == R.id.action_terapeuta){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), AjustesActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter implements Observer {

        List<Categoria> categorias = CurrentUser.getInstance().getCategorias();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            if (position < categorias.size()){
                return CategoriaAlumnoFragment.newInstance(categorias.get(position));
            } else {
                return CategoriaAlumnoFragment.newInstance(null);
            }
        }

        @Override
        public int getCount() {
            return categorias.size() + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position < categorias.size()){
                return categorias.get(position).toString();
            } else {
                return CurrentUser.getInstance().getAlumno().getNombre().toUpperCase();
            }
        }

        @Override
        public void update(Observable observable, Object data) {
            categorias = CurrentUser.getInstance().getCategorias();
            notifyDataSetChanged();
        }
    }
}
