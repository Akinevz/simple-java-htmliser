package com.example.sitebro.ast;

public class ASTWhiteSpace implements ASTLeaf {

    @Override
    public String content() {
        return " ";
    }

    @Override
    public String toString() {
        return "ASTWhiteSpace []";
    }

}