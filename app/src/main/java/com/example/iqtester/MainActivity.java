package com.example.iqtester;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView your_Score, time_Left, question;
    Button yes_Button, no_Button;
    boolean isResultRight;
    int userScore = 0;
    int a;
    int b;
    int currentResult;
    AlertDialog alertDialog;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // lets link it
        your_Score = findViewById(R.id.yourScore);
        time_Left = findViewById(R.id.timeLeft);
        question = findViewById(R.id.question);
        yes_Button = findViewById(R.id.yesButton);
        no_Button = findViewById(R.id.noButton);
        String score = "Your Score = 0";

        your_Score.setText(score);
        createNewQuestion();

        yes_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = a+b;
                if(result == currentResult){
                    //User answers correct
                    updateUserScore();
                    createNewQuestion();

                }
                else {
                    //user answers wrong
                    onGameOver();

                }
            }
        });
        no_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = a+b;
                if(result != currentResult)
                {//User answers correct
                    updateUserScore();
                    createNewQuestion();

                }
                else{
                    //user answers wrong
                    onGameOver();

                }

            }
        });
    }
    private void createNewQuestion(){
        startCountDownTimer();
        a = new Random().nextInt(50);
        b = new Random().nextInt(50);
        int result = a + b;
        isResultRight = new Random().nextBoolean();
        if(isResultRight)
        {
            String myQuestion = a+" + "+b+" = "+result;
            currentResult = result;
            question.setText(myQuestion);
        }
        else{

            int fake = new Random().nextInt(5)+1;
            int wrongResult = result + fake;
            String myQuestion = a+" + "+b+" = "+wrongResult;
            currentResult = wrongResult;
            question.setText(myQuestion);


        }


    }
    private void updateUserScore(){

        userScore = userScore + 10;
        String score = "Your Score = "+ userScore;
        your_Score.setText(score);

    }
    private void onGameOver(){
        time_Left.setText("0 sec");
        if(timer != null){
            timer.cancel();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setTitle("Game Over!").setMessage("Your Score = " + userScore).setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userScore = 0;
                String score = "Your Score = " + userScore;
                your_Score.setText(score);
                createNewQuestion();
            }
        }).setCancelable(false);
        // we will add the builder in the dialog
        alertDialog = builder.create();
        alertDialog.show();
    }
    private void startCountDownTimer(){
        if(timer != null){
            timer.cancel();
        }

        timer = new CountDownTimer(30100,1000) {//1s = 1000 ms
            @Override
            public void onTick(long millisUntilFinished) {
                String timeLeftConverter = "Time Left : " + (millisUntilFinished/1000) +"sec";
                time_Left.setText(timeLeftConverter);


            }

            @Override
            public void onFinish() {
                onGameOver();
            }
        };
        timer.start();
    }
}