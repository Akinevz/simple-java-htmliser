package com.example.sitebro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sitebro.ast.ASTPrinter;

@SpringBootTest
public class ASTFormattingTests {

    @Test
    void testNested() {
        var html = new TagNode("html").with(
                new TagNode("head"),
                new TagNode("body").with(
                        new TagNode("div", TagNode.TextNode.of("hello world")).with(
                                new TagNode("div", Tags.attr("id=child"), TagNode.TextNode.of("childNested")))));
        System.out.println(html);
        System.out.println("----");
        System.out.println(new ASTPrinter(html));
    }

}
