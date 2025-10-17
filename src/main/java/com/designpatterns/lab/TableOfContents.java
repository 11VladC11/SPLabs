package com.designpatterns.lab;

import java.util.ArrayList;
import java.util.List;

public class TableOfContents implements Element {
    private List<String> entries = new ArrayList<>();

    public void addEntry(String entry) {
        entries.add(entry);
    }

    @Override
    public void print() {
        System.out.println("Table of Contents");
        for (String e : entries) {
            System.out.println(e);
        }
    }

    @Override
    public void add(Element element) {
        // Table of contents can contain entries as Paragraphs or Sections
        if (element instanceof Paragraph) {
            entries.add(((Paragraph) element).getText());
        } else if (element instanceof Section) {
            entries.add(((Section) element).getTitle());
        } else {
            entries.add(element.toString());
        }
    }

    @Override
    public void remove(Element element) {
        if (element instanceof Paragraph) {
            entries.remove(((Paragraph) element).getText());
        } else if (element instanceof Section) {
            entries.remove(((Section) element).getTitle());
        }
    }

    @Override
    public Element get(int index) {
        throw new UnsupportedOperationException("TableOfContents does not support get by Element");
    }
}