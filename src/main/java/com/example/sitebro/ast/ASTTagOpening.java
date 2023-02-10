package com.example.sitebro.ast;

import java.util.Optional;
import java.util.stream.Stream;

import org.jsoup.nodes.Attributes;

import com.example.sitebro.TagNode;

public class ASTTagOpening implements ASTTree {

    final private String name;
    final private Optional<Attributes> attr;

    public ASTTagOpening(String tagName, Attributes attributes) {
        this.name = tagName;
        this.attr = Optional.ofNullable(attributes);
    }

    public ASTTagOpening(TagNode t) {
        this(t.getTagName(), t.getAttributes());
    }

    public String getTagName() {
        return this.name;
    }

    public String getTagId() {
        return this.attr.map(s -> s.get("id")).orElse(null);
    }

    private Optional<ASTLeaf> attributes() {
        return this.attr.map(ASTTagAttributes::new);
    }

    @Override
    public String content() {
        final var optionalProps = attributes()
                .map(s -> (ASTTree) () -> new ASTLeaf[] { ASTWhitespace.space, s })
                .map(ASTTree::content).orElse("");
        final var opening = String.format("<%s%s>", name, optionalProps);
        return opening;
    }


    @Override
    public String toString() {
        return "ASTTagOpening [name=" + name + "]";
    }

    @Override
    public ASTLeaf[] children() {
        Optional<ASTLeaf> nameNode = Optional.of(() -> name);
        return Stream.of(nameNode, attributes())
                .flatMap(s -> s.map(Stream::of).orElseGet(Stream::empty))
                .toArray(ASTLeaf[]::new);
    }

}