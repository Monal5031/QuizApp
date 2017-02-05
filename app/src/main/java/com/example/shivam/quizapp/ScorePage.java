package com.example.shivam.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScorePage extends AppCompatActivity {

    TextView score1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);

        score1 = (TextView)findViewById(R.id.textView8);
        Intent i = getIntent();
        String score = i.getStringExtra("SCORE");
        score1.setText(score);


    }
}
