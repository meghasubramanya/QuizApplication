package com.example.quizapp.database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizapp.data.States;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {States.class},version = 1,exportSchema = false)
public abstract class StateDatabase extends RoomDatabase {

    public static StateDatabase stateDatabaseInstance=null;

    public abstract StateDao stateDao();

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static  StateDatabase getInstance(final Context context) {
        if (stateDatabaseInstance == null) {
            synchronized (StateDatabase.class)
            {
                if(stateDatabaseInstance==null)
                {
                    stateDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), StateDatabase.class,
                            "note_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            prePopulate(context.getAssets(),stateDatabaseInstance.stateDao());
                                        }
                                    });
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return stateDatabaseInstance;
    }




    public static void prePopulate(AssetManager assetManager, StateDao stateDao) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("state-capital.json")));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);
            }
            json = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            JSONObject object = new JSONObject(json);
            JSONObject sections = object.getJSONObject("sections");
            populateFromJson(sections.getJSONArray("States (A-L)"), stateDao);
            populateFromJson(sections.getJSONArray("States (M-Z)"), stateDao);
            populateFromJson(sections.getJSONArray("Union Territories"), stateDao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void populateFromJson(JSONArray array, StateDao stateDao)
    {
        try {
            for(int i=0;i<array.length();i++)
            {
                JSONObject jsonObject=array.getJSONObject(i);
                String stateName=jsonObject.getString("key");
                String capitalName = jsonObject.getString("val");

                stateDao.insert(new States(stateName,capitalName));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

