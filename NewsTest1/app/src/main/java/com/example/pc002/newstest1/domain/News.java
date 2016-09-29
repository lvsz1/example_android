package com.example.pc002.newstest1.domain;

/**
 * Created by pc002 on 2016/9/27.
 */

public class News {
    private int id;
    private String title;
    private String detail;
    private String news_time;
    private String url_image;
    private String url_jump;

    public News(){

    }

    public News(int id, String title, String detail, String news_time, String url_image, String url_jump) {
        super();
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.news_time = news_time;
        this.url_image = url_image;
        this.url_jump = url_jump;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getTime() {
        return news_time;
    }
    public void setTime(String time) {
        this.news_time = time;
    }
    public String getUrl_image() {
        return url_image;
    }
    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
    public String getUrl_jump() {
        return url_jump;
    }
    public void setUrl_jump(String url_jump) {
        this.url_jump = url_jump;
    }

    @Override
    public String toString() {
        return "News [id=" + id + ", title=" + title + ", detail=" + detail + ", time=" + news_time + ", url_image="
                + url_image + ", url_jump=" + url_jump + "]";
    }
}
