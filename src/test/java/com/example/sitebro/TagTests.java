package com.example.sitebro;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sitebro.ast.ASTTag;

@SpringBootTest
class TagTests {

	@Test
	void testEmpty() {
		var emptyTag = new TagNode("html");
		System.out.println(emptyTag.repr());
	}

	@Test
	void testSimple() throws ParseException {
		var headTag = new TagNode("head");
		var div = Tags.div("hello world", Tags.attr("style=background-color:red;"));
		var bodyTag = new TagNode("body", div);
		var tag = new TagNode("html", headTag, bodyTag);
		System.out.println(tag.repr());
	}

	@Test
	void testSimpler() {
		var emptyTag = new TagNode("html");
		var headOnly = emptyTag.with(
				new TagNode("head"),
				new TagNode("body").with(
						Tags.div("hello world", null)));
		System.out.println(headOnly.repr());
	}

	@Test
	void testTree() {
		var head = new TagNode("head");
		var body = new TagNode("body").with(
				Tags.div("Hello world", Tags.attr("id=key")).with(
						Tags.div("childNested", Tags.attr("id=child"))));

		var html = new TagNode("html").with(head, body);

		var comp = new com.example.sitebro.ast.ASTFormatter(new ASTTag(html));
		// System.out.println(new ASTPrinter(comp));
		System.out.println(comp.content());
	}
}
