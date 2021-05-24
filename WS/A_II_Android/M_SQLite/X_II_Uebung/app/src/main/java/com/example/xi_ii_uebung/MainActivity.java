package com.example.xi_ii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.xi_ii_uebung.database.PersonDbHelper;
import com.example.xi_ii_uebung.database.UebungTbl;
import com.example.xi_ii_uebung.model.Contact;
import com.example.xi_ii_uebung.model.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Person> personen;
    SQLiteDatabase db;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PersonDbHelper dbHelper = new PersonDbHelper(this);
        db = dbHelper.getReadableDatabase();

        personen = new ArrayList<Person>();
        ListView lv = findViewById(R.id.listView);
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, personen);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelceted(position);
            }
        });

        datenbankQuery();

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        Button btnAddPerson = findViewById(R.id.buttonAddPatient);
        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }

    private void changeActivity() {
        Intent intent = new Intent(this, AddPersonActivity.class);
        intent.putExtra("newID", personen.size());
        startActivity(intent);
    }

    private void saveChanges() {
        EditText id = findViewById(R.id.editTextID);
        EditText alter = findViewById(R.id.editTextAlter);
        EditText nach = findViewById(R.id.editTextNachname);
        EditText vor = findViewById(R.id.editTextVorname);
        EditText city = findViewById(R.id.editTextCity);
        EditText zip = findViewById(R.id.editTextZipCode);
        EditText street = findViewById(R.id.editTextStreet);

        db.execSQL(UebungTbl.PSTMT_UPDATE, new String[]{vor.getText().toString(), nach.getText().toString(), alter.getText().toString(), id.getText().toString()});
        db.execSQL(UebungTbl.CSTMT_UPDATE, new String[]{city.getText().toString(), street.getText().toString(), zip.getText().toString(), id.getText().toString()});

        datenbankQuery();
        aa.notifyDataSetChanged();

        clearFields();


    }

    private void clearFields() {
        EditText id = findViewById(R.id.editTextID);
        EditText alter = findViewById(R.id.editTextAlter);
        EditText nach = findViewById(R.id.editTextNachname);
        EditText vor = findViewById(R.id.editTextVorname);
        EditText city = findViewById(R.id.editTextCity);
        EditText zip = findViewById(R.id.editTextZipCode);
        EditText street = findViewById(R.id.editTextStreet);

        id.setText("");
        alter.setText("");
        nach.setText("");
        vor.setText("");

        city.setText("");
        zip.setText("");
        street.setText("");
    }

    private void onItemSelceted(int position) {
        Person p = personen.get(position);

        Cursor rows = db.rawQuery(UebungTbl.CSTMT_SELECT_ID, new String[]{p.getId()});
        rows.moveToNext();
        Contact c = new Contact(
                rows.getString(0),
                rows.getString(2),
                rows.getString(1),
                rows.getString(3));

        EditText id = findViewById(R.id.editTextID);
        EditText alter = findViewById(R.id.editTextAlter);
        EditText nach = findViewById(R.id.editTextNachname);
        EditText vor = findViewById(R.id.editTextVorname);
        EditText city = findViewById(R.id.editTextCity);
        EditText zip = findViewById(R.id.editTextZipCode);
        EditText street = findViewById(R.id.editTextStreet);

        id.setText(p.getId());
        alter.setText(p.getAlter());
        nach.setText(p.getNachname());
        vor.setText(p.getVorname());

        city.setText(c.getCity());
        zip.setText(c.getZipcode());
        street.setText(c.getStreet());
    }

    private void datenbankQuery() {
        personen.clear();
        Cursor rows=  db.rawQuery(UebungTbl.PSTMT_SELECT, null);
        while(rows.moveToNext()){
            personen.add(new Person(
                    rows.getString(0),
                    rows.getString(1),
                    rows.getString(2),
                    rows.getString(3)
            ));
        }
        rows.close();

    }


}