package com.example.iv_i_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int zustand = 0;
    private ArrayList<String> scores;
    private CandidaScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Switch s1 = findViewById(R.id.switch1);
        Switch s2 = findViewById(R.id.switch2);
        Switch s3 = findViewById(R.id.switch3);
        Switch s4 = findViewById(R.id.switch4);

        makeListeners(s1, s2, s3, s4);

        zustandSetzen();

        scores = new ArrayList<>();
        scores.add("test");
        ListView lv = findViewById(R.id.listView);
        adapter = new CandidaScoreAdapter(this, R.layout.candida_list, scores);
        lv.setAdapter(adapter);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scores.add(""+ zustand);
                adapter.notifyDataSetChanged();
            }
        });


    }

    private void zustandSetzen(){
        EditText zahl = findViewById(R.id.zahlField);
        EditText yesNo = findViewById(R.id.yesNoField);

        if(zustand > 2){
            zahl.setBackgroundColor(Color.RED);
            yesNo.setBackgroundColor(Color.RED);

            zahl.setText("" + zustand);
            yesNo.setText("NEIN");

        }else{
            zahl.setBackgroundColor(Color.GREEN);
            yesNo.setBackgroundColor(Color.GREEN);

            zahl.setText("" +zustand);
            yesNo.setText("JA");
        }
    }

    private void zustandAktualisieren(){
        Switch s1 = findViewById(R.id.switch1);
        Switch s2 = findViewById(R.id.switch2);
        Switch s3 = findViewById(R.id.switch3);
        Switch s4 = findViewById(R.id.switch4);

        zustand = 0;
        if(s1.isChecked()){
            zustand++;
        }
        if(s2.isChecked()){
            zustand++;
        }
        if(s3.isChecked()){
            zustand++;
        }
        if(s4.isChecked()){
            zustand = zustand + 2;
        }

        zustandSetzen();
    }

    private void makeListeners(Switch s1, Switch s2, Switch s3, Switch s4){
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                zustandAktualisieren();
            }
        });

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                zustandAktualisieren();
            }
        });

        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                zustandAktualisieren();
            }
        });

        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                zustandAktualisieren();
            }
        });
    }
}