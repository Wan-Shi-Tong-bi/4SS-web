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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NeuerPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuer_patient);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSpeichern(v);
            }
        });
    }

    public void onClickSpeichern(View v){

        Thread th = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    EditText vor = findViewById(R.id.editVor);
                    EditText nach = findViewById(R.id.editNach);
                    String ip = "172.17.210.161";
                    URL url = new URL("http://"+ ip +":8080/E_REST_Server/resources/patienten/newpatient/"+ vor.getText()+"/"+ nach.getText()+"");
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(url.openStream()));
                    readResponseStream(reader);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        };
        th.start();
        startActivity(new Intent(this, MainActivity.class));
        System.exit(0);
    }

    private String readResponseStream(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
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