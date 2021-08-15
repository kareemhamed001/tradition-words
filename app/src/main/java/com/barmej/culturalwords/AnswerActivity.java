package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        textView=findViewById(R.id.text_view_answer);
        button=findViewById(R.id.button_return);
        String answer=getIntent().getStringExtra("answer_key");
        if (answer !=null){
        textView.setText(answer);
                        }
    }
    public void back(View view){
        finish();
    }

}