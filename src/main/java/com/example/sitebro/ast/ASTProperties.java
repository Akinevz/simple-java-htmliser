package com.example.sitebro.ast;

import java.util.Optional;
import java.util.Properties;

import com.example.sitebro.Prop;

public class ASTProperties implements ASTLeaf {

    final private Properties props;

    public ASTProperties(Properties props) {
        this.props = props;
    }

    @Override
    public String content() {
        return Optional.ofNullable(props).map(Prop::format).orElse("");
    }

}