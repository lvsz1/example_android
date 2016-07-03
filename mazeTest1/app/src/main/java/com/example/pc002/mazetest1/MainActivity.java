package com.example.pc002.mazetest1;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main";
    private GridLayout gridLayout;

    private int numberRows;
    private int numberColumns;
    private Drawable picDog;

    MazaSolver mazaSolver;

    private int[][] grid = new int[][]{
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

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "hello world");
            mazaSolver.lastTextview.setBackgroundColor(Color.BLUE);
            mazaSolver.currentTextview.setBackground(picDog);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (GridLayout)findViewById(R.id.gridlayout);
        numberRows = grid.length;
        numberColumns = grid[0].length;

        mazeCreate();
        picDog = getResources().getDrawable(R.drawable.dog);

        mazaSolver = new MazaSolver(grid);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mazaSolver.findPath1(gridLayout, handler);
//
////                TextView tmp_view = (TextView)gridLayout.getChildAt(15);
////                tmp_view.setBackgroundColor(Color.BLACK);
//
//            }
//        }).start();

        Button start_button = (Button)findViewById(R.id.start_buttion);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mazaSolver.clear();
                        mazaSolver.findPath1(gridLayout, handler);

//                TextView tmp_view = (TextView)gridLayout.getChildAt(15);
//                tmp_view.setBackgroundColor(Color.BLACK);

                    }
                }).start();
            }
        });
    }

    private void mazeCreate(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int oneQuarterWidth = (int) ((screenWidth - 64) / 13);

        for(int i = 0; i < numberRows; i ++){
            for(int j = 0; j < numberColumns; j ++){
                TextView view = new TextView(this);

                GridLayout.Spec rowSpec = GridLayout.spec(i);
                GridLayout.Spec columnSpec = GridLayout.spec(j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.width = oneQuarterWidth;
                params.height = oneQuarterWidth;
                gridLayout.addView(view, params);

                if(grid[i][j] == 1){
                    view.setBackgroundColor(Color.BLUE);
                }else {
                    view.setBackgroundColor(Color.GREEN);
                    Drawable pic1 = getResources().getDrawable(R.drawable.wall);
                    view.setBackground(pic1);
                }
                if((i == 0 && j == 0) || (i == numberRows - 1) && (j == numberColumns - 1)){
                    Drawable pic1 = getResources().getDrawable(R.drawable.dog);
                    view.setBackground(pic1);

                }
                Log.d(TAG, Integer.toString(view.getId()));
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
