package com.example.sitebro.ast;

import java.util.Optional;

@FunctionalInterface
public interface ASTLeaf  {
    String content();

    default boolean isEmpty(){
        return Optional.ofNullable(content()).map(String::isEmpty).orElse(true);
    }

    static ASTLeaf prependSpace(String arg0) {
        return () -> " " + arg0;
    }

}