package com.designpatterns.lab;

public class ImageContent {
    private String url;

    public ImageContent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Image with name:" + url;
    }
}