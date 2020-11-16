package com.example.quizapp.custom;

import android.util.Pair;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class CustomLiveData extends MediatorLiveData<Pair<Integer,Integer>> {
    public CustomLiveData(final MutableLiveData<Integer> count , final MutableLiveData<Integer> increment) {
        addSource(count, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setValue(Pair.create(integer , increment.getValue()));
            }
        });
        addSource(increment, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setValue(Pair.create(count.getValue() , integer));
            }
        });
    }
}
