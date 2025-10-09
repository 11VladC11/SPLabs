package com.designpatterns.lab;

public class Image implements Element {
    private String imageName;

    public Image(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public void print() {
        System.out.println("Image with name:" + imageName);
    }

    @Override
    public void add(Element element) {
        throw new UnsupportedOperationException("Cannot add elements to an Image");
    }

    @Override
    public void remove(Element element) {
        throw new UnsupportedOperationException("Cannot remove elements from an Image");
    }

    @Override
    public Element get(int index) {
        throw new UnsupportedOperationException("Cannot get elements from an Image");
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}