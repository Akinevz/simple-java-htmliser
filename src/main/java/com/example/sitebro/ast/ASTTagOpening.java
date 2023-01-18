package com.example.sitebro.ast;

import java.util.Optional;
import java.util.Properties;

import com.example.sitebro.Prop;
import com.example.sitebro.Tag;

public class ASTTagOpening implements ASTLeaf {

    final private String name;
    final private Optional<Properties> props;

    public ASTTagOpening(String tagName, Properties properties) {
        this.name = tagName;
        this.props = Optional.ofNullable(properties);
    }

    public ASTTagOpening(Tag t) {
        this(t.getTagName(), t.getProperties());
    }

    public String getTagName(){
        return this.name;
    }

    public String getTagId() {
        return this.props.map(s->s.getProperty("id")).orElse(null);
    }

    @Override
    public String content() {
        final var optionalProps = props
                .map(Prop::cast)
                .map(ASTLeaf::content)
                .orElse("");
        final var opening = String.format("<%s%s>", name, optionalProps);
        return opening;
    }

    @Override
    public String toString() {
        return "ASTTagOpening [" + name + "]";
    }
    
}