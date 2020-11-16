package com.example.quizapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.example.quizapp.data.States;
import com.example.quizapp.database.StateRepository;

public class StateViewModel extends AndroidViewModel {

    public StateRepository stateRepository;
    public LiveData<PagedList<States>> data;

    private MutableLiveData<String> sortOrderChanged=new MutableLiveData<>();

    public StateViewModel(@NonNull Application application) {
        super(application);
        stateRepository=StateRepository.getStateRepository(application);
        sortOrderChanged.setValue("id");
        data= Transformations.switchMap(sortOrderChanged, new Function<String, LiveData<PagedList<States>>>() {
            @Override
            public LiveData<PagedList<States>> apply(String input) {
                return stateRepository.getAllStates(input);
            }
        });
    }

    public void changeSortingOrder(String sortBy)
    {
        sortOrderChanged.postValue(sortBy);
    }

    public void insert(States state)
    {
        stateRepository.insert(state);
    }

    public void delete(States state)
    {
        stateRepository.delete(state);
    }

    public void update(States state)
    {
        stateRepository.update(state);
    }
}
