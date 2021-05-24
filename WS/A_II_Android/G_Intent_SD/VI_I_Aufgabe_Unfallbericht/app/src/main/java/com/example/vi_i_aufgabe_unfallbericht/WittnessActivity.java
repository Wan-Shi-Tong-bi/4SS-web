package com.example.vi_i_aufgabe_unfallbericht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WittnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wittness);

        Bundle bundle = getIntent().getExtras();
        if(bundle.get("pos") != null){
            Witness w = ((Case) bundle.get("case")).getWitnesses().get((Integer) bundle.get("pos"));
            werteBefuellen(w);
        }

        Button ok = findViewById(R.id.btnOK);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok();
            }
        });

        Button del = findViewById(R.id.btnLOESCHEN);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        final Button cancel = findViewById(R.id.btnCANCEL);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }

    private void ok(){
        EditText vor = findViewById(R.id.vor);
        EditText nach = findViewById(R.id.nach);
        EditText tel = findViewById(R.id.tel);

        Bundle b = getIntent().getExtras();
        Case c = (Case) b.getSerializable("case");
        Witness w = new Witness(vor.getText().toString(), nach.getText().toString(), tel.getText().toString());


        if(b.get("pos") == null){
            c.addWitnesses(w);
        }else{
            int pos = (Integer) b.get("pos");
            c.getWitnesses().set(pos, w);
        }

        Intent intent = new Intent(this, CaseActivity.class);
        intent.putExtra("case", c);
        intent.putExtra("art", b.getString("art"));
        startActivity(intent);
    }

    private void cancel(){

        Bundle b = getIntent().getExtras();
        Case c = (Case) b.getSerializable("case");

        Intent intent = new Intent(this, CaseActivity.class);
        intent.putExtra("case", c);
        intent.putExtra("art", b.getString("art"));
        startActivity(intent);
    }

    private void delete(){
        Bundle bundle = getIntent().getExtras();

        if(bundle.get("pos") == null){
            cancel();
        }else{
            int pos = (Integer) bundle.get("pos");
            Case c = (Case) bundle.getSerializable("case");
            c.getWitnesses().remove(pos);

            Intent intent = new Intent(this, CaseActivity.class);
            intent.putExtra("case", c);
            intent.putExtra("art", bundle.getString("art"));
            startActivity(intent);
        }

    }

    private void werteBefuellen(Witness w){
        EditText vor = findViewById(R.id.vor);
        EditText nach = findViewById(R.id.nach);
        EditText tel = findViewById(R.id.tel);

        vor.setText(w.getVorname());
        nach.setText(w.getNachname());
        tel.setText(w.getTel());
    }
}