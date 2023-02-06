package com.example.sitebro;

import java.util.stream.Stream;

import org.jsoup.nodes.Attributes;

public class Tags {

    public static TagNode div(String string, Attributes attributes) {
        return new TagNode("div", attributes, new TagNode.TextNode(string));
    }

    public static Attributes attr(String... strings) {
        return Stream.of(strings)
                .map(s -> s.split("=", 2))
                .<Attributes>collect(
                        Attributes::new,
                        (arg0, arg1) -> arg0.add(arg1[0], arg1[1]),
                        Attributes::addAll);
    }

}
