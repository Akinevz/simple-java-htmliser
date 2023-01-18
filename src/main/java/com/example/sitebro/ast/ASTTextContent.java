package com.example.sitebro.ast;

import com.example.sitebro.Tag;

public class ASTTextContent implements ASTLeaf {

    private String text;

    public ASTTextContent(String text) {
        this.text = text;
    }

    public ASTTextContent(Tag tag) {
        this.text = tag.getTextContent();
    }

    @Override
    public String content() {
        return text;
    }

    @Override
    public String toString() {
        return "ASTTextContent [" + text + "]";
    }

}