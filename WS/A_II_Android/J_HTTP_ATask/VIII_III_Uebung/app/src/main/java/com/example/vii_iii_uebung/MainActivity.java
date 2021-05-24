package com.example.vii_iii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.vii_iii_uebung.Tasks.MainServerTask;
import com.example.vii_iii_uebung.model.Eintrag;
import com.example.vii_iii_uebung.model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<Patient> aa;
    List<Patient> patients;

    ServerTask task;
    MainServerTask task2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
                openNeuerPatientActivity();

            }
        });

        //Variante Task Klasse in Klasse
        patients = new ArrayList<>();
        lv = findViewById(R.id.listView);
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);
        lv.setAdapter(aa);

        task = new ServerTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //

        //Variante Task in extra Klasse
        //

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                task.cancel(true);
                openPatientActivity(position);
            }
        });
    }

    private void openPatientActivity(int pos){
        Intent intent = new Intent(this, PatientActivity.class);
        intent.putExtra("id", patients.get(pos).getiD());
        startActivity(intent);

    }

    private void openNeuerPatientActivity(){
        Intent intent = new Intent(this, NeuerPatientActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(true);
    }

    private class ServerTask extends AsyncTask<String, List<Patient>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
                while(true) {
                    try {
                        String ip = "172.17.210.161";
                        URL connect = new URL("http://" + ip + ":8080/E_REST_Server/resources/patienten/patientenIDs");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connect.openStream()));


                        //IDs ermitteln
                        String sJson = reader.readLine();

                        JSONArray jsonArr = (new JSONObject(sJson)).getJSONArray("Liste der IDs");
                        ArrayList<Integer> ids = new ArrayList<>();
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            ids.add(jsonObj.getInt("id"));
                        }

                            //Patienten - List
                            List<Patient> patients = new ArrayList<>();
                            for (int id : ids) {
                                connect = new URL("http://" + ip + ":8080/E_REST_Server/resources/patienten/patient/" + id);
                                reader = new BufferedReader(new InputStreamReader(connect.openStream()));

                                sJson = reader.readLine();
                                JSONObject jsonObj = new JSONObject(sJson);
                                Patient p = new Patient(id, jsonObj.getString("vorname"), jsonObj.getString("nachname"));

                                jsonArr = jsonObj.getJSONArray("Daten");
                                for (int e = 0; e < jsonArr.length(); e++) {
                                    JSONObject jsonEintrag = (JSONObject) jsonArr.get(e);
                                    Eintrag tempEintrag = new Eintrag(jsonEintrag.getString("Datum"), jsonEintrag.getString("Eintrag"));
                                    p.addEintrag(tempEintrag);
                                }

                                patients.add(p);
                            }
                        reader.close();
                        publishProgress(patients);


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(isCancelled()){
                        return null;
                    }
                }
        }

        @Override
        protected void onProgressUpdate(List<Patient>... values) {
            patients.clear();
            for(Patient p : values[0]){
                patients.add(p);
            }
            aa.notifyDataSetChanged();
            super.onProgressUpdate(values);
        }


        public void cancel() {
        }
    }
}

