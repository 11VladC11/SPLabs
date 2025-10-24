package com.designpatterns.lab;

public class AlignLeft implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Object context) {
        System.out.println("Paragraph: " + paragraph.getText());
    }
}