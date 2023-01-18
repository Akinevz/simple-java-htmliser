package com.example.sitebro.ast;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ASTIndented implements ASTTree {

    final private boolean should;
    final private ASTLeaf indent;
    final private ASTLeaf[] children;
    private Optional<Predicate<ASTLeaf>> condition = Optional.empty();

    public ASTIndented(ASTLeaf indentToken, boolean condition, ASTLeaf... children) {
        this.should = condition;
        this.children = children;
        this.indent = indentToken;
    }

    ASTIndented indentAfter(Class<?>... classes) {
        this.condition = Optional.of((t) -> Stream.of(classes).anyMatch(s -> should && s.isInstance(t)));
        return this;
    }

    @Override
    public ASTLeaf[] children() {
        var indentable = this.condition.orElse(s -> false);
        return Stream.of(children)
                .flatMap(s -> indentable.test(s) ? Stream.of(s, indent) : Stream.of(s))
                .toArray(ASTLeaf[]::new);
    }

}
