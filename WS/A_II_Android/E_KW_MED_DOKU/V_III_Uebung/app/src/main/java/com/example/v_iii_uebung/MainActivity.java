package com.example.v_iii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<DRG> drglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drglist = new ArrayList<>();
        try {
            fillList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListView lv = findViewById(R.id.listview);
        ArrayAdapter<DRG> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drglist);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateFields(position);
            }
        });

    }

    private void fillList() throws IOException {
        AssetManager assets = getAssets();
        BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("V_IV_Daten.csv")));
        br.readLine();
        String line = br.readLine();
        while (line != null){
            drglist.add(deserialize(line));
            line = br.readLine();
        }

    }

    private DRG deserialize(String banana){
        String[] splitList = banana.split(";");
        DRG drg = new DRG(splitList[0],
                splitList[1],
                Double.parseDouble(splitList[2]),
                Integer.parseInt(splitList[3]),
                Integer.parseInt(splitList[4]));
        return drg;
    }

    private void updateFields(int pos){
        DRG drg = drglist.get(pos);
        TextView i1 = findViewById(R.id.input1);
        i1.setText(drg.getName());
        TextView i2 = findViewById(R.id.input2);
        i2.setText("" + drg.getVwd());
        TextView i3 = findViewById(R.id.input3);
        i3.setText("" + drg.getAbschlag());
        TextView i4 = findViewById(R.id.input4);
        i4.setText("" + drg.getZusatz());
    }
}