package com.example.sitebro.ast;

import java.util.function.BinaryOperator;
import java.util.function.Function;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

public class ASTTagAttributes implements ASTLeaf {

    private Attributes attr;

    public ASTTagAttributes(Attributes attributes) {
        this.attr = attributes;
    }

    @Override
    public String content() {
        Function<Attribute, String> attr2String = (Attribute attr) -> attr.getKey() + "=" + attr.getValue();
        BinaryOperator<String> commaSeparate = (a, b) -> a + ", " + b;
        return attr.asList().stream().map(attr2String).reduce(commaSeparate).orElse("");
    }

    @Override
    public String toString() {
        return "ASTTagAttributes [attr=" + attr + "]";
    }



}
