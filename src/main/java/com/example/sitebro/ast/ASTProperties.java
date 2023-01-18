package com.example.sitebro.ast;

import java.util.Map;
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
        return Optional.ofNullable(props).map(ASTProperties::formatProps).orElse("");
    }

    public static String formatProps(Properties entry) {
        return entry.entrySet().stream().map(n -> formatEntry(n)).reduce("", Prop::spaceDelim);
    }

    public static String formatEntry(Map.Entry<Object, Object> entry) {
        return String.format("%s=%s", formatKey(entry), formatValue(entry));
    }

    public static String formatValue(Map.Entry<Object, Object> entry) {
        return "\"" + entry.getValue().toString() + "\"";
    }

    public static String formatKey(Map.Entry<Object, Object> entry) {
        return entry.getKey().toString();
    }

    @Override
    public String toString() {
        return "ASTProperties [" + formatProps(props) + "]";
    }

}