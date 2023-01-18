package com.example.sitebro.ast;

public class ASTNewLine implements ASTLeaf {

    @Override
    public String content() {
        return "\n";
    }

    @Override
    public String toString() {
        return "ASTNewLine []";
    }

}