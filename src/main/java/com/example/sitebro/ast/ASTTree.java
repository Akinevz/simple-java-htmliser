package com.example.sitebro.ast;

import java.util.stream.Stream;

public interface ASTTree extends ASTLeaf {
    ASTLeaf[] children();

    default String content() {
        return Stream.ofNullable(children())
                .flatMap(s -> Stream.of(s)
                        .map(ASTLeaf::content)
                        .filter(x -> x != null))
                .reduce("", String::concat);
    }

    default ASTTree concat(ASTTree other) {
        return () -> Stream.concat(Stream.of(children()), Stream.of(other.children())).toArray(ASTLeaf[]::new);
    }

    default ASTTree append(ASTLeaf newline) {
        return () -> Stream.concat(Stream.of(children()), Stream.of(newline)).toArray(ASTLeaf[]::new);
    }
}