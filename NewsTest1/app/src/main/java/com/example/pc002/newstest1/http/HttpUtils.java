package com.example.pc002.newstest1.http;

import android.content.SyncStatusObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pc002 on 2016/9/27.
 */
public class HttpUtils {

    public static String httpGet(String path, String encode){
        String result = null;
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
//            Log.d("main", "code : " + String.valueOf(code));
            if(code == 200){
                InputStream in = connection.getInputStream();
                result = readContent(in, encode);
                in.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    private static String readContent(InputStream in, String encode){
        String result = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len;
        try {
            while ((len = in.read(data)) != -1)
                outputStream.write(data, 0, len);
            result = new String(outputStream.toByteArray(), encode);
//            Log.d("main", result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static Bitmap loadBitmap(String path){
        Bitmap bitmap = null;
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
//            Log.d("main", "code : " + String.valueOf(code));
            if(code == 200){
                InputStream in = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
                in.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  bitmap;
    }

}
