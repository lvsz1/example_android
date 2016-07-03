package com.example.pc002.mazetest1;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Stack;

public class MazaSolver {

    private Maza maza;
    private Stack<Position> stack = new Stack<Position>();
    private int numberRows;
    private int numberColumns;

    public TextView lastTextview;
    public TextView currentTextview;

    public MazaSolver(String filePath) {
        maza = new Maza(filePath);
        numberRows = maza.getRows();
        numberColumns = maza.getColumns();
    }
    public MazaSolver(int[][] grid) {
        maza = new Maza(grid);
        numberRows = maza.getRows();
        numberColumns = maza.getColumns();
    }

    public Stack<Position> findPath(){
        Position startPosition = new Position(0, 0);
        stack.push(startPosition);
        maza.tryPath(0, 0);
        if(traval(0, 0)){
            return stack;
        }else {
            return null;
        }
    }

    private boolean traval(int row, int col){
        if(row == numberRows - 1 && col == numberColumns - 1)
        {
            return true;
        }

        if(maza.isValidPath(row, col + 1)){
            Position position = new Position(row, col + 1);
            stack.push(position);
            maza.tryPath(row, col + 1);

            if(traval(row, col + 1))
                return true;
            maza.backPath(row, col + 1);
        }

        if(maza.isValidPath(row + 1, col)){
            Position position = new Position(row + 1, col);
            stack.push(position);
            maza.tryPath(row + 1, col);

            if(traval(row + 1, col))
                return true;
            stack.pop();
            maza.backPath(row + 1, col);
        }
        if(maza.isValidPath(row, col - 1)){
            Position position = new Position(row, col - 1);
            stack.push(position);
            maza.tryPath(row, col - 1);
            if(traval(row, col - 1))
                return true;
            stack.pop();
            maza.backPath(row, col - 1);
        }
        if(maza.isValidPath(row - 1, col)){
            Position position = new Position(row - 1, col);
            stack.push(position);
            maza.tryPath(row - 1, col);
            if(traval(row - 1, col))
                return true;
            stack.pop();
            maza.backPath(row - 1, col);
        }
        return false;
    }


    public boolean findPath1(GridLayout gridLayout, Handler handler){
        Position startPosition = new Position(0, 0);
        stack.push(startPosition);
        maza.tryPath(0, 0);

        boolean fonud = false;

        currentTextview = (TextView)gridLayout.getChildAt(0);
        lastTextview = currentTextview;

        while(!fonud && !stack.isEmpty()){
            Position tmp = stack.pop();

            maza.tryPath(tmp.row, tmp.col);
            lastTextview = currentTextview;
            currentTextview = (TextView)gridLayout.getChildAt(tmp.row * maza.getColumns() + tmp.col);
//            tmp_view.setBackground(pic);
            Message message = new Message();
            handler.sendMessage(message);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(tmp.row == numberRows - 1 && tmp.col == numberColumns - 1){
                stack.push(tmp);
                fonud = true;
                break;
            }

            if(maza.isValidPath(tmp.row, tmp.col + 1)){
                stack.push(tmp);
                stack.push(new Position(tmp.row, tmp.col + 1));
            }else if(maza.isValidPath(tmp.row + 1, tmp.col)){
                stack.push(tmp);
                stack.push(new Position(tmp.row + 1, tmp.col));
            }else if(maza.isValidPath(tmp.row, tmp.col - 1)){
                stack.push(tmp);
                stack.push(new Position(tmp.row, tmp.col - 1));
            }else if(maza.isValidPath(tmp.row - 1, tmp.col)){
                stack.push(tmp);
                stack.push(new Position(tmp.row - 1, tmp.col));
            }
        }
        return fonud;
    }

    public void markPath(){
        for(int i=0; i<stack.size(); i++){
            Position tmp = stack.elementAt(i);
            maza.setMazaValue(tmp.row, tmp.col, 5);
        }
    }

    public void clear(){
        stack.clear();
    }



    @Override
    public String toString() {
        return maza.toString();
    }

    public static void main(String[] args) {
        // TODO 自动生成的方法存根

        int[][] grid = new int[][]{
                {1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1},
                {1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

//		int[][] grid = new int[][]{
//			{1, 1, 1, 0},
//			{1, 0, 0, 1},
//			{1, 1, 1, 1},
//			{0, 0, 0, 1},
//		};


        MazaSolver mazaSolver = new MazaSolver(grid);

        System.out.println(mazaSolver);

//		mazaSolver.findPath();

//        mazaSolver.findPath1();
//        mazaSolver.markPath();

        System.out.println(mazaSolver);

    }

}
