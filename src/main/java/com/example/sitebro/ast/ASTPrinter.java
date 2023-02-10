package com.example.sitebro.ast;

import java.util.function.Function;
import java.util.stream.Stream;

import com.example.sitebro.TagNode;

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

    public ASTPrinter(TagNode html) {
        this(new ASTTag(html));
    }

    @Override
    public String toString() {
        ASTLeaf[] children = tree.children();
        if (children == null)
            return "Tree<>";
        if (children.length == 0)
            return ">>>"+tree.toString();
        Function<ASTLeaf, String> recurse = s -> (s instanceof ASTTree tree)
                ? new ASTPrinter(tree, depth + 1).toString()
                : ">>"+s.content();
        Function<String, String> padding = s -> Stream.generate(() -> Character.toString(' '))
                .limit(depth + 1)
                .reduce(String::concat)
                .orElseGet(() -> "") + s;
        return tree.getClass().getSimpleName() + "<" + tree.content().replace("\n", "\\n") + "\n"
                + Stream.of(children).map(recurse).map(padding).reduce((x, y) -> x + ",\n" + y).orElse("") + ">";
    }

}
