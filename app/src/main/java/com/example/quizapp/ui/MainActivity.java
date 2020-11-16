package com.example.quizapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.custom.QuizView;
import com.example.quizapp.custom.QuizViewModel;
import com.example.quizapp.data.States;
import com.example.quizapp.settings.SettingsActivity;

import java.util.List;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    private QuizViewModel viewModel;
    private QuizView quizView;
    private SharedPreferences sharedPreferences;
    private String noOfOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        noOfOptions = sharedPreferences.getString("list_preference_3", "Four");
        final int value;
        if(noOfOptions.equals("Four")) {
            value = 4;
        } else {
            value = 3;
        }


        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        quizView = findViewById(R.id.quiz_view);

        viewModel.data.observe(this, new Observer<List<States>>() {
            @Override
            public void onChanged(List<States> states) {
                if(states!=null) {
                    if(states.size() == 4 || states.size() == 3) {
                        quizView.setData(states , value);
                    } else {
                       Toast.makeText(MainActivity.this, "Add more states", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        quizView.setOptionsClickListener(new QuizView.OptionsClickListener() {
            @Override
            public void optionsClicked(Boolean result) {
                updateResult(result);
            }
        });
    }

    private void updateResult(Boolean result) {
        if(result) {
            Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
        }
        viewModel.refreshGame();
        quizView.reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.list:
                Intent intent=new Intent(MainActivity.this, StateListActivityy.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                Intent intent1=new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent1);
                return true;
            default:return super.onContextItemSelected(item);
        }
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            if(s.equals("list_preference_3")) {
                String c = sharedPreferences.getString("list_preference_3" , "Four");
                final int val;
                if(noOfOptions.equals("Four")) {
                    val = 4;
                } else {
                    val = 3;
                }
                viewModel.count.postValue(val);
                viewModel.refreshGame();
                quizView.reset();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!noOfOptions.equals(sharedPreferences.getString("list_preference_3" , "Four"))) {
            viewModel.refreshGame();
            quizView.reset();
            recreate();
        }
    }
}