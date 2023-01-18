package com.example.sitebro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sitebro.ast.*;

@SpringBootTest
public class ASTFormatting {

    @Test
    void testNested() {
        var html = new Tag("html").with(
                new Tag("head"),
                new Tag("body").with(
                        Tags.div("Hello world", Prop.of("id=key")).with(
                                Tags.div("childNested", Prop.of("id=child")))));
        System.out.println(html.repr());
        System.out.println("----");
        System.out.println(new ASTPrinter(html));
    }

}
