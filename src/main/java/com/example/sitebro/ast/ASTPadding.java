package com.example.sitebro.ast;

import java.util.stream.Stream;

public class ASTPadding implements ASTLeaf {

    final private String content;

    public ASTPadding(int depth) {
        this.content = Stream.generate(() -> Character.toString('\t')).limit(depth).reduce("", String::concat);
    }

    @Override
    public String content() {
        return this.content;
    }

}