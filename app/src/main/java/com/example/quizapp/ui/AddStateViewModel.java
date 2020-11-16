package com.example.quizapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.quizapp.data.States;
import com.example.quizapp.database.StateRepository;

public class AddStateViewModel extends AndroidViewModel {

    private StateRepository stateRepository;
    public LiveData<PagedList<States>> data;


    public AddStateViewModel(@NonNull Application application) {
        super(application);
        stateRepository=StateRepository.getStateRepository(application);
    }

    public void insert(States state)
    {
        stateRepository.insert(state);
    }

    public void update(States state)
    {
        stateRepository.update(state);
    }
}
