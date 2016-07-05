package com.example.pc002.towerofhanoitest;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import java.util.Stack;
import java.util.Vector;

/**
 * Created by pc002 on 2016/7/4.
 */
public class TowerOfHanoiSolver {
    private static final String TAG = "main";
    private int totalDisks;
    private Stack<Tower> firstStack;
    private Stack<Tower> secondStack;
    private Stack<Tower> thirdStack;

    private TowerView towerView;
    private Vector<Integer> colorVector;

    public TowerOfHanoiSolver(int totalDisks, TowerView view) {
        this.totalDisks = totalDisks;
        this.towerView = view;

        firstStack = new Stack<>();
        secondStack = new Stack<>();
        thirdStack = new Stack<>();

        colorVector = new Vector<>();
        colorVector.add(Color.DKGRAY);
        colorVector.add(Color.CYAN);
        colorVector.add(Color.GREEN);
        colorVector.add(Color.LTGRAY);
        colorVector.add(Color.YELLOW);

        for(int i = 0; i < totalDisks; i ++){
            firstStack.add(new Tower(150 - i * 10, colorVector.get(i % colorVector.size())));
        }

        towerView.setStack(firstStack, secondStack, thirdStack);
    }

    public void solve(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                moveTower(totalDisks, 1, 3, 2);
            }
        }).start();
    }

    public void moveTower(int nums, int start, int end, int temp){
        if(nums == 1){
            moveOneDisk(start, end);
        }else {
            moveTower(nums - 1, start, temp, end);
            moveOneDisk(start, end);
            moveTower(nums - 1, temp, end, start);
        }
    }

    //每次只移动一个盘子
    public void moveOneDisk(int start, int end){
        Log.d(TAG, "move disk from " + start + " to " + end);

        towerView.START_MOVE = true;
        towerView.FINISH_MOVE = false;
        towerView.setFromAndTo(start, end);

        while (!towerView.FINISH_MOVE) {
            towerView.postInvalidate();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
