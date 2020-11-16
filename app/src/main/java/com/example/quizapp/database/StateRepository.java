package com.example.quizapp.database;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.quizapp.data.States;

import java.util.List;
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

    public void insert(final States state)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                  stateDao.insert(state);
            }
        });
    }

    public void delete(final States state)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                stateDao.delete(state);
            }
        });
    }

    public void update(final States state)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                stateDao.update(state);
            }
        });
    }

    public LiveData<PagedList<States>> getAllStates(String sortBy)
    {
        LiveData data= new LivePagedListBuilder<>(
                stateDao.getAllSortedStates(constructQuery(sortBy)),
                PAGE_SIZE)
                .build();
        return data;
    }

    private SupportSQLiteQuery constructQuery(String sortBy)
    {
        String query= "SELECT * FROM state_table ORDER BY " + sortBy + " ASC";
        return new SimpleSQLiteQuery(query);
    }

    @WorkerThread
    public States getRandomState()
    {
        return stateDao.getRandomState();
    }

   /* public Future<List<States>>  getQuizStates(final int value)
    {
        Callable<List<States>> callable = new Callable<List<States>>() {
            @Override
            public List<States> call() throws Exception {
                return stateDao.getQuizStates(value);
            }
        };
        return executorService.submit(callable);
    }*/
   public LiveData<List<States>> getQuizStates(int value){
       return stateDao.getQuizStates(constructOptionsQuery(value));
   }

    private SupportSQLiteQuery constructOptionsQuery(int value){
        String q = "SELECT DISTINCT * FROM state_table ORDER BY RANDOM() LIMIT "+value;
        return new SimpleSQLiteQuery(q);
    }

}
