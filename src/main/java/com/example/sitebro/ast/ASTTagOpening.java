package com.example.sitebro.ast;

import java.util.Optional;
import java.util.Properties;

import com.example.sitebro.Prop;
import com.example.sitebro.Tag;
import com.example.sitebro.tags.Div;

public class ASTTagOpening implements ASTLeaf {

    final private Properties props;
    final private String name;
    private String textContent;

    public ASTTagOpening(Tag t) {
        this.name = t.getTagName();
        this.props = t.getProperties();
    }

    public ASTTagOpening(Div d) {
        this.name = d.getTagName();
        this.props = d.getProperties();
        this.textContent = d.getTextContent();
    }

    @Override
    public String content() {
        final var optionalProps = Optional.ofNullable(props)
                .map(Prop::format)
                .map(Tag::prependSpace)
                .map(ASTLeaf::content)
                .orElse("");
        final var opening = String.format("<%s%s>", name, optionalProps);
        if (this.textContent != null) {
            return opening + textContent;
        }
        return opening;
    }
}