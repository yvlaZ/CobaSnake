package com.example.cobasnake;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PartSnake {
    private Bitmap bm;
    private int x, y;
    private Rect rectBody, rectTop, rectBottom, rectRight, rectLeft;

    public PartSnake(Bitmap bm, int x, int y) {
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getRectTop() {
        return new Rect(this.x, this.y-10* com.example.cobasnake.Constants.SCREEN_HEIGHT/1920, this.x+GameView.sizeOfMap, this.y);
    }

    public void setRectTop(Rect rectTop) {
        this.rectTop = rectTop;
    }

    public Rect getRectBottom() {
        return new Rect(this.x, this.y + GameView.sizeOfMap, this.x + GameView.sizeOfMap, this.y + GameView.sizeOfMap +10* com.example.cobasnake.Constants.SCREEN_HEIGHT/1920);
    }

    public void setRectBottom(Rect rectBottom) {
        this.rectBottom = rectBottom;
    }

    public Rect getRectRight() {
        return new Rect(this.x + GameView.sizeOfMap, this.y, this.x + GameView.sizeOfMap +10* com.example.cobasnake.Constants.SCREEN_WIDTH/1080, this.y+GameView.sizeOfMap);
    }

    public void setRectRight(Rect rectRight) {
        this.rectRight = rectRight;
    }

    public Rect getRectLeft() {
        return new Rect(this.x - 10* com.example.cobasnake.Constants.SCREEN_WIDTH/1080, this.y, this.x, this.y + GameView.sizeOfMap);
    }

    public void setRectLeft(Rect rectLeft) {
        this.rectLeft = rectLeft;
    }

    public Rect getRectBody() {
        return new Rect(this.x, this.y, this.x + GameView.sizeOfMap, this.y + GameView.sizeOfMap);
    }

    public void setRectBody(Rect rectBody) {
        this.rectBody = rectBody;
    }
}
