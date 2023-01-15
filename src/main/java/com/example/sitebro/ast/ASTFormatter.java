package com.example.sitebro.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.example.sitebro.Tag;

public class ASTFormatter {
    final private Tag root;
    final private int depth;
    final private int maxdepth;

    static final ASTLeaf newline = new ASTNewLine();

    public ASTFormatter(int depth, Tag root) {
        this(depth, Integer.MAX_VALUE, root);
    }

    public ASTFormatter(int depth, int fmtDepth, Tag root) {
        this.root = root;
        this.depth = depth;
        this.maxdepth = fmtDepth;
    }

    public ASTTree tree() {
        final ASTLeaf opening = new ASTTagOpening(root);
        final ASTLeaf closing = new ASTTagClosing(root);
        final ASTLeaf indent = new ASTPadding(depth);
        final ASTLeaf textContent = new ASTTextContent(root);

        final var children = root.getChildren();

        for (Tag child : children) {
            var fmt = new ASTFormatter(depth + 1, child);
            fmt.tree().children();
        }

        Optional<ASTTree> content = Stream.of(children)
                .map(child -> new ASTFormatter(depth + 1, maxdepth, child))
                .map(ASTFormatter::tree).reduce((arg0, arg1) -> arg0.append(newline).concat(arg1));

        return format(indent, opening, textContent, content.orElse(null), closing, depth < maxdepth);
    }

    private ASTTree format(ASTLeaf indent, ASTLeaf opening,
            ASTLeaf textContent, ASTTree children,
            ASTLeaf closing, boolean formatted) {

        List<ASTLeaf> ret = new ArrayList<>();
        if (formatted)
            ret.add(indent);
        ret.add(opening);
        if (textContent != null) {
            ret.add(textContent);
        }
        if (children != null) {
            if (formatted)
                ret.add(newline);
            ret.add(children);
        }
        if (formatted)
            ret.add(indent);
        ret.add(newline);
        if (formatted)
            ret.add(indent);
        ret.add(closing);
        return () -> ret.toArray(ASTLeaf[]::new);
    }
}