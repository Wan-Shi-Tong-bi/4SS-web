package com.example.ii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2Clicked(v);
            }
        });
    }

    public void btn1Clicked(final View src){
        Toast t = Toast.makeText(this, "Button 1 Event gezündet", Toast.LENGTH_LONG);
        t.show();
    }

    public void btn2Clicked(final View src){
        Toast t = Toast.makeText(this, "Button 2 Event gezündet", Toast.LENGTH_LONG);
        t.show();
    }
}