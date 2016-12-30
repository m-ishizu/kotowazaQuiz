package com.example.ishidzumichiko.kotowazaquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    public final static String SCORE = "com.example.ishidzumichiko.kotowazaquiz";
    public ArrayList<String[]> quizSet = new ArrayList<String[]>();
    int count;
    //正解数/staticを付けてScoreActivityから呼び出せるようにする。
    static int point = 0;

    private TextView scoreText; //問題数の表示
    private TextView question;  //問題の表示

    //各ボタン
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button nextButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        getView();  //Viewのリソース取得メソッド

        loadFile();  //ファイルを呼び込むメッソド

        setQuiz();  //クイズ素材をテキストボタンに配置


    }

    private void setQuiz() {
        question.setText(quizSet.get(count)[0]);

        ArrayList answers = new ArrayList();
        /*
        答えを答え用の配列に格納する。
         */
        for (int i = 1; i <= 4; i++) {
            answers.add(quizSet.get(count)[i]);
        }

        Collections.shuffle(answers);  //答えをランダムにボタンに配置する処理

        button1.setText((Integer) answers.get(0));
        button2.setText((Integer) answers.get(1));
        button3.setText((Integer) answers.get(2));
        button4.setText((Integer) answers.get(3));

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);

        nextButton.setEnabled(false);
        scoreText();

    }

    private void scoreText() {
        if ((count + 1) == quizSet.size()) {
            scoreText.setText("最終問題");
        } else {
            scoreText.setText((count + 1) + "問目");
        }
    }

    private void loadFile() {
        /*
        inputStreamとBufferedReader宣言
        Assetsフォルダのクイズテキストから文字を取り出していく一連の処理。
         */
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = getAssets().open("kotowaza");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            /*
            クイズの問題を1行ずつquizSetに出力していく
             */
            while ((s = bufferedReader.readLine()) != null) {
                /*
                quizSetの配列にクイズtxtのtab文字区切りで配列に追加する
                 */
                quizSet.add(s.split("¥t"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkAnswer(View view) {

        //answer
        Button clickButton = (Button) view;
        String clickAnswer = clickButton.getText().toString();

        //judge
        if (clickAnswer.equals(quizSet.get(count)[1])) {
            clickButton.setText("正解");
            point++;
        } else {
            clickButton.setText("間違い");
        }
        scoreText();

        //button
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        nextButton.setEnabled(false);

        count++;
        if (count == quizSet.size()) {
            nextButton.setText("結果");
        }
    }

    public void nextButton(View view){

        if (count == quizSet.size()){

            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra(SCORE, point + "/" + quizSet.size());
            startActivity(intent);
        }else {
            setQuiz();
        }
    }



    /*
    スコア画面から戻った時にリセットする処置
     */
    @Override
    public void onResume() {
        super.onResume();
        nextButton.setText("次へ");
        count = 0;
        point = 0;
        setQuiz();
    }

    private void getView() {
        scoreText = (TextView) findViewById(R.id.score);
        question = (TextView) findViewById(R.id.question);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        nextButton = (Button) findViewById(R.id.next_b);
    }


}
