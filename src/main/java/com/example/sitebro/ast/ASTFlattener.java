package com.example.sitebro.ast;

import java.util.stream.Stream;

public class ASTFlattener implements ASTTree {

    private ASTLeaf[] children;

    public ASTFlattener(ASTLeaf... childs) {
        this.children = childs;
    }

    @Override
    public ASTLeaf[] children() {
        return Stream.of(children)
                .flatMap(s -> (s instanceof ASTTree t)
                        ? Stream.of(new ASTFlattener(t.children()).children())
                        : Stream.of(s))
                .toArray(ASTLeaf[]::new);
    }

}
