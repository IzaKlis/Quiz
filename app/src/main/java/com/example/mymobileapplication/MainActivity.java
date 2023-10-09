package com.example.mymobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private Button trueButton;
private Button falseButton;
private Button nextButton;
private TextView questionTextView;
private int currentIndex=0;
    private final Question[] questions=new Question[]{
            new Question(R.string.q1,true),
            new Question(R.string.q2,false),
            new Question(R.string.q3,false),
            new Question(R.string.q4,true),
            new Question(R.string.q5,false),
            new Question(R.string.q6,true),
            new Question(R.string.q7,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView=findViewById(R.id.question);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex=(currentIndex+1)%questions.length;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }
    private void checkAnswer(boolean userAnswer){
        boolean correctAnswer= questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(userAnswer== correctAnswer){
            resultMessageId=R.string.correct_ans;
        }else{
            resultMessageId=R.string.incorrect_ans;
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
}