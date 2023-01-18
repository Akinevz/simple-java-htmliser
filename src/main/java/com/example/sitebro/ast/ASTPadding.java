package com.example.sitebro.ast;

import java.util.stream.Stream;

public class ASTPadding implements ASTLeaf {

    final private String content;

    public ASTPadding(int depth) {
        this.content = Stream
                .generate(() -> Character.toString('\t'))
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