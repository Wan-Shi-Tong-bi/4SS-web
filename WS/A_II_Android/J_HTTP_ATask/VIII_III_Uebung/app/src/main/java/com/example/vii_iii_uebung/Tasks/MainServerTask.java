package com.example.vii_iii_uebung.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vii_iii_uebung.R;
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

public class MainServerTask extends AsyncTask<String, List<Patient>, String> {
    Context context;
    View rootView;
    ArrayAdapter aa;
    List<Patient> patients;

    public MainServerTask(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    protected void onPreExecute() {
        patients = new ArrayList<>();
        ListView lv = rootView.findViewById(R.id.listView);
        aa = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, patients);
        lv.setAdapter(aa);

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i("tuber", "ja");
        while(true) {
            try {
                String ip = "172.17.204.81";
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

                Thread.sleep(1000);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
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


}
