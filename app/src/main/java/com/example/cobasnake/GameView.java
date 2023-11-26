package com.example.cobasnake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private Bitmap bmGrass1, bmGrass2, bmSnake1, bmApple;
    private ArrayList<Grass> grassArray = new ArrayList<>();
    private int width = 12, height = 21;
    public static int sizeOfMap = 75 * com.example.cobasnake.Constants.SCREEN_WIDTH/1080;
    private Snake snake;
    private Apple apple;
    private Handler handler;
    private Runnable r;
    private boolean move = false;
    private float movex, movey;
    public static boolean playing = false;
    public static int score = 0, highestScore = 0;
    private Context context;
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sp!=null){
            highestScore = sp.getInt("highestscore",0);
        }
        bmGrass1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
        bmGrass1 = Bitmap.createScaledBitmap(bmGrass1, sizeOfMap, sizeOfMap, true);
        bmGrass2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass03);
        bmGrass2 = Bitmap.createScaledBitmap(bmGrass2, sizeOfMap, sizeOfMap, true);
        bmSnake1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake1);
        bmSnake1 = Bitmap.createScaledBitmap(bmSnake1, 14* sizeOfMap, sizeOfMap, true);
        bmApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bmApple = Bitmap.createScaledBitmap(bmApple, sizeOfMap, sizeOfMap, true);
        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if((j+i)%2==0){
                    grassArray.add(new Grass(bmGrass1, j*bmGrass1.getWidth() + com.example.cobasnake.Constants.SCREEN_WIDTH/2 - (width /2)*bmGrass1.getWidth(), i*bmGrass1.getHeight()+50* com.example.cobasnake.Constants.SCREEN_HEIGHT/1920, bmGrass1.getWidth(), bmGrass1.getHeight()));
                }else{
                    grassArray.add(new Grass(bmGrass2, j*bmGrass2.getWidth() + com.example.cobasnake.Constants.SCREEN_WIDTH/2 - (width /2)*bmGrass2.getWidth(), i*bmGrass2.getHeight()+50* com.example.cobasnake.Constants.SCREEN_HEIGHT/1920, bmGrass2.getWidth(), bmGrass2.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake1,grassArray.get(126).getX(),grassArray.get(126).getY(), 4);
        apple = new Apple(bmApple, grassArray.get(randomApple()[0]).getX(), grassArray.get(randomApple()[1]).getY());
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    private int[] randomApple(){
        int[] coords = new int[2];
        Random r = new Random();
        coords[0] = r.nextInt(grassArray.size()-1);
        coords[1] = r.nextInt(grassArray.size()-1);
        Rect rect = new Rect(grassArray.get(coords[0]).getX(), grassArray.get(coords[1]).getY(), grassArray.get(coords[0]).getX()+ sizeOfMap, grassArray.get(coords[1]).getY()+ sizeOfMap);
        boolean checkLoop = true;
        while (checkLoop){
            checkLoop = false;
            for (int i = 0; i < snake.getPartSnakeArrayList().size(); i++){
                if(rect.intersect(snake.getPartSnakeArrayList().get(i).getRectBody())){
                    checkLoop = true;
                    coords[0] = r.nextInt(grassArray.size()-1);
                    coords[1] = r.nextInt(grassArray.size()-1);
                    rect = new Rect(grassArray.get(coords[0]).getX(), grassArray.get(coords[1]).getY(), grassArray.get(coords[0]).getX()+ sizeOfMap, grassArray.get(coords[1]).getY()+ sizeOfMap);
                }
            }
        }
        return coords;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventActionMasked = event.getActionMasked();
        switch (eventActionMasked){
            case  MotionEvent.ACTION_MOVE:{
                if(move == false){
                    movex = event.getX();
                    movey = event.getY();
                    move = true;
                }else{
                    if(movex - event.getX() > 100 && !snake.isMove_right()){
                        movex = event.getX();
                        movey = event.getY();
                        this.snake.setMove_left(true);
                        playing = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(event.getX() - movex > 100 &&!snake.isMove_left()){
                        movex = event.getX();
                        movey = event.getY();
                        this.snake.setMove_right(true);
                        playing = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(event.getY() - movey > 100 && !snake.isMove_up()){
                        movex = event.getX();
                        movey = event.getY();
                        this.snake.setMove_down(true);
                        playing = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(movey - event.getY() > 100 && !snake.isMove_down()){
                        movex = event.getX();
                        movey = event.getY();
                        this.snake.setMove_up(true);
                        playing = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                movex = 0;
                movey = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(0xFF456413);
        for(int i = 0; i < grassArray.size(); i++){
            canvas.drawBitmap(grassArray.get(i).getBm(), grassArray.get(i).getX(), grassArray.get(i).getY(), null);
        }
        if(playing){
            snake.update();
            if(snake.getPartSnakeArrayList().get(0).getX() < this.grassArray.get(0).getX()
                    ||snake.getPartSnakeArrayList().get(0).getY() < this.grassArray.get(0).getY()
                    ||snake.getPartSnakeArrayList().get(0).getY()+ sizeOfMap >this.grassArray.get(this.grassArray.size()-1).getY() + sizeOfMap
                    ||snake.getPartSnakeArrayList().get(0).getX()+ sizeOfMap >this.grassArray.get(this.grassArray.size()-1).getX() + sizeOfMap){
                gameOver();
            }
            for (int i = 1; i < snake.getPartSnakeArrayList().size(); i++){
                if (snake.getPartSnakeArrayList().get(0).getRectBody().intersect(snake.getPartSnakeArrayList().get(i).getRectBody())){
                    gameOver();
                }
            }
        }
        snake.drawSnake(canvas);
        apple.draw(canvas);
        if(snake.getPartSnakeArrayList().get(0).getRectBody().intersect(apple.getRect())){
            apple.reset(grassArray.get(randomApple()[0]).getX(), grassArray.get(randomApple()[1]).getY());
            snake.addPart();
            score++;
            MainActivity.txt_score.setText(score+"");
            if(score > highestScore){
                highestScore = score;
                SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("bestscore", highestScore);
                editor.apply();
                MainActivity.txt_best_score.setText(highestScore +"");
            }
        }
        handler.postDelayed(r, 100);
    }

    private void gameOver() {
        playing = false;
        MainActivity.dialogScore.show();
        MainActivity.txt_dialog_best_score.setText(highestScore +"");
        MainActivity.txt_dialog_score.setText(score+"");
    }

    public void reset(){
        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if((j+i)%2==0){
                    grassArray.add(new Grass(bmGrass1, j*bmGrass1.getWidth() + com.example.cobasnake.Constants.SCREEN_WIDTH/2 - (width /2)*bmGrass1.getWidth(), i*bmGrass1.getHeight()+50* com.example.cobasnake.Constants.SCREEN_HEIGHT/1920, bmGrass1.getWidth(), bmGrass1.getHeight()));
                }else{
                    grassArray.add(new Grass(bmGrass2, j*bmGrass2.getWidth() + com.example.cobasnake.Constants.SCREEN_WIDTH/2 - (width /2)*bmGrass2.getWidth(), i*bmGrass2.getHeight()+50* com.example.cobasnake.Constants.SCREEN_HEIGHT/1920, bmGrass2.getWidth(), bmGrass2.getHeight()));
                }
            }
        }
        snake = new Snake(bmSnake1,grassArray.get(126).getX(),grassArray.get(126).getY(), 4);
        apple = new Apple(bmApple, grassArray.get(randomApple()[0]).getX(), grassArray.get(randomApple()[1]).getY());
        score = 0;
    }
}
