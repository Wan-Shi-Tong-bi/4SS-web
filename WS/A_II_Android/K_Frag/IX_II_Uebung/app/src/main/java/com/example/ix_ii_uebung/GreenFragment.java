package com.example.ix_ii_uebung;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class GreenFragment extends Fragment {
    OnButtonGreenClickedListener listener;
    private EditText greentext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_green, container, false);
        initalizeViews(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  OnButtonGreenClickedListener){
            listener = (OnButtonGreenClickedListener) context;
        }
    }

    private void textOnClicked(){
        listener.onButtonGreenClicked(greentext.getText().toString());
    }

    private void initalizeViews(View view){
        greentext = view.findViewById(R.id.editGreen);
        Button buttonGreen = view.findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textOnClicked();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public interface OnButtonGreenClickedListener{
        void onButtonGreenClicked(String item);
    }
}