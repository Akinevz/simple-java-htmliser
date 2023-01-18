package com.example.sitebro.ast;

import java.util.function.Function;
import java.util.stream.Stream;

import com.example.sitebro.Tag;

public class ASTPrinter {

    private ASTTree tree;
    private int depth;

    public ASTPrinter(ASTTree tree, int depth) {
        this.tree = tree;
        this.depth = depth;
    }

    public ASTPrinter(ASTTree tree) {
        this(tree, 0);
    }

    public ASTPrinter(Tag html) {
        this(new ASTTag(html));
    }

    @Override
    public String toString() {
        ASTLeaf[] children = tree.children();
        if (children.length == 0)
            return tree.getClass().getSimpleName();
        Function<ASTLeaf, String> recurse = s -> (s instanceof ASTTree tree)
                ? new ASTPrinter(tree, depth+1).toString()
                : s.getClass().getSimpleName();
        return "Tree<\n" + Stream.of(children).map(recurse).map(s -> {
            return Stream
                    .generate(() -> Character.toString(' '))
                    .limit(depth+1)
                    .reduce(String::concat)
                    .orElse("") + s;
        }).reduce((x, y) -> x + ",\n" + y).orElse("") + ">";
    }

}
