package com.example.pc002.newstest1.json;

import android.util.Log;

import com.example.pc002.newstest1.domain.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by pc002 on 2016/9/27.
 */
public class GsonUtils {

    public static News parseNews(String json){
        Gson gson = new Gson();
        News news = gson.fromJson(json, News.class);
        return  news;
    }

    public static List<News> parseNewsList(String json){
        Gson gson = new Gson();
        Log.d("mian", "list: " + json);
        List<News> newsList = gson.fromJson(json, new TypeToken<List<News>>(){}.getType());
        return  newsList;
    }

}
