package com.example.sitebro.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.NonNull;

@FunctionalInterface
public interface ASTTree extends ASTLeaf {
    ASTLeaf[] children();

    default boolean isLeaf() {
        return stream().anyMatch(s -> s instanceof ASTTree == false);
    }

    default ASTLeaf first() {
        return Optional.ofNullable(children()).map(s -> s[0]).orElse(null);
    }

    default Stream<ASTLeaf> stream() {
        return Stream.ofNullable(children()).flatMap(Stream::of);
    }

    @Override
    default String content() {
        return stream()
                .map(ASTLeaf::content)
                .filter(x -> x != null)
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

    default ASTTree rewrite(String mainId, @NonNull ASTTree arg1) {
        if (children() == null) {
            return arg1;
        }

        boolean replacing = false;
        List<ASTLeaf> ret = new ArrayList<>();

        return () -> ret.toArray(ASTLeaf[]::new);
    }

    static ASTTree of(ASTLeaf... s) {
        return () -> s;
    }

}