package com.example.x_vi_uebung;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private final String IPv4 = "192.168.48.145";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionDialog();
            }
        });
    }

    private void openSelectionDialog() {

        final View vDialog = getLayoutInflater().inflate(R.layout.my_dialog, null);
        new AlertDialog.Builder(this)
                .setView(vDialog)
                .setPositiveButton("ok", (dialog, which) -> {
                    onDialogOkSelected(vDialog);
                })
                .show();

        RadioButton buttonJSON = vDialog.findViewById(R.id.radioJSON);
        RadioButton buttonICD = vDialog.findViewById(R.id.radioICD);
        buttonJSON.setChecked(true);
        buttonICD.setChecked(true);

    }


    private void onDialogOkSelected(View vDialog) {
        String URL = createURL(vDialog);

        ServerTask task = new ServerTask();
        task.execute("" + URL);

    }

    private String createURL(View vDialog) {
        String URL = "http://" + IPv4 + ":41793" + "/RestServerV1/resources/";
        String ergebnis = "";

        Log.i("testung", "1" + URL);

        RadioGroup radioGroup1 = vDialog.findViewById(R.id.radioGroup1);
        int idGroup1 = radioGroup1.getCheckedRadioButtonId();

        RadioGroup radioGroup2 = vDialog.findViewById(R.id.radioGroup2);
        int idGroup2 = radioGroup2.getCheckedRadioButtonId();

        EditText editTextCode = vDialog.findViewById(R.id.editTextCode);
        String code = editTextCode.getText().toString();

        switch (idGroup1) {
            case R.id.radioICD:
                URL += "ICD";
                break;
            case R.id.radioICF:
                URL += "ICF";
                break;
            case R.id.radioTNM:
                URL += "TNM";
                break;
        }

        Log.i("testung", "2" + URL);

        if (!code.isEmpty()) {
            URL += "/" + code;
        }

        Log.i("testung", "3" + URL);

        switch (idGroup2) {
            case R.id.radioJSON:
                URL += "/J";
                break;
            case R.id.radioXML:
                URL += "/X";
                break;
        }

        return URL;
    }



    public class ServerTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String ergebnis = "";
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    ergebnis = readResponseStream(reader);

                    Log.i("testung", ergebnis);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return ergebnis;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView textView = findViewById(R.id.textView);
            textView.setText("" + s);
            super.onPostExecute(s);
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
}