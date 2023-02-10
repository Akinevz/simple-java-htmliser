package com.example.sitebro.ast;

import java.util.stream.Stream;

public class ASTFormatter implements ASTTree {
    public ASTFormatter(ASTTag rootNode) {
        this.rootNode = rootNode;
    }

    final private ASTTag rootNode;

    final static private ASTLeaf newline = new ASTWhitespace(1, '\n');

    protected Stream<ASTLeaf> recurse(int depth, ASTTree r) {
        var indent = new ASTWhitespace(depth);
        return r.stream().flatMap(s -> (s instanceof ASTTag t)
                ? recurse(depth + 1, t)
                : ASTTree.of(newline, indent, s).stream());

    }

    @Override
    public ASTLeaf[] children() {
        return recurse(0, rootNode)
                // drop the leading newline
                .dropWhile(s -> (s instanceof ASTWhitespace n))
                .toArray(ASTLeaf[]::new);
    }

}
