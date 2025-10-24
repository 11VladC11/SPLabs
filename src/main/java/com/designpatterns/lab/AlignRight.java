package com.designpatterns.lab;

public class AlignRight implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Object context) {
        String text = paragraph.getText();
        int width = 60; 
        if (text.length() >= width) {
            System.out.println("Paragraph: " + text);
            return;
        }
        int padding = width - text.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) sb.append(' ');
        sb.append(text);
        System.out.println("Paragraph:" + sb.toString());
    }
}