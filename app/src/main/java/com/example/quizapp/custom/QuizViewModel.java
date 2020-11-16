package com.example.quizapp.custom;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.quizapp.data.States;
import com.example.quizapp.database.StateRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuizViewModel extends AndroidViewModel {

    private StateRepository stateRepository;
    public LiveData<List<States>> data;
    public MutableLiveData<Integer> count = new MutableLiveData<>();
    public MutableLiveData<Integer> increment = new MutableLiveData<>();
    int i = 0;
    CustomLiveData trigger;

    public QuizViewModel(@NonNull Application application) {
        super(application);
        stateRepository=StateRepository.getStateRepository(application);
        count.setValue(4);
        increment.setValue(i);
        trigger = new CustomLiveData(count , increment);
        loadGame();
    }

    private void loadGame() {
       /* try {
            quizData.postValue(stateRepository.getQuizStates().get());
        }catch (Exception e) {
            e.printStackTrace();
        }*/

       data = Transformations.switchMap(trigger, new Function<Pair<Integer, Integer>, LiveData<List<States>>>() {
           @Override
           public LiveData<List<States>> apply(Pair<Integer, Integer> input) {
               return stateRepository.getQuizStates(input.first);
           }
       });
    }

    public void refreshGame() {
        i++;
        increment.postValue(i);
        loadGame();
    }
}
