package com.example.cobasnake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Snake {
    private Bitmap bm, bm_head_bottom, bm_head_left, bm_head_right, bm_head_top,
            bm_body_vertical, bm_body_horizontal, bm_body_bottom_left, bm_body_bottom_right, bm_body_top_left, bm_body_top_right,
            bm_tail_top, bm_tail_bottom, bm_tail_right, bm_tail_left;
    private ArrayList<PartSnake> partSnakeArrayList = new ArrayList<>();
    private int length;
    private boolean move_left, move_right, move_up, move_down;

    public Snake(Bitmap bm, int x, int y, int length) {
        this.bm = bm;
        this.length = length;
        bm_body_bottom_left = Bitmap.createBitmap(bm, 0, 0, bm.getWidth()/14, bm.getHeight());
        bm_body_bottom_right = Bitmap.createBitmap(bm, bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_body_horizontal = Bitmap.createBitmap(bm, 2*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_body_top_left = Bitmap.createBitmap(bm, 3*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_body_top_right = Bitmap.createBitmap(bm, 4*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_body_vertical = Bitmap.createBitmap(bm, 5*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_head_bottom = Bitmap.createBitmap(bm, 6*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_head_left = Bitmap.createBitmap(bm, 7*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_head_right = Bitmap.createBitmap(bm, 8*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_head_top = Bitmap.createBitmap(bm, 9*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_tail_top = Bitmap.createBitmap(bm, 10*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_tail_right = Bitmap.createBitmap(bm, 11*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_tail_left = Bitmap.createBitmap(bm, 12*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        bm_tail_bottom = Bitmap.createBitmap(bm, 13*bm.getWidth()/14, 0, bm.getWidth()/14, bm.getHeight());
        setMove_right(true);
        partSnakeArrayList.add(new PartSnake(bm_head_right, x, y));
        for (int i = 1; i < length-1; i++){
            this.partSnakeArrayList.add(new PartSnake(bm_body_horizontal, this.partSnakeArrayList.get(i-1).getX()-GameView.sizeOfMap, y));
        }
        partSnakeArrayList.add(new PartSnake(bm_tail_right, partSnakeArrayList.get(length-2).getX()-GameView.sizeOfMap, partSnakeArrayList.get(length-2).getY()));
    }

    public void update(){
        for(int i = length-1; i > 0; i--){
            partSnakeArrayList.get(i).setX(partSnakeArrayList.get(i-1).getX());
            partSnakeArrayList.get(i).setY(partSnakeArrayList.get(i-1).getY());
        }
        if(move_right){
            partSnakeArrayList.get(0).setX(partSnakeArrayList.get(0).getX()+GameView.sizeOfMap);
            partSnakeArrayList.get(0).setBm(bm_head_right);
        }else if(move_down){
            partSnakeArrayList.get(0).setY(partSnakeArrayList.get(0).getY()+GameView.sizeOfMap);
            partSnakeArrayList.get(0).setBm(bm_head_bottom);
        }else if(move_up){
            partSnakeArrayList.get(0).setY(partSnakeArrayList.get(0).getY()-GameView.sizeOfMap);
            partSnakeArrayList.get(0).setBm(bm_head_top);
        }else{
            partSnakeArrayList.get(0).setX(partSnakeArrayList.get(0).getX()-GameView.sizeOfMap);
            partSnakeArrayList.get(0).setBm(bm_head_left);
        }
        for (int i = 1; i < length - 1; i++){
            if(partSnakeArrayList.get(i).getRectLeft().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectBottom().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    || partSnakeArrayList.get(i).getRectBottom().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectLeft().intersect(partSnakeArrayList.get(i-1).getRectBody())){
                partSnakeArrayList.get(i).setBm(bm_body_bottom_left);
            }else if (partSnakeArrayList.get(i).getRectLeft().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectTop().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    || partSnakeArrayList.get(i).getRectLeft().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    && partSnakeArrayList.get(i).getRectTop().intersect(partSnakeArrayList.get(i+1).getRectBody())){
                partSnakeArrayList.get(i).setBm(bm_body_top_left);
            }else if (partSnakeArrayList.get(i).getRectRight().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectTop().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    || partSnakeArrayList.get(i).getRectRight().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    && partSnakeArrayList.get(i).getRectTop().intersect(partSnakeArrayList.get(i+1).getRectBody())) {
                partSnakeArrayList.get(i).setBm(bm_body_top_right);
            }else if(partSnakeArrayList.get(i).getRectRight().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectBottom().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    || partSnakeArrayList.get(i).getRectRight().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    && partSnakeArrayList.get(i).getRectBottom().intersect(partSnakeArrayList.get(i+1).getRectBody())){
                partSnakeArrayList.get(i).setBm(bm_body_bottom_right);
            }else if(partSnakeArrayList.get(i).getRectLeft().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    && partSnakeArrayList.get(i).getRectRight().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    || partSnakeArrayList.get(i).getRectLeft().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectRight().intersect(partSnakeArrayList.get(i-1).getRectBody())){
                partSnakeArrayList.get(i).setBm(bm_body_horizontal);
            }else if(partSnakeArrayList.get(i).getRectTop().intersect(partSnakeArrayList.get(i-1).getRectBody())
                    && partSnakeArrayList.get(i).getRectBottom().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    || partSnakeArrayList.get(i).getRectTop().intersect(partSnakeArrayList.get(i+1).getRectBody())
                    && partSnakeArrayList.get(i).getRectBottom().intersect(partSnakeArrayList.get(i-1).getRectBody())){
                partSnakeArrayList.get(i).setBm(bm_body_vertical);
            }else{
                if(move_right){
                    partSnakeArrayList.get(i).setBm(bm_body_horizontal);
                }else if(move_down){
                    partSnakeArrayList.get(i).setBm(bm_body_vertical);
                }else if(move_up){
                    partSnakeArrayList.get(i).setBm(bm_body_vertical);
                }else{
                    partSnakeArrayList.get(i).setBm(bm_body_horizontal);
                }
            }
        }
        if(partSnakeArrayList.get(length-1).getRectRight().intersect(partSnakeArrayList.get(length-2).getRectBody())){
            partSnakeArrayList.get(length-1).setBm(bm_tail_right);
        }else if(partSnakeArrayList.get(length-1).getRectLeft().intersect(partSnakeArrayList.get(length-2).getRectBody())){
            partSnakeArrayList.get(length-1).setBm(bm_tail_left);
        }else if(partSnakeArrayList.get(length-1).getRectBottom().intersect(partSnakeArrayList.get(length-2).getRectBody())){
            partSnakeArrayList.get(length-1).setBm(bm_tail_bottom);
        }else{
            partSnakeArrayList.get(length-1).setBm(bm_tail_top);
        }
    }

    public void drawSnake(Canvas canvas){
        for(int i = length-1; i >= 0; i--){
            canvas.drawBitmap(partSnakeArrayList.get(i).getBm(), partSnakeArrayList.get(i).getX(), partSnakeArrayList.get(i).getY(), null);
        }
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm_head_bottom() {
        return bm_head_bottom;
    }

    public void setBm_head_bottom(Bitmap bm_head_bottom) {
        this.bm_head_bottom = bm_head_bottom;
    }

    public Bitmap getBm_head_left() {
        return bm_head_left;
    }

    public void setBm_head_left(Bitmap bm_head_left) {
        this.bm_head_left = bm_head_left;
    }

    public Bitmap getBm_head_right() {
        return bm_head_right;
    }

    public void setBm_head_right(Bitmap bm_head_right) {
        this.bm_head_right = bm_head_right;
    }

    public Bitmap getBm_head_top() {
        return bm_head_top;
    }

    public void setBm_head_top(Bitmap bm_head_top) {
        this.bm_head_top = bm_head_top;
    }

    public Bitmap getBm_body_vertical() {
        return bm_body_vertical;
    }

    public void setBm_body_vertical(Bitmap bm_body_vertical) {
        this.bm_body_vertical = bm_body_vertical;
    }

    public Bitmap getBm_body_horizontal() {
        return bm_body_horizontal;
    }

    public void setBm_body_horizontal(Bitmap bm_body_horizontal) {
        this.bm_body_horizontal = bm_body_horizontal;
    }

    public Bitmap getBm_body_bottom_left() {
        return bm_body_bottom_left;
    }

    public void setBm_body_bottom_left(Bitmap bm_body_bottom_left) {
        this.bm_body_bottom_left = bm_body_bottom_left;
    }

    public Bitmap getBm_body_bottom_right() {
        return bm_body_bottom_right;
    }

    public void setBm_body_bottom_right(Bitmap bm_body_bottom_right) {
        this.bm_body_bottom_right = bm_body_bottom_right;
    }

    public Bitmap getBm_body_top_left() {
        return bm_body_top_left;
    }

    public void setBm_body_top_left(Bitmap bm_body_top_left) {
        this.bm_body_top_left = bm_body_top_left;
    }

    public Bitmap getBm_body_top_right() {
        return bm_body_top_right;
    }

    public void setBm_body_top_right(Bitmap bm_body_top_right) {
        this.bm_body_top_right = bm_body_top_right;
    }

    public Bitmap getBm_tail_top() {
        return bm_tail_top;
    }

    public void setBm_tail_top(Bitmap bm_tail_top) {
        this.bm_tail_top = bm_tail_top;
    }

    public Bitmap getBm_tail_bottom() {
        return bm_tail_bottom;
    }

    public void setBm_tail_bottom(Bitmap bm_tail_bottom) {
        this.bm_tail_bottom = bm_tail_bottom;
    }

    public Bitmap getBm_tail_right() {
        return bm_tail_right;
    }

    public void setBm_tail_right(Bitmap bm_tail_right) {
        this.bm_tail_right = bm_tail_right;
    }

    public Bitmap getBm_tail_left() {
        return bm_tail_left;
    }

    public void setBm_tail_left(Bitmap bm_tail_left) {
        this.bm_tail_left = bm_tail_left;
    }

    public ArrayList<PartSnake> getPartSnakeArrayList() {
        return partSnakeArrayList;
    }

    public void setPartSnakeArrayList(ArrayList<PartSnake> partSnakeArrayList) {
        this.partSnakeArrayList = partSnakeArrayList;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isMove_left() {
        return move_left;
    }

    public void setMove_left(boolean move_left) {
        this.setup();
        this.move_left = move_left;
    }

    public boolean isMove_right() {
        return move_right;
    }

    public void setMove_right(boolean move_right) {
        this.setup();
        this.move_right = move_right;
    }

    public boolean isMove_up() {
        return move_up;
    }

    public void setMove_up(boolean move_up) {
        this.setup();
        this.move_up = move_up;
    }

    public boolean isMove_down() {
        return move_down;
    }

    public void setMove_down(boolean move_down) {
        this.setup();
        this.move_down = move_down;
    }

    public void setup(){
        this.move_right = false;
        this.move_down = false;
        this.move_left = false;
        this.move_up = false;
    }

    public void addPart() {
        PartSnake p = this.partSnakeArrayList.get(length-1);
        this.length += 1;
        if(p.getBm()==bm_tail_right){
            this.partSnakeArrayList.add(new PartSnake(bm_tail_right, p.getX()-GameView.sizeOfMap, p.getY()));
        }else if(p.getBm()==bm_tail_left){
            this.partSnakeArrayList.add(new PartSnake(bm_tail_left, p.getX()+GameView.sizeOfMap, p.getY()));
        }else if(p.getBm()== bm_tail_top){
            this.partSnakeArrayList.add(new PartSnake(bm_tail_top, p.getX(), p.getY()+GameView.sizeOfMap));
        }else if(p.getBm()== bm_tail_bottom){
            this.partSnakeArrayList.add(new PartSnake(bm_tail_top, p.getX(), p.getY()-GameView.sizeOfMap));
        }
    }
}
