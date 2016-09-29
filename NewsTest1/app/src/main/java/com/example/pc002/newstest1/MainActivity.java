package com.example.pc002.newstest1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.pc002.newstest1.domain.News;
import com.example.pc002.newstest1.http.HttpUtils;
import com.example.pc002.newstest1.json.GsonUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private NewsAdapter mAdapter;

    private String path1 = "http://58.154.207.104:8080/NewsService1/Login?type=news&&id=1";
    private String path2 = "http://58.154.207.104:8080/NewsService1/Login?type=newsList&&id1=1&&id2=5";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.news_listview);

        mAdapter = new NewsAdapter(this);
        listView.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = HttpUtils.httpGet(path2, "utf-8");
                List<News> newsList1 = GsonUtils.parseNewsList(json);
                mAdapter.addNews(newsList1);

                Message msg = new Message();
                mHandler.sendMessage(msg);
            }
        }).start();

    }
}
