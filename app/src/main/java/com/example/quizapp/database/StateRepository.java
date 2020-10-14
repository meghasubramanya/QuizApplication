package com.example.quizapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.quizapp.data.State;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StateRepository {

    private StateDao stateDao;
    private static StateRepository REPOSITORY=null;
    ExecutorService executorService= Executors.newSingleThreadExecutor();
    public static int PAGE_SIZE=15;

    private StateRepository(Application application)
    {
        StateDatabase db=StateDatabase.getInstance(application);
        stateDao=db.stateDao();
    }

    public static StateRepository getStateRepository(Application application){
        if(REPOSITORY==null){
            synchronized (StateRepository.class){
                if (REPOSITORY==null){
                    REPOSITORY = new StateRepository(application);
                }
            }
        }
        return REPOSITORY;
    }

    public void insert(final State state)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                  stateDao.insert(state);
            }
        });
    }

    public void delete(final State state)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                stateDao.delete(state);
            }
        });
    }

    public void update(final State state)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                stateDao.update(state);
            }
        });
    }

    public LiveData<PagedList<State>> getAllStates()
    {
        return new LivePagedListBuilder<>(stateDao.getAllStates(),PAGE_SIZE).build();
    }

   
}
