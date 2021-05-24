package com.example.vi_ii_aufgabe_medikamentenverwaltung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Produkt> produkte;
    ArrayAdapter<Produkt> adapter;
    ListView lv;
    boolean produktGeladen;
    int produktSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        produktGeladen = false;
        produktSelected = -1;

        Button save = findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonPressed();
            }
        });

        produkte = new ArrayList<>();
        lv = findViewById(R.id.listView);
        registerForContextMenu(lv);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produkte);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemPressed(position);
            }
        });

    }

    private void onSaveButtonPressed(){
        Produkt p = getProdukt();
        if(produktGeladen){
            produkte.set(produktSelected, p);
            produktGeladen = false;
            clearProdukt();
        }else{
            produkte.add(p);
            clearProdukt();
        }

        adapter.notifyDataSetChanged();
    }

    private void onListItemPressed(int position){
        produktGeladen = true;
        produktSelected = position;

        EditText her = findViewById(R.id.editHersteller);
        EditText produkt = findViewById(R.id.editProdukt);
        EditText menge = findViewById(R.id.editMenge);
        EditText datum = findViewById(R.id.editDatum);

        Produkt p = produkte.get(position);
        her.setText(p.hersteller);
        produkt.setText(p.produkt);
        menge.setText("" + p.menge);
        datum.setText(p.datum);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        int id = v.getId();
        if(id == R.id.listView){
            getMenuInflater().inflate(R.menu.deletecontextmenu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuVersionInfo:
                Toast.makeText(this, "Version 0.1", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.context_delete){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int pos = info.position;
            produkte.remove(pos);
            adapter.notifyDataSetChanged();
            produktGeladen = false;
            clearProdukt();
            Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show();

        }

        return super.onContextItemSelected(item);
    }

    private Produkt getProdukt(){
        EditText her = findViewById(R.id.editHersteller);
        EditText produkt = findViewById(R.id.editProdukt);
        EditText menge = findViewById(R.id.editMenge);
        EditText datum = findViewById(R.id.editDatum);

        return new Produkt(her.getText().toString(), produkt.getText().toString(), Integer.parseInt(menge.getText().toString()), datum.getText().toString());
    }

    private void clearProdukt(){
        EditText her = findViewById(R.id.editHersteller);
        EditText produkt = findViewById(R.id.editProdukt);
        EditText menge = findViewById(R.id.editMenge);
        EditText datum = findViewById(R.id.editDatum);

        her.setText("");
        produkt.setText("");
        menge.setText("");
        datum.setText("");
    }
}