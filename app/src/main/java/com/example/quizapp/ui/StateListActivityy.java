package com.example.quizapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.R;
import com.example.quizapp.data.States;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class StateListActivityy extends AppCompatActivity {

    private static final String EXTRA_ID = "Extra_id";
    private static final String EXTRA_STATE = "Extra_state";
    private static final String EXTRA_CAPITAL = "Extra_capital";

    public static final int NEW_REQUEST_CODE = 1;
    public static final int UPDATE_REQUEST_CODE = 2;

    private StateViewModel viewModel;
    private StatePagedListAdapter pagedListAdapter;
    private States state;

    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    FloatingActionButton btnFloating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list_activityy);

        viewModel = new ViewModelProvider(this).get(StateViewModel.class);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String sort=sharedPreferences.getString("list_preference_1","id");
        viewModel.changeSortingOrder(sort);


        recyclerView = findViewById(R.id.recyclerView);
        btnFloating = findViewById(R.id.btnFloating);


        pagedListAdapter = new StatePagedListAdapter();
        recyclerView.setAdapter(pagedListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.data.observe(this, new Observer<PagedList<States>>() {
            @Override
            public void onChanged(PagedList<States> states) {
                pagedListAdapter.submitList(states);
            }
        });

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StateListActivityy.this, AddStateActivityy.class);
                startActivityForResult(intent, NEW_REQUEST_CODE);
            }
        });

        pagedListAdapter.setOnItemClickListener(new StatePagedListAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                States state = pagedListAdapter.getStateAtPosition(position);
                Intent intent = new Intent(StateListActivityy.this, AddStateActivityy.class);
                intent.putExtra(EXTRA_ID, state.getId());
                intent.putExtra(EXTRA_STATE, state.getState());
                intent.putExtra(EXTRA_CAPITAL, state.getCapital());
                startActivityForResult(intent, UPDATE_REQUEST_CODE);
            }
        });


        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        final Snackbar snackbar = Snackbar.make(constraintLayout, "State Deleted",Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.insert(state);
                    }
                });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                state = pagedListAdapter.getStateAtPosition(position);
                viewModel.delete(state);
                snackbar.show();
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener=new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("list_preference_1"))
            {
                String s=sharedPreferences.getString(key,"id");
                viewModel.changeSortingOrder(s);
            }
        }
    };

}


