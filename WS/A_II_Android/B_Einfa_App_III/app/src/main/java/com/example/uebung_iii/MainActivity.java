package com.example.uebung_iii;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    LinkedList<Patient> l = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l.add(new Patient("Franz", "Huber"));
        l.add(new Patient("Georg", "Narcos"));

        final Button zeigenButton = (Button) findViewById(R.id.button);
        zeigenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeigenBtnClicked(v);
            }
        });
    }

    private void zeigenBtnClicked(final View v){
        EditText eingabe = (EditText) findViewById(R.id.editTExt);


        try{
            int id = Integer.parseInt(eingabe.getText().toString());
            Patient p = (Patient) l.get(id);
            TextView vorname = findViewById(R.id.vorname);
            vorname.setText(p.getVorname());
            TextView nachname = findViewById(R.id.nachname);
            nachname.setText(p.getNachname());
        }catch (IndexOutOfBoundsException e){
            Toast toast = Toast.makeText(this, "Patient ist nicht vorhanden", Toast.LENGTH_LONG);
            toast.show();
        }catch( NumberFormatException e){
            Toast toast = Toast.makeText(this, "Bitte eine Zahl eingeben", Toast.LENGTH_LONG);
            toast.show();
        }

    }


}