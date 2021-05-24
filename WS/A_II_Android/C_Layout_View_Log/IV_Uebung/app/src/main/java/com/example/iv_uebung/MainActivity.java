package com.example.iv_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> personen;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            personen = savedInstanceState.getParcelableArrayList("key");
        }else{
            personen = new ArrayList<>();
        }
        setContentView(R.layout.activity_main);


        index = 0;


        RadioButton arztButton = (RadioButton) findViewById(R.id.arztBtn);
        arztButton.setChecked(true);


        Button saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClicked(v);
            }
        });

        Button vorButton = (Button) findViewById(R.id.vorBtn);
        vorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personen.size() - 1 == index){
                    index = 0;
                }else{
                    index++;
                }
                if(personen.size()>0){
                    changePersoneView();
                }
            }
        });

        Button backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(0 == index){
                    index = personen.size()-1;
                }else{
                    index--;
                }
                if(personen.size()>0){
                    changePersoneView();
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("key", personen);
    }

    private void saveButtonClicked(final View v){
        RadioButton arztButton = (RadioButton) findViewById(R.id.arztBtn);
        boolean arztSelected = arztButton.isChecked();

        EditText vorname = findViewById(R.id.vorField);
        EditText nachname = findViewById(R.id.nachField);

        Person p = new Person(vorname.getText().toString(), nachname.getText().toString());

        if(arztSelected){
            p.setTyp(Typ.ARZT);
        }else{
            p.setTyp(Typ.PATIENT);
        }

        personen.add(p);

        vorname.setText("");
        nachname.setText("");
        arztButton.setChecked(true);
    }

    private void changePersoneView(){
        TextView vor = findViewById(R.id.vorView);
        TextView nach = findViewById(R.id.nachView);
        TextView typ = findViewById(R.id.typView);

        Person p = personen.get(index);
        vor.setText(p.getVorname());
        nach.setText(p.getNachname());
        typ.setText(p.getTyp().toString());
    }
}