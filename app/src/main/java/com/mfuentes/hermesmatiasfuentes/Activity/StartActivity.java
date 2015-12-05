package com.mfuentes.hermesmatiasfuentes.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mfuentes.hermesmatiasfuentes.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        String[] fakeData = new String[] {"Pedro","Pablo","Juan","Pedro","Pablo","Juan","Pedro","Pablo","Juan"};
        final ListView listview = (ListView) findViewById(R.id.alumnos_listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,fakeData);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String alumno = adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ALUMNO_ID", 1);
                startActivity(intent);
            }
        });
    }
}
