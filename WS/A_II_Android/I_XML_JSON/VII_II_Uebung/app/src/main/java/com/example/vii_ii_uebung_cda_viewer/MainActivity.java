package com.example.vii_ii_uebung_cda_viewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assets = getAssets();
        items = new ArrayList<>();
        try {
            String[] temp =  assets.list("xml_Dateien");
            items = Arrays.asList(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,items);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(position);
            }
        });
    }

    private void itemClicked(int pos){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("fileName", items.get(pos));
        startActivity(intent);
    }
}