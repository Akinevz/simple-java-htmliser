package com.example.sitebro;

import java.util.Properties;

import com.example.sitebro.ast.ASTTag;
import com.example.sitebro.ast.Formatter;

public class Tag {

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

    public Tag with(Tag... children) {
        this.children = children;
        return this;
    }

    public Tag withTextContent(String textContent) {
        this.textContent = textContent;
        return this;
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

    public ASTTag repr() {
        return new ASTTag(this);
    }

    @Override
    public String toString() {
        return new Formatter(repr()).content();
    }

}
