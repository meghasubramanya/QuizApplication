package com.example.quizapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizapp.R;
import com.example.quizapp.data.State;

public class AddStateActivityy extends AppCompatActivity {

    private static final String EXTRA_ID="Extra_id";
    private static final String EXTRA_STATE="Extra_state";
    private static final String EXTRA_CAPITAL="Extra_capital";

    private AddStateViewModel viewModel;

    EditText etStateName,etCapitalName;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_state_activityy);

        final Bundle extras=getIntent().getExtras();

        etStateName=findViewById(R.id.etStateName);
        etCapitalName=findViewById(R.id.etCapitalName);
        btnAdd=findViewById(R.id.btnAdd);
        viewModel= new ViewModelProvider(this).get(AddStateViewModel.class);

        if(extras!=null)
        {
            String stateName=extras.getString(EXTRA_STATE,"");
            if(!stateName.isEmpty())
            {
                etStateName.setText(stateName);
            }

            String capitalName=extras.getString(EXTRA_CAPITAL,"");
            if(!capitalName.isEmpty())
            {
                etCapitalName.setText(capitalName);
            }

            btnAdd.setText("SAVE");
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stateName=etStateName.getText().toString();
                String capitalName=etCapitalName.getText().toString();

                if(extras!=null && extras.containsKey(EXTRA_ID) )
                {
                    int id=extras.getInt(EXTRA_ID,-1);
                    State state1=new State(id,stateName,capitalName);
                    viewModel.update(state1);
                }
                else
                {
                    State state1=new State(stateName,capitalName);
                    viewModel.insert(state1);
                }

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}


