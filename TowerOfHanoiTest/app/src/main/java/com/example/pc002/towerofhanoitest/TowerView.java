package com.example.pc002.towerofhanoitest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Camera;
import android.view.View;

import java.util.Stack;
import java.util.Vector;

/**
 * Created by pc002 on 2016/7/4.
 */
public class TowerView extends View {
    private Paint paint;
    private Paint paintTower;

    private int from, to;
    private Point startPoint;
    private Point currentPoint;
    private Point endPoint;
    private int dx;

    public boolean START_MOVE = false;
    public boolean FINISH_MOVE = true;

    private Stack<Tower> firstStack;
    private Stack<Tower> secondStack;
    private Stack<Tower> thirdStack;
    private Tower outTower;
    private boolean firstStart = true;

    public TowerView(Context context){
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);

        paintTower = new Paint();
        paintTower.setColor(Color.RED);
        paintTower.setStrokeWidth(5);

        startPoint = new Point();
        currentPoint = new Point();
        endPoint = new Point();
    }

    public void setStack(Stack<Tower> firstStack, Stack<Tower> secondStack, Stack<Tower> thirdStack){
        this.firstStack = firstStack;
        this.secondStack = secondStack;
        this.thirdStack = thirdStack;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawPillarsAndTowers(canvas);
        if(!firstStart) {
            moveOneToOther(canvas);
        }
        firstStart = false;
    }

    //绘制三个柱子以及柱子上的盘子
    private void drawPillarsAndTowers(Canvas canvas){
        canvas.drawRect(25, 600, 225, 610, paint);
        canvas.drawRect(275, 600, 475, 610, paint);
        canvas.drawRect(525, 600, 725, 610, paint);
        paint.setStrokeWidth(6);
        canvas.drawLine(125, 200, 125, 600, paint);
        canvas.drawLine(375, 200, 375, 600, paint);
        canvas.drawLine(625, 200, 625, 600, paint);

        drawTowersPerPillar(canvas, firstStack, 125);
        drawTowersPerPillar(canvas, secondStack, 375);
        drawTowersPerPillar(canvas, thirdStack, 625);
    }

    //根据每个柱子上的盘子，绘制盘子的堆叠图像
    private void drawTowersPerPillar(Canvas canvas, Stack<Tower> towerStack, int center){
        for(int i = 0; i < towerStack.size(); i ++){
            Tower towerTmp = towerStack.get(i);
            int x1 = center - towerTmp.width / 2;
            int y1 = 580 - 20 * i;
            int x2 = center + towerTmp.width / 2;
            int y2 = y1 + 20;
            paintTower.setColor(towerTmp.color);
            canvas.drawRect(x1, y1, x2, y2, paintTower);
        }
    }

    private void moveOneToOther(Canvas canvas){
        calculatePosition();
        paintTower.setColor(outTower.color);
        canvas.drawRect(currentPoint.x, currentPoint.y, currentPoint.x + outTower.width, currentPoint.y + 20, paintTower);
    }


    //计算起始点和终止点，每个盘子第一次移动的时候调用
    public void calculatePoint(){
        switch (from){
            case 1:
                outTower = firstStack.pop();
                startPoint.x = 125 - outTower.width / 2;
                startPoint.y = 600 - 20 * (firstStack.size() + 1);
                break;
            case 2:
                outTower = secondStack.pop();
                startPoint.x = 375 - outTower.width / 2;
                startPoint.y = 600 - 20 * (secondStack.size() + 1);
                break;
            case 3:
                outTower = thirdStack.pop();
                startPoint.x = 625 - outTower.width / 2;
                startPoint.y = 600 - 20 * (thirdStack.size() + 1);
                break;
        }
        currentPoint.x = startPoint.x;
        currentPoint.y = startPoint.y;
        switch (to){
            case 1:
                endPoint.x = 125 - outTower.width / 2;
                endPoint.y = 600 - 20 * (firstStack.size() + 1);
                break;
            case 2:
                endPoint.x = 375 - outTower.width / 2;
                endPoint.y = 600 - 20 * (secondStack.size() + 1);
                break;
            case 3:
                endPoint.x = 625 - outTower.width / 2;
                endPoint.y = 600 - 20 * (thirdStack.size() + 1);
                break;
        }

    }

    //计算盘子在柱子间移动时的位置坐标
    private void calculatePosition(){
        if(START_MOVE){
            START_MOVE = false;
            calculatePoint();

            if(from > to){
                dx = -5;
            }else {
                dx = 5;
            }
        }else if(!FINISH_MOVE) {
            if(currentPoint.y > 150 && currentPoint.x == startPoint.x){
                currentPoint.y -= 5;
            }else if(currentPoint.y == 150 && currentPoint.x != endPoint.x){
                currentPoint.x += dx;
            }else if(currentPoint.y != endPoint.y && currentPoint.x == endPoint.x){
                currentPoint.y += 5;
            }else if(currentPoint.equals(endPoint.x, endPoint.y)){
                FINISH_MOVE = true;
                switch (to){
                    case 1:
                        firstStack.push(outTower);
                        break;
                    case 2:
                        secondStack.push(outTower);
                        break;
                    case 3:
                        thirdStack.push(outTower);
                        break;
                }
            }
        }
    }

    public void setFromAndTo(int from, int to){
        this.from = from;
        this.to = to;
    }
}
