package com.example.sitebro.ast;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.sitebro.Tag;

public class ASTTag implements ASTTree {

    private Tag tag;

    public ASTTag(Tag root) {
        this.tag = root;
    }

    @Override
    public ASTLeaf[] children() {
        var children = Stream.ofNullable(tag.getChildren())
                .filter(Objects::nonNull)
                .flatMap(Stream::of)
                .map(ASTTag::new);
        var prefix = Arrays.asList(new ASTTagOpening(tag), new ASTTextContent(tag));
        var suffix = Arrays.asList(new ASTTagClosing(tag));
        var all = Stream.concat(prefix.stream(), Stream.concat(children, suffix.stream()));
        return all.filter(s -> !s.isEmpty()).toArray(ASTLeaf[]::new);

    }

}
