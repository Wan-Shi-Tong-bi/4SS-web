package com.example.vii_iii_uebung.Tasks;

import android.app.ActionBar;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.LinkedList;
import java.util.List;

public class GetPatientTask extends AsyncTask<Integer, Patient, String> {
    Context context;
    View rootView;
    Patient p;
    List<Eintrag> items;
    ArrayAdapter<Eintrag> aa;

    public GetPatientTask(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ListView lv = rootView.findViewById(R.id.listViewEintrag);
        p = new Patient();
        items = new ArrayList<>();
        aa = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
        lv.setAdapter(aa);

    }

    @Override
    protected String doInBackground(Integer... ids) {
        Log.i("tuber", "ja");
        int id = ids[0];
        String ip = "172.17.210.161";
        while(true) {
            try {

                URL connect = new URL("http://" + ip + ":8080/E_REST_Server/resources/patienten/patient/" + id);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.openStream()));

                String sJson = reader.readLine();
                JSONObject jsonObj = new JSONObject(sJson);
                p = new Patient(id, jsonObj.getString("vorname"), jsonObj.getString("nachname"));

                JSONArray jsonArr = jsonObj.getJSONArray("Daten");
                items.clear();
                for (int e = 0; e < jsonArr.length(); e++) {
                    JSONObject jsonEintrag = (JSONObject) jsonArr.get(e);
                    Eintrag tempEintrag = new Eintrag(jsonEintrag.getString("Datum"), jsonEintrag.getString("Eintrag"));
                    items.add(tempEintrag);
                }

                publishProgress(p);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(isCancelled()){
                return null;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onProgressUpdate(Patient... values) {
        Log.i("tuber", values[0].getVorname());
        TextView id = rootView.findViewById(R.id.textID2);
        TextView vor = rootView.findViewById(R.id.textVor2);
        TextView nach = rootView.findViewById(R.id.textNach2);
        id.setText("" + p.getiD());
        vor.setText("" + p.getVorname());
        nach.setText("" + p.getNachname());
        aa.notifyDataSetChanged();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
