package com.example.ix_ii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BlueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);
        initializeView();

        int orientation = getResources().getConfiguration().orientation;
        if(orientation != Configuration.ORIENTATION_PORTRAIT){
            finish();
            return;
        }
        handleIntent();
    }

    private void initializeView() {
        Button buttonBlue = findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity();
            }
        });
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if(intent == null) return;
        BlueFragment blueFragment = (BlueFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_blue);
        String item = intent.getStringExtra("item");
        blueFragment.show(item);
    }
}