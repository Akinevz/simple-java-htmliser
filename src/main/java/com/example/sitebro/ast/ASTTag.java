package com.example.sitebro.ast;

import java.util.Objects;
import java.util.stream.Stream;

import org.jsoup.nodes.Element;

import com.example.sitebro.TagNode;
import com.example.sitebro.TagNode.TextNode;

public class ASTTag implements ASTTree {

    private TagNode tag;

    public static class ASTTextContent implements ASTLeaf {

        private String textContent;

        public ASTTextContent(TextNode textContent) {
            this.textContent = textContent.getTextContent();
        }

        @Override
        public String content() {
            return this.textContent;
        }

    }

    public ASTTag(TagNode root) {
        this.tag = root;
    }

    public ASTTag(Element n) {
        this.tag = new TagNode(n.normalName(), n.attributes(), n.childNodes());
    }

    @Override
    public ASTLeaf[] children() {
        var children = Stream.ofNullable(tag.getChildren())
                .flatMap(Stream::of)
                .map(s -> (s instanceof TagNode t) ? new ASTTag(t)
                        : (s instanceof TextNode n) ? new ASTTextContent(n)
                                : null)
                .filter(Objects::nonNull);
        return Stream
                .concat(Stream.of(new ASTTagOpening(tag)), Stream.concat(children, Stream.of(new ASTTagClosing(tag))))
                .toArray(ASTLeaf[]::new);

    }

    public String getTagId() {
        return this.tag.getAttributes().get("id");
    }

}
