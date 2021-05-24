package com.example.xi_ii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xi_ii_uebung.database.PersonDbHelper;
import com.example.xi_ii_uebung.database.UebungTbl;

public class AddPersonActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        PersonDbHelper dbHelper = new PersonDbHelper(this);
        db = dbHelper.getReadableDatabase();

        Button btn = findViewById(R.id.buttonADD);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

    }

    private void changeActivity() {
        int id = getIntent().getExtras().getInt("newID") + 1;

        EditText alter = findViewById(R.id.editTextAlter2);
        EditText nach = findViewById(R.id.editTextNachname2);
        EditText vor = findViewById(R.id.editTextVorname2);
        EditText city = findViewById(R.id.editTextCity2);
        EditText zip = findViewById(R.id.editTextZipCode2);
        EditText street = findViewById(R.id.editTextStreet2);

        db.execSQL(UebungTbl.PSTMT_INSERT, new Object[]{id, vor.getText().toString(), nach.getText().toString(),  Integer.parseInt(alter.getText().toString())});
        db.execSQL(UebungTbl.CSTMT_INSERT, new Object[]{id, city.getText().toString(), street.getText().toString(), zip.getText().toString()});


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}