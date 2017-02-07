package com.example.shivam.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LevelPage extends AppCompatActivity implements View.OnClickListener {
    Button GeneralKnowledge ;

    TextView tvName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_page);

        Intent j = getIntent();
        String name = j.getStringExtra("PLAYER_NAME");

        GeneralKnowledge = (Button)findViewById(R.id.GeneralKnowledge);

        tvName = (TextView)findViewById(R.id.textView2);

        GeneralKnowledge.setOnClickListener(this);


        tvName.setText("Hi "+name+",");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId() ;
        String buttonName;
        switch(id){
            case R.id.GeneralKnowledge:
                buttonName = "GeneralKnowledge";
                break;

            default:
                buttonName = null;
        }
        Intent i = new Intent(LevelPage.this,QuizGame.class);
        i.putExtra("QUIZ_SELECT",buttonName);
        startActivity(i);

    }
    public void readGk(String s){

        try {
            JSONObject mainObject = new JSONObject(s);
            JSONArray  generalknowledge = mainObject.getJSONArray("GeneralKnowledge");
            for (int i = 0 ; i<generalknowledge.length() ; i++){
                JSONObject currentObject = generalknowledge.getJSONObject(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
