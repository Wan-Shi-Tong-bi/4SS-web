package com.example.vii_iii_uebung.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.vii_iii_uebung.R;
import com.example.vii_iii_uebung.model.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AddEintragTask extends AsyncTask<Integer, Integer, String> {
    View rootView;
    String entrytext;

    public AddEintragTask(View rootView) {
        this.rootView = rootView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        EditText eintrag = rootView.findViewById(R.id.editEintrag);
        entrytext = eintrag.getText().toString();
    }

    @Override
    protected String doInBackground(Integer... values) {
        int id = values[0];
        try {

            String ip = "172.17.210.161";
            URL url = new URL("http://" + ip + ":8080/E_REST_Server/resources/patienten/newentry/" + id + "/" + entrytext + "");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            readResponseStream(reader);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String readResponseStream(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }


}
