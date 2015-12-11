package com.mfuentes.hermesmatiasfuentes.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Long alumno_id = intent.getLongExtra("ALUMNO_ID", (long) 0);
        Alumno alumno = AlumnoDAO.getInstance().getAlumno(this, alumno_id);
        Configuracion configuracion = AlumnoDAO.getInstance().getConfig(this);
        CurrentUser.setAlumno(getBaseContext(), alumno);
        CurrentUser.getInstance().setConfiguracion(configuracion);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {}
            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            }

        };
        init();
    }

    private void init(){
        mSectionsPagerAdapter.refresh();
        mSectionsPagerAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final ActionBar actionBar = getSupportActionBar();
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        actionBar.removeAllTabs();
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(tabListener));
        }
        actionBar.setTitle(CurrentUser.getInstance().getAlumno().getNombre() + " " + CurrentUser.getInstance().getAlumno().getApellido());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_terapeuta:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(getApplicationContext(), AjustesActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        List<Categoria> categorias;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            categorias = CurrentUser.getInstance().getCategorias();

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

        public void refresh(){
            categorias = CurrentUser.getInstance().getCategorias();
        }

    }
}
