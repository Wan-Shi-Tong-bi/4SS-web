package com.example.ix_ii_uebung;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BlueFragment extends Fragment {
    private Button buttonBlue;
    private TextView textBlue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blue, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view){
        buttonBlue = view.findViewById(R.id.buttonBlue);
        textBlue = view.findViewById(R.id.textBlue);
        textBlue.setText("noch kein Inhalt");
    }

    public void show(String item){
        textBlue.setText(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}