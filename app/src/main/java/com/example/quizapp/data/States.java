package com.example.quizapp.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "state_table")
public class States {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "state_name")
    private String State;

    @ColumnInfo(name = "capital_name")
    private String Capital;

    public States()
    {

    }

    public States(int id, String state, String capital) {
        this.id = id;
        State = state;
        Capital = capital;
    }

    @Ignore
    public States(String state, String capital) {
        State = state;
        Capital = capital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public boolean equals(States state)
    {
        return (State== state.getState())&& (Capital==state.getCapital());
    }
}
