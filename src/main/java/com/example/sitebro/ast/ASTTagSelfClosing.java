package com.example.sitebro.ast;

import java.util.Optional;
import java.util.Properties;

import com.example.sitebro.Prop;
import com.example.sitebro.Tag;

public class ASTTagSelfClosing implements ASTLeaf {
    private String name;
    private Properties props;

    public ASTTagSelfClosing(Tag t) {
        this.name = t.getTagName();
        this.props = t.getProperties();
    }

    @Override
    public String content() {
        final var properties = Optional.ofNullable(props)
                .map(Prop::format)
                .map(Tag::prependSpace)
                .map(ASTLeaf::content)
                .orElse("");
        final var selfClosing = String.format("<%s%s />", name, properties);
        return selfClosing;
    }
}