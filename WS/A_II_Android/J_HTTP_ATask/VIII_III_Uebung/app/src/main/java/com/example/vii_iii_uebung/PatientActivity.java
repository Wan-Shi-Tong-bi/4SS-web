package com.example.vii_iii_uebung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vii_iii_uebung.Tasks.GetPatientTask;
import com.example.vii_iii_uebung.model.Eintrag;
import com.example.vii_iii_uebung.model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class PatientActivity extends AppCompatActivity {
    int id;
    GetPatientTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Bundle bundle = getIntent().getExtras();
        id = (int) bundle.get("id");


        task = new GetPatientTask(this, findViewById(R.id.rootViewPatient));
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id);
        fillActivity();
    }

    private void openNeuerEintragActivity(){
        Intent intent = new Intent(this, NeuerEintragActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void fillActivity(){
        Button btnadd = findViewById(R.id.buttonAddEintrag);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
                openNeuerEintragActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuEnde:

                this.finishAffinity();
                //System.exit(0);
                return true;
            case R.id.menuMain:
                startActivity(new Intent(this, MainActivity.class));
                System.exit(0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}