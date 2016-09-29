package com.example.pc002.newstest1;

import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class JumpActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);

        webView = (WebView)findViewById(R.id.webview);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");

        Log.d("main", path);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(path);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }
            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
//                Toast.makeText(WebViewTest.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
