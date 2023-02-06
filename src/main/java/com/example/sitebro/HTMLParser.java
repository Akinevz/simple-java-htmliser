package com.example.sitebro;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.sitebro.ast.*;

public class HTMLParser implements ASTTree {

    private Document node;

    public HTMLParser(Document document) {
        this.node = document;
    }

    private ASTLeaf node2Leaf(Element n) {
        return new ASTTag(n);
    }

    @Override
    public ASTLeaf[] children() {
        return this.node.children().stream().map(this::node2Leaf).toArray(ASTLeaf[]::new);
    }

}
