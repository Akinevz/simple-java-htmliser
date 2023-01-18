package com.example.sitebro.ast;

import java.util.stream.Stream;

import com.example.sitebro.Tag;

public class Formatter implements ASTTree {

    final private ASTTag rootNode;

    final static private ASTLeaf newline = new ASTNewLine();

    public Formatter(Tag root) {
        this.rootNode = new ASTTag(root);
    }

    public Formatter(ASTTag root) {
        this.rootNode = root;
    }

    protected Stream<ASTLeaf> recurse(int depth, ASTTree r) {
        var indent = new ASTPadding(depth);
        return r.stream().flatMap(s -> (s instanceof ASTTree t)
                ? recurse(depth + 1, t)
                : ASTTree.of(newline, indent, s).stream());

    }

    @Override
    public ASTLeaf[] children() {
        return recurse(0, rootNode)
                // drop the leading newline
                .dropWhile(s -> (s instanceof ASTNewLine n))
                .toArray(ASTLeaf[]::new);
    }

}