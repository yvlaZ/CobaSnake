package com.example.cobasnake;



import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static ImageView img_swipe;
    public static Dialog dialogScore;
    private GameView gameView;
    public static TextView txt_score, txt_best_score, txt_dialog_score, txt_dialog_best_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        com.example.cobasnake.Constants.SCREEN_WIDTH = dm.widthPixels;
        com.example.cobasnake.Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);
        img_swipe = findViewById(R.id.img_swipe);
        gameView = findViewById(R.id.gv);
        txt_score = findViewById(R.id.txt_score);
        txt_best_score = findViewById(R.id.txt_best_score);
        dialogScore();
    }

    private void dialogScore() {
        int highestScore = 0;
        SharedPreferences sp = this.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sp!=null){
            highestScore = sp.getInt("bestscore",0);
        }
        MainActivity.txt_best_score.setText(highestScore+"");
        dialogScore = new Dialog(this);
        dialogScore.setContentView(R.layout.dialog_start);
        txt_dialog_score = dialogScore.findViewById(R.id.txt_dialog_score);
        txt_dialog_best_score = dialogScore.findViewById(R.id.txt_dialog_best_score);
        txt_dialog_best_score.setText(highestScore + "");
        dialogScore.setCanceledOnTouchOutside(false);
        RelativeLayout rl_start = dialogScore.findViewById(R.id.rl_start);
        rl_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_swipe.setVisibility(View.VISIBLE);
                gameView.reset();
                dialogScore.dismiss();
            }
        });
        dialogScore.show();
    }
}