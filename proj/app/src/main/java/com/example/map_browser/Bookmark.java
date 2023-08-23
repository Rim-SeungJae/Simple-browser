package com.example.map_browser;

public class Bookmark {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String url;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long userid;

    public Bookmark(){}

    public Bookmark(String name,String url,Long userid){
        this.name=name;
        this.url=url;
        this.userid=userid;
    }
}
