package com.example.pc002.newstest1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc002.newstest1.domain.News;
import com.example.pc002.newstest1.http.HttpUtils;
import com.example.pc002.newstest1.json.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc002 on 2016/9/27.
 */
public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<News> newsList = new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ImageHolder imageHolder = (ImageHolder)msg.obj;
            imageHolder.imageView.setImageBitmap(imageHolder.bitmap);
        }
    };

    public NewsAdapter(Context context){
        this.mContext = context;
    }

    public void addNews(List<News> newsList1){
        newsList.addAll(newsList1);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ItemHolder mHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
            mHolder = new ItemHolder();
            mHolder.mImageView = (ImageView)view.findViewById(R.id.item_imageview);
            mHolder.mTitle = (TextView)view.findViewById(R.id.item_title);
            mHolder.mContent = (TextView)view.findViewById(R.id.item_content);
            mHolder.mDate = (TextView)view.findViewById(R.id.item_date);
            view.setTag(mHolder);
        }else {
            mHolder = (ItemHolder)view.getTag();
        }

        final News news = newsList.get(position);
        mHolder.mTitle.setText(news.getTitle());
        mHolder.mContent.setText(news.getDetail());
        mHolder.mDate.setText(news.getTime());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JumpActivity.class);
                intent.putExtra("path", news.getUrl_jump());
                mContext.startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                String path_image = news.getUrl_image();
                Log.d("main", path_image);
                Bitmap bitmap = HttpUtils.loadBitmap(path_image);
                ImageHolder imageHolder = new ImageHolder();
                imageHolder.imageView = mHolder.mImageView;
                imageHolder.bitmap = bitmap;
                Message msg = new Message();
                msg.obj = imageHolder;
                mHandler.sendMessage(msg);
            }
        }).start();

        return view;
    }

    private class ItemHolder{
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mContent;
        public TextView mDate;
    }

    private class ImageHolder{
        public ImageView imageView;
        public Bitmap bitmap;
    }
}
