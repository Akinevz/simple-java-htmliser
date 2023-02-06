package com.example.sitebro.ast;

import java.util.Objects;
import java.util.stream.Stream;

@FunctionalInterface
public interface ASTTree extends ASTLeaf {
    ASTLeaf[] children();

    default Stream<ASTLeaf> stream() {
        return Stream.ofNullable(children()).flatMap(Stream::of);
    }

    @Override
    default String content() {
        return stream()
                .map(ASTLeaf::content)
                .filter(Objects::nonNull)
                .reduce(String::concat)
                .orElseGet(() -> "");
    }

    default ASTTree concat(ASTTree other) {
        if (children() == null)
            return other;
        if (other.children() == null)
            return this;
        return () -> Stream.concat(Stream.of(children()), Stream.of(other.children())).toArray(ASTLeaf[]::new);
    }

    default ASTTree append(ASTLeaf next) {
        if (children() == null)
            return () -> new ASTLeaf[] { next };
        return () -> Stream.concat(Stream.of(children()), Stream.of(next)).toArray(ASTLeaf[]::new);
    }

    default ASTTree replace(ASTTree other) {
        return other;
    }

    static ASTTree of(ASTLeaf... a) {
        return () -> Stream.of(a).toArray(ASTLeaf[]::new);
    }

}