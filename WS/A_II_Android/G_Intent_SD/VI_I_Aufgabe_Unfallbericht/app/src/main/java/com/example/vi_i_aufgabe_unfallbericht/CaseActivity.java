package com.example.vi_i_aufgabe_unfallbericht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CaseActivity extends AppCompatActivity {
    ArrayList<Witness> witnesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.setTitle(bundle.getString("art"));

        Case c = (Case) bundle.getSerializable("case");
        if(c != null){
            wertebefuellen(c);
        }else{
            witnesses = new ArrayList<>();
        }

        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<Witness> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, witnesses);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startDeleteWitnessActivity(position);
            }
        });

        Button addWit = findViewById(R.id.addWitness);
        addWit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWitnessActivity();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Case c = getCase();

        if(!c.getDate().isEmpty()){

        try {
            FileInputStream fis = openFileInput("nummer.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            int i  = Integer.parseInt(in.readLine());
            i++;
            in.close();


            FileOutputStream fos = openFileOutput("nummer.txt", Context.MODE_PRIVATE);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(fos));
            out.println("" + i);
            out.flush();
            out.close();
            if(this.getTitle().toString().equals("neuer Eintrag")){
                fos = openFileOutput(""+i, Context.MODE_PRIVATE);
                out = new PrintWriter(new OutputStreamWriter(fos));
                out.println(c.toString());
                out.flush();
                out.close();
            }else{
                fos = openFileOutput(""+getTitle().toString(), Context.MODE_PRIVATE);
                out = new PrintWriter(new OutputStreamWriter(fos));
                out.println(c.toString());
                out.flush();
                out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    private void startWitnessActivity(){
        Intent intent = new Intent(this, WittnessActivity.class);
        intent.putExtra("art", this.getTitle());
        intent.putExtra("case", this.getCase());
        startActivity(intent);
    }

    private void startDeleteWitnessActivity(int pos){
        Intent intent = new Intent(this, WittnessActivity.class);
        intent.putExtra("art", this.getTitle());
        intent.putExtra("case", this.getCase());
        intent.putExtra("pos", pos);
        startActivity(intent);
    }

    private Case getCase(){
        EditText date = findViewById(R.id.editDay);

        EditText time = findViewById(R.id.editTime);
        EditText ort = findViewById(R.id.editOrt);
        EditText plz = findViewById(R.id.editPLZ);

        EditText nr = findViewById(R.id.editNr);

        String num = nr.getText().toString();
        int i;
        if(num.isEmpty()){
            i = 0;
        }else{
            i = Integer.parseInt(num);
        }


        CheckBox verletzte = findViewById(R.id.check1);
        CheckBox schaeden = findViewById(R.id.check2);

        Case c = new Case(date.getText().toString(), time.getText().toString(), ort.getText().toString(), plz.getText().toString(), i , verletzte.isChecked(), schaeden.isChecked(), witnesses);

        return c;
    }

    private void wertebefuellen(Case c){
        witnesses = c.getWitnesses();

        EditText date = findViewById(R.id.editDay);

        EditText time = findViewById(R.id.editTime);
        EditText ort = findViewById(R.id.editOrt);
        EditText plz = findViewById(R.id.editPLZ);

        EditText nr = findViewById(R.id.editNr);

        date.setText(c.getDate());
        time.setText(c.getTime());
        ort.setText(c.getOrt());
        plz.setText(c.getPlz());
        nr.setText("" + c.getNr());

    }
}