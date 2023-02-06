package com.example.sitebro;

import java.util.List;
import java.util.stream.Stream;

import org.jsoup.nodes.*;

import com.example.sitebro.ast.ASTTag;

public class TagNode implements NodeElement {

    private NodeElement[] children;

    private String tagName;

    private Attributes attributes;

    public TagNode(String tagName, NodeElement... children) {
        this(tagName, new Attributes(), children);
    }

    public TagNode(String tagName, Attributes attr) {
        this(tagName, attr, new TagNode[] {});
    }

    public TagNode(String tagName, Attributes attributes, List<Node> children) {
        this(tagName, attributes, children.stream()
                .map(s -> (s instanceof org.jsoup.nodes.Element e) ? new TagNode(e)
                        : (s instanceof org.jsoup.nodes.TextNode t) ? new TagNode.TextNode(t) : null)
                .toArray(NodeElement[]::new));
    }

    public TagNode(String tagName, Attributes attr, NodeElement... children) {
        this.tagName = tagName;
        this.children = children;
        this.attributes = attr;
    }

    public TagNode(Element e) {
        this(e.tagName(), e.attributes(), e.childNodes());
    }

    public TagNode with(NodeElement... children) {
        this.children = children;
        return this;
    }

    public TagNode addTextContent(String textContent) {
        this.children = Stream.concat(Stream.of(children), Stream.of(new TextNode(textContent)))
                .toArray(NodeElement[]::new);
        return this;
    }

    public NodeElement[] getChildren() {
        return children;
    }

    public String getTagName() {
        return tagName;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public ASTTag repr() {
        return new ASTTag(this);
    }

    @Override
    public String toString() {
        return repr().content();
    }

    public static class TextNode implements NodeElement {

        private String textContent;

        public TextNode(org.jsoup.nodes.TextNode t) {
            this.textContent = t.text();
        }

        public TextNode(String textContent) {
            this.textContent = textContent;
        }

        public String getTextContent() {
            return textContent;
        }

        public static NodeElement of(String string) {
            return new TextNode(string);
        }

    }

}
