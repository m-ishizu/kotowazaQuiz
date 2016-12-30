package com.example.ishidzumichiko.kotowazaquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView scoreText = (TextView)findViewById(R.id.scoretxt);
        TextView comeText = (TextView)findViewById(R.id.come);
        Intent intent = getIntent();
        scoreText.setText(intent.getStringExtra(QuizActivity.SCORE));
        if (QuizActivity.point == 10){
            comeText.setText("素晴らしい！全問正解です");
        }else if (QuizActivity.point == 9){
            comeText.setText("惜しい！１問間違い");
        }else {
            comeText.setText("頑張ろう！");
        }

    }

    public void replay(View view){
        finish();
    }

}
