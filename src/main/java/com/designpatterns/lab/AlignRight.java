package com.designpatterns.lab;

public class AlignRight implements AlignStrategy {
    @Override
    public void render(Paragraph paragraph, Object context) {
        // Simple right alignment: pad with spaces to simulate right alignment
        String text = paragraph.getText();
        int width = 60; // arbitrary width for demonstration
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