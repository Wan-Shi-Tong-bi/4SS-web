package com.example.iv_i_uebung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CandidaScoreAdapter extends BaseAdapter {
    private List<String> scores = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;

    public CandidaScoreAdapter(Context ctx, int layoutId, List<String> scores) {
        this.scores = scores;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        String score = scores.get(position);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        ((TextView) listItem.findViewById(R.id.candidaScore)).setText(score);
        return listItem;
    }
}
