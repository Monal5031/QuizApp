package com.example.shivam.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuizGame extends AppCompatActivity implements View.OnClickListener {


    TextView counter ;
    RadioGroup rg ;
    int score = 10;
    String answer = "";
    int qn = 0;
    TextView temp;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    TextView textView4;

    Button next;
    String squiz;

    String radiovalue="";
    ArrayList<String> questions= new ArrayList<>();
    ArrayList<ArrayList<String>> options= new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();





    public void startCounter() {
        CountDownTimer myCountDown = new CountDownTimer(61000, 1000) {
            public void onTick(long millisUntilFinished) {
                //update the UI with the new count
                counter.setText((millisUntilFinished)/1000-1+"s remaining");






            }

            public void onFinish() {
                //start the activity
                qn++;
                if(qn<5) {
                    setter();
                }
                else{
                    Intent c = new Intent(QuizGame.this,ScorePage.class);
                    c.putExtra("SCORE",score+"");
                    startActivity(c);
                }



            }

        };
//start the countDown
        myCountDown.start();
    }
    public void getcheckedRadioButton(){
         radiovalue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
         temp.setText(radiovalue);



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        Intent i = getIntent();

        squiz= i.getStringExtra("QUIZ_SELECT");

        counter = (TextView)findViewById(R.id.textView5);
        rg = (RadioGroup)findViewById(R.id.radioGroup);
        temp = (TextView)findViewById(R.id.textView6);
        option1 = (RadioButton)findViewById(R.id.Option1);
        option2 = (RadioButton)findViewById(R.id.Option2);
        option3 = (RadioButton)findViewById(R.id.Option3);
        option4 = (RadioButton)findViewById(R.id.Option4);
        next = (Button)findViewById(R.id.button);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        textView4 = (TextView) findViewById(R.id.textView4);

        String completeQuiz = readFromFile("quiz.json",QuizGame.this);
        convertToJSONData(completeQuiz);
        setter();



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(radiovalue.equals(answers.get(qn))){
                    score+=10;

                }
                qn++;
                if(qn<5){
                    setter();
                }
                else {
                    Intent c = new Intent(QuizGame.this, ScorePage.class);
                    c.putExtra("SCORE", score + "");
                    startActivity(c);
                }
            }
        });





            startCounter();






    }


    @Override
    public void onClick(View v) {
        getcheckedRadioButton();
    }

    private void convertToJSONData(String completeQuiz) {
        try {
            JSONObject mainObject = new JSONObject(completeQuiz);
            //JSONObject quiz = mainObject.getJSONObject(squiz);

            JSONArray ques = mainObject.getJSONArray(squiz);
            JSONObject quiz = ques.getJSONObject(0);

            for(int i=0;i<5;i++){
                String s= "Q"+(i+1);
                //JSONObject currentobject = quiz.getJSONObject(s);
                JSONArray currentlist = null;
                try {
                    currentlist = quiz.getJSONArray(s);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                ArrayList<String> inoptions = new ArrayList<>();
                //JSONArray currentlist = currentobject.getJSONArray(s);
                for(int j=0;j<currentlist.length();j++) {
                    JSONObject a = null;
                    try {
                        a = currentlist.getJSONObject(j);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    String q=null;
                    switch (j) {
                        case 0:
                            try {
                                q = a.getString("Question");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            questions.add(q);
                            break;
                        case 1:
                            try {
                                q = a.getString("OptionA");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            inoptions.add(q);
                            break;
                        case 2:
                            try {
                                q = a.getString("OptionB");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            inoptions.add(q);
                            break;
                        case 3:
                            try {
                                q = a.getString("OptionC");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            inoptions.add(q);
                            break;
                        case 4:
                            try {
                                q = a.getString("OptionD");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            inoptions.add(q);
                            break;
                        case 5:
                            try {
                                q = a.getString("Answer");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            answers.add(q);
                            break;
                        default:
                            break;
                    }
                }
                options.add(inoptions);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String readFromFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }


    public void setter(){

        textView4.setText(questions.get(qn));
        ArrayList<String> ops = new ArrayList<>();
        ops = options.get(qn);

        option1.setText(ops.get(0));
        option2.setText(ops.get(1));
        option3.setText(ops.get(2));
        option4.setText(ops.get(3));




    }





}
