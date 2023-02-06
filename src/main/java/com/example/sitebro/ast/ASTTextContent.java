package com.example.sitebro.ast;

public class ASTTextContent implements ASTLeaf {

    private String text;

    public ASTTextContent(String text) {
        this.text = text;
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