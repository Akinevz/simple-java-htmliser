package com.example.sitebro.ast;

import com.example.sitebro.Tag;

public class ASTTagClosing implements ASTLeaf {
    final private String name;

    public ASTTagClosing(Tag t) {
        this.name = t.getTagName();
    }

    @Override
    public String content() {
        final var closing = String.format("</%s>", name);
        return closing;
    }

    @Override
    public String toString() {
        return "ASTTagClosing [" + name + "]";
    }
    
}