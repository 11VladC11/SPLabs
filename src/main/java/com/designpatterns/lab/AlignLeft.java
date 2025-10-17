package com.designpatterns.lab;

public class AlignLeft implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Object context) {
        // Simple left alignment: just print paragraph text as-is
        System.out.println("Paragraph: " + paragraph.getText());
    }
}