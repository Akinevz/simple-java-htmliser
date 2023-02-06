package com.example.sitebro.ast;

import java.util.Optional;
import java.util.stream.Stream;

import org.jsoup.nodes.Attributes;

import com.example.sitebro.TagNode;

public class ASTTagSelfClosing implements ASTTree {

    private String name;
    private Optional<Attributes> attr;

    public ASTTagSelfClosing(String tagName, Attributes attributes) {
        this.name = tagName;
        this.attr = Optional.ofNullable(attributes);
    }

    public ASTTagSelfClosing(TagNode t) {
        this(t.getTagName(), t.getAttributes());
    }

    private Optional<ASTLeaf> attributes() {
        return this.attr.map(ASTTagAttributes::new);
    }

    @Override
    public String content() {
        final var optionalProps = attributes()
                .map(s -> (ASTTree) () -> new ASTLeaf[] { ASTWhitespace.space, s })
                .map(ASTTree::content).orElse("");
        final var selfClosing = String.format("<%s%s />", name, optionalProps);
        return selfClosing;
    }

    @Override
    public String toString() {
        return "ASTTagSelfClosing [" + name + "]";
    }

    @Override
    public ASTLeaf[] children() {
        Optional<ASTLeaf> nameNode = Optional.of(() -> name);
        return Stream.of(nameNode, attributes())
                .flatMap(s -> s.map(Stream::of).orElseGet(Stream::empty))
                .toArray(ASTLeaf[]::new);
    }

}