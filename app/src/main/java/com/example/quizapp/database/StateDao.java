package com.example.quizapp.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.quizapp.data.States;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM state_table")
    DataSource.Factory<Integer, States> loadAllStates();

    @RawQuery(observedEntities = States.class)
    DataSource.Factory<Integer, States> getAllSortedStates(SupportSQLiteQuery query);

    @Insert
     void insert(States state);

    @Delete
     void delete(States state);

    @Update
     void update(States state);

    @Query("SELECT * FROM state_table ORDER BY RANDOM() LIMIT 1")
    States getRandomState();

    @RawQuery(observedEntities = States.class)
    LiveData<List<States>> getQuizStates(SupportSQLiteQuery supportSQLiteQuery);
}
