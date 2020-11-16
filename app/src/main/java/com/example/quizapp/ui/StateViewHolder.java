package com.example.quizapp.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.data.States;

public class StateViewHolder extends RecyclerView.ViewHolder {

    TextView tvStateName,tvCapitalName;

    public StateViewHolder(@NonNull View itemView) {
        super(itemView);
        tvStateName=itemView.findViewById(R.id.tvStateName);
        tvCapitalName=itemView.findViewById(R.id.tvCapitalName);
    }

    public void bind(States state)
    {
        tvStateName.setText(state.getState());
        tvCapitalName.setText(state.getCapital());
    }
}
