package com.example.jsonparsingexample;

public class Movies {

    private String title;
    private String imageUrl;

    public Movies(String mTitle, String mImageUrl){
        this.title = mTitle;
        this.imageUrl = mImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
