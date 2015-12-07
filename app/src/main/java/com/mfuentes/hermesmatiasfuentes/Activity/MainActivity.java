package com.mfuentes.hermesmatiasfuentes.Activity;


import android.app.Activity;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import com.mfuentes.hermesmatiasfuentes.DAO.AlumnoDAO;
import com.mfuentes.hermesmatiasfuentes.DAO.PictogramaDAO;
import com.mfuentes.hermesmatiasfuentes.Helpers.CurrentUser;
import com.mfuentes.hermesmatiasfuentes.R;
import com.mfuentes.hermesmatiasfuentes.adapters.PictogramaAdapter;
import com.mfuentes.hermesmatiasfuentes.enums.Categoria;
import com.mfuentes.hermesmatiasfuentes.fragments.PlaceholderFragment;
import com.mfuentes.hermesmatiasfuentes.fragments.UserFragment;
import com.mfuentes.hermesmatiasfuentes.model.Alumno;
import com.mfuentes.hermesmatiasfuentes.model.Pictograma;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Long alumno_id = intent.getLongExtra("ALUMNO_ID", (long) 0);
        Alumno alumno = AlumnoDAO.getInstance().getAlumno(this,alumno_id);
        CurrentUser.setAlumno(getBaseContext(),alumno);
        actionBar.setTitle(alumno.getNombre() + " " + alumno.getApellido());
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);

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



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Categoria[] categorias = Categoria.values();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 4){
                return UserFragment.newInstance();
            } else {
                return PlaceholderFragment.newInstance(categorias[position]);
            }
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
