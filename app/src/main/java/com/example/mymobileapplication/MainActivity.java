package com.example.mymobileapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_EXTRA_ANSWER = "CorrectAnswer";
    private static final String KEY_CURRENT_INDEX = "CurrentIndex";
    private static final int REQUEST_CODE_HINT = 0;
    private static final String tag = "MainActivity";

    private Button trueButton, falseButton, nextButton, hintButtonMain;

    private TextView questionTextView;
    private int currentIndex = 0;
    private int correctAnswersCount = 0;
    private boolean checked = false;
    private boolean answerWasShown;
    private final Question[] questions = new Question[]{
            new Question(R.string.q1, true),
            new Question(R.string.q2, false),
            new Question(R.string.q3, false),
            new Question(R.string.q4, true),
            new Question(R.string.q5, false),
            new Question(R.string.q6, true),
            new Question(R.string.q7, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "Wywołana została metoda: onCreate");
        setContentView(R.layout.activity_main);
        questionTextView = findViewById(R.id.question);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        hintButtonMain = findViewById(R.id.hint_button_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked) {
                    checkAnswer(true);
                    checked = true;
                }
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked) {
                    checkAnswer(false);
                    checked = true;
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown = false;
                setNextQuestion();
                checked = false;

                if (currentIndex == 0) {
                    correctAnswersCount = 0;
                }
            }
        });
        //Pokaż poprawną odpowiedź na pytanie
        hintButtonMain.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent, REQUEST_CODE_HINT);
        });
        setNextQuestion();
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        System.out.println(resultMessageId);
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            resultMessageId = userAnswer == correctAnswer ? R.string.correct_ans : R.string.incorrect_ans;
            correctAnswersCount += userAnswer == correctAnswer ? 1 : 0;
        }
        if (currentIndex == questions.length - 1) {
            String score = getString(R.string.score_message, correctAnswersCount, questions.length);
            Toast.makeText(this, score, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        }
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "Wywołana została metoda: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "Wywołana została metoda: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "Wywołana została metoda: onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "Wywołana została metoda: onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "Wywołana została metoda: onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(tag, "Wywołana została metoda: OnSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_HINT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}
