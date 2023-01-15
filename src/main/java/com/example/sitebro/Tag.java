package com.example.sitebro;

import java.util.ArrayList;
import java.util.Properties;

import com.example.sitebro.ast.ASTFormatter;
import com.example.sitebro.ast.ASTLeaf;

public class Tag {

    public static ASTLeaf prependSpace(String arg0) {
        return () -> " " + arg0;
    }

    private Tag[] children;

    private String tagName;

    private String textContent;

    private Properties properties;

    public Tag(String tagName, Tag... children) {
        this.tagName = tagName;
        this.children = children;
    }

    public Tag(String tagName, Properties props, Tag... children) {
        this.tagName = tagName;
        this.children = children;
        this.properties = props;
    }

    @Override
    public String toString() {
        return this.repr();
    }

    public Tag with(Tag... children) {
        this.children = children;
        return this;
    }

    public Tag withTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String repr() {
        return repr(Integer.MAX_VALUE);
    }

    public Tag setChildren(Tag... tag) {
        this.children = tag;
        return this;
    }

    public Tag[] getChildren() {
        return children;
    }

    public String getTagName() {
        return tagName;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getTextContent() {
        return textContent;
    }

    public String repr(int depth) {
        return new ASTFormatter(0, depth, this).tree().content();
    }

    // public static class TagFormatter {

    // private String padding;
    // private ArrayList<String> lines;
    // private String result;

    // public TagFormatter(int depth, String opening, Tag[] children, String
    // closing) {
    // this.padding = Stream.generate(() -> "\t").limit(depth).reduce("", (a, b) ->
    // a + b);
    // this.lines = new ArrayList<String>();
    // this.lines.add(padding + opening);
    // for (Tag childTag : children) {
    // this.lines.add(childTag.repr(depth + 1));
    // }
    // this.lines.add(padding + closing);
    // this.result = lines.stream().filter(s -> !s.isBlank()).reduce("", (a, b) -> a
    // + "\n" + b);
    // }

    // @Override
    // public String toString() {
    // return this.result;
    // }

    // }
}
