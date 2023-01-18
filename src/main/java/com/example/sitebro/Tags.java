package com.example.sitebro;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class Tags {

    public static Tag div(String textContent, Properties prop) {
        return new Tag("div", prop).withTextContent(textContent);
    }

    public static Tag span(String textContent, Properties prop) {
        return new Tag("span", prop).withTextContent(textContent);
    }

    public static Properties properties(String... args) {
        return Prop.parse(args);

    }

    // public static class TagFormatter {

    // private String padding;
    // private ArrayList<String> lines;
    // private String result;

    // public TagFormatter(int depth, Tag tag) {
    // this.padding = Stream.generate(() -> "\t").limit(depth).reduce("", (a, b) ->
    // a + b);
    // this.lines = new ArrayList<String>();
    // String opening = "<" + tag.getTagName()
    // + Optional.ofNullable(tag.getProperties()).map(Prop::format).orElse("") +
    // ">";
    // this.lines.add(padding + opening);
    // Tag[] children = tag.getChildren();
    // for (Tag childTag : children) {
    // this.lines.add(childTag.repr(depth + 1));
    // }
    // String closing = "</" + tag.getTagName() + ">";
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
