package com.designpatterns.lab;

public class Paragraph implements Element {
    private String text;
    private AlignStrategy alignStrategy;

    public Paragraph(String text) {
        this.text = text;
    }

    @Override
    public void print() {
        if (alignStrategy != null) {
            alignStrategy.render(this, null);
        } else {
            System.out.println("Paragraph: " + text);
        }
    }

    @Override
    public void add(Element element) {
        throw new UnsupportedOperationException("Cannot add elements to a Paragraph");
    }

    @Override
    public void remove(Element element) {
        throw new UnsupportedOperationException("Cannot remove elements from a Paragraph");
    }

    @Override
    public Element get(int index) {
        throw new UnsupportedOperationException("Cannot get elements from a Paragraph");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AlignStrategy getAlignStrategy() {
        return alignStrategy;
    }

    public void setAlignStrategy(AlignStrategy alignStrategy) {
        this.alignStrategy = alignStrategy;
    }
}