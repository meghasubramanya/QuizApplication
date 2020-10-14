package com.example.quizapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.quizapp.data.State;
import com.example.quizapp.database.StateRepository;

public class AddStateViewModel extends AndroidViewModel {

    private StateRepository stateRepository;
    public LiveData<PagedList<State>> data;

    public AddStateViewModel(@NonNull Application application) {
        super(application);
        stateRepository=StateRepository.getStateRepository(application);
        data=stateRepository.getAllStates();
    }

    public void insert(State state)
    {
        stateRepository.insert(state);
    }

    public void update(State state)
    {
        stateRepository.update(state);
    }
}
