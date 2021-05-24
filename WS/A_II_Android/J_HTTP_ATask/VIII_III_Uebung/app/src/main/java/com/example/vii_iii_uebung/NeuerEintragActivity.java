package com.example.vii_iii_uebung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vii_iii_uebung.Tasks.AddEintragTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NeuerEintragActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuer_eintrag);

        Bundle bundle = getIntent().getExtras();
        id = (int) bundle.get("id");

        Button buttonsave = findViewById(R.id.buttonSave3);
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

    }

    public void onSaveClicked() {
        AddEintragTask task = new AddEintragTask(findViewById(R.id.rootViewNewEntry));
        task.execute(id);

        Intent intent = new Intent(this, PatientActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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