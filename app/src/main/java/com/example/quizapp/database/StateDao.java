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

import com.example.quizapp.data.State;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM state_table")
    DataSource.Factory<Integer,State> getAllStates();

    @RawQuery(observedEntities = State.class)
    DataSource.Factory<Integer, State> getAllSortedStates(SupportSQLiteQuery query);

    @Insert
     void insert(State state);

    @Delete
     void delete(State state);

    @Update
     void update(State state);
}
