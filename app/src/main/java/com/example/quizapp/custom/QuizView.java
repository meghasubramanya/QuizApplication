package com.example.quizapp.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.quizapp.data.States;

import java.util.List;
import java.util.Random;

public class QuizView extends LinearLayout {

    private States correctState;
    private int correctOptionId;
    private RadioGroup optionsRadio;
    private OptionsClickListener optionsClickListener;
    
    public QuizView(Context context) {
        super(context);
        initRadios();
    }

    public QuizView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRadios();
    }


    private void initRadios() {
        optionsRadio=new RadioGroup(getContext());
        optionsRadio.setId(View.generateViewId());
    }

    public interface OptionsClickListener
    {
        void optionsClicked(Boolean result);
    }

    public void setOptionsClickListener(OptionsClickListener optionsClickListener)
    {
        this.optionsClickListener=optionsClickListener;
    }

    public void setData(List<States> states ,  int value)
    {
        Random random=new Random(System.currentTimeMillis());
        int correctOption=random.nextInt(value);

        correctState=states.get(correctOption);

        TextView tvQuestion= new TextView(getContext());
        String question="What is the capital of " + correctState.getState();
        tvQuestion.setText(question);

        this.addView(tvQuestion);
        this.addView(optionsRadio);

        RadioButton[] radios= new RadioButton[value];
        radios[correctOption] = new RadioButton(getContext());
        radios[correctOption].setId(View.generateViewId());
        radios[correctOption].setText(correctState.getCapital());

        correctOptionId = radios[correctOption].getId();

        for(int i=0,j=0; i<value && j<value ; i++ , j++)
        {
            if(i==correctOption)
            {
                optionsRadio.addView(radios[correctOption]);
                continue;
            }
            else
            {
                radios[i] = new RadioButton(getContext());
                radios[i].setId(View.generateViewId());
                radios[i].setText(states.get(j).getCapital());
                optionsRadio.addView(radios[i]);
            }
            initListeners();
        }
    }

    private void initListeners() {
        optionsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(optionsClickListener!=null)
                {
                    if(i==correctOptionId)
                    {
                        optionsClickListener.optionsClicked(true);
                    }
                    else
                    {
                        optionsClickListener.optionsClicked(false);
                    }
                }
            }
        });
    }

    public void reset()
    {
        optionsRadio.removeAllViews();
        this.removeAllViews();
    }
}
