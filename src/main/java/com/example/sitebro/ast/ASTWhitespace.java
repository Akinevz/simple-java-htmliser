package com.example.sitebro.ast;

import java.util.stream.Stream;

public class ASTWhitespace implements ASTLeaf {

    public static ASTLeaf space = new ASTWhitespace(1, ' ');
    final private String content;

    public ASTWhitespace(int depth){
        this(depth, '\t');
    }
    public ASTWhitespace(int depth, char ch) {
        this.content = Stream
                .generate(() -> Character.toString(ch))
                .limit(depth)
                .reduce(String::concat)
                .orElseGet(() -> "");
    }

    @Override
    public String content() {
        return this.content;
    }

    @Override
    public String toString() {
        return "ASTPadding [" +content.length()+ "]";
    }

}