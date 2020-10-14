package com.example.quizapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.quizapp.R;
import com.example.quizapp.data.State;

public class StatePagedListAdapter extends PagedListAdapter<State, StateViewHolder> {

    public ClickListener clickListener;

    protected StatePagedListAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.state_list_row,parent,false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, final int position) {
        State currentState=getItem(position);
        if(currentState!=null)
        {
            holder.bind(currentState);
            if(clickListener!=null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onItemClick(view,position);
                    }
                });
            }
        }
    }

    public static DiffUtil.ItemCallback<State> itemCallback=new DiffUtil.ItemCallback<State>() {
        @Override
        public boolean areItemsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.getState()==newItem.getState();
        }

        @Override
        public boolean areContentsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.equals(newItem);
        }
    };

    public State getStateAtPosition(int position)
    {
        return getItem(position);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }

    public interface ClickListener
    {
        void onItemClick(View view,int position);
    }
}
