package com.example.pc002.calculator1;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main";
    private GridLayout gridLayout;
    private TextView expression_textview;
    private StringBuilder expression_sb = new StringBuilder();
    PostfixEvaluator evaluator = new PostfixEvaluator();

    private final String[] buttonTexts = new String[]{
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            ".", "0", "=", "+"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (GridLayout)findViewById(R.id.root);
        expression_textview = (TextView)findViewById(R.id.expression_textview);
        addGridLayoutButtons();
    }

    private void addGridLayoutButtons(){
        //获取屏幕的大小
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x - 64;
        int oneQuarterWidth = screenWidth / 4;

        for(int i = 0; i < buttonTexts.length; i ++){
            Button button = new Button(this);
            button.setText(buttonTexts[i]);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn = (Button)v;
                    if(btn.getText().equals("=")){
                        double result = evaluator.evaluator(evaluator.postfixTurn(expression_sb.toString()));
                        Log.d(TAG, "result" + evaluator.postfixTurn(expression_sb.toString()));
                        expression_textview.setText(String.format("%.2f", result));
                        expression_sb.delete(0, expression_sb.length());
                    }else {
                        expression_sb.append(btn.getText());
                        expression_textview.setText(expression_sb.toString());
                    }
                }
            });

            GridLayout.Spec rowSpec = GridLayout.spec(i/ 4 + 2);  //button 所在行
            GridLayout.Spec columnSpec = GridLayout.spec(i % 4);  // button 所在列
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.width = oneQuarterWidth; //button 所占的像素宽度
            gridLayout.addView(button, params); // 将button添加到网格布局中
        }
    }

    public void onAddLeftKuohao(View v){
        expression_sb.append("(");
        expression_textview.setText(expression_sb.toString());
    }

    public void onAddRightKuohao(View v){
        expression_sb.append(")");
        expression_textview.setText(expression_sb.toString());
    }

    public void onClearText(View v){
        if(expression_sb.length() > 0) {
            expression_sb.delete(0, expression_sb.length());
        }
        expression_textview.setText(expression_sb.toString());
    }
    public void onDeleteText(View v){
        if(expression_sb.length() > 0) {
            expression_sb.delete(expression_sb.length()-1, expression_sb.length());
        }
        expression_textview.setText(expression_sb.toString());
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
