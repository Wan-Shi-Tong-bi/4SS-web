package com.example.vi_i_aufgabe_unfallbericht;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Übersicht");
        items = Arrays.asList(fileList());
        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oldCaseActivity(position);
            }
        });

        Button newButton = findViewById(R.id.button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCaseActivity();
            }
        });
    }

    public void newCaseActivity(){
        Intent intent = new Intent(this, CaseActivity.class);
        intent.putExtra("art", "neuer Eintrag");
        startActivity(intent);
    }

    public void oldCaseActivity(int pos){
        if(pos != 0){
            String filename = items.get(pos);
            Case c = new Case();

            try {
                FileInputStream fis = openFileInput(filename);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                c = Case.desarialize(in.readLine());
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, CaseActivity.class);
            intent.putExtra("case", c);
            intent.putExtra("art", "" + filename);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Dieser Eintrag steht nicht zur Auswahl!", Toast.LENGTH_SHORT).show();

        }


    }

}