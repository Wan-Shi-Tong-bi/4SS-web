package com.example.ix_ii_uebung;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
    implements GreenFragment.OnButtonGreenClickedListener{

    private BlueFragment blueFragment;
    private boolean showBlueFragement = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
    }

    private void initializeView(){
        blueFragment = (BlueFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_blue);
        showBlueFragement = blueFragment != null && blueFragment.isInLayout();
        if(showBlueFragement){
            Button blue = findViewById(R.id.buttonBlue);
            blue.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onButtonGreenClicked(String item) {
        if(showBlueFragement) blueFragment.show(item);
        else callBlueActivity(item);
    }

    private void callBlueActivity(String item) {
        Intent intent = new Intent(this, BlueActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}