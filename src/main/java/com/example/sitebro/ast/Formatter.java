package com.example.sitebro.ast;

import java.util.stream.Stream;

import com.example.sitebro.Tag;

public class Formatter implements ASTTree {
    final private Tag root;

    final static private ASTLeaf newline = new ASTNewLine();

    public Formatter(Tag root) {
        this.root = root;
    }

    protected Stream<ASTLeaf> recurse(int depth, ASTTree r) {
        var indent = new ASTPadding(depth);
        return r.stream().flatMap(s -> (s instanceof ASTTree t)
                ? recurse(depth + 1, t)
                : ASTTree.of(newline, indent, s).stream());

    }

    @Override
    public ASTLeaf[] children() {

        final ASTTree rootNode = new ASTTag(root);

        // final ASTTree flattened = new ASTFlattener(clean);
        // final ASTTree formatted = new ASTFormatted(0, rootNode);

        return recurse(0, rootNode).dropWhile(s -> (s instanceof ASTNewLine n)).toArray(ASTLeaf[]::new);
    }

}