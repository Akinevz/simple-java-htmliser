package com.example.sitebro;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sitebro.ast.ASTPrinter;
import com.example.sitebro.ast.ASTTag;
import com.example.sitebro.ast.Formatter;

@SpringBootTest
class TagTests {

	@Test
	void testEmpty() {
		var emptyTag = new Tag("html");
		System.out.println(emptyTag.repr());
	}

	@Test
	void testSimple() throws ParseException {
		var headTag = new Tag("head");
		var div = Tags.div("hello world", Prop.parse("style=background-color:red;"));
		var bodyTag = new Tag("body", div);
		var tag = new Tag("html", headTag, bodyTag);
		System.out.println(tag.repr());
	}

	@Test
	void testSimpler() {
		var emptyTag = new Tag("html");
		var headOnly = emptyTag.setChildren(new Tag("head"),
				new Tag("body").setChildren(Tags.div("hello world", null)));
		System.out.println(headOnly.repr());
	}

	@Test
	void testTree() {
		var html = new Tag("html").with(
				new Tag("head"),
				new Tag("body").with(
						Tags.div("Hello world", Prop.of("id=key")).with(
								Tags.div("childNested", Prop.of("id=child")))));

		var comp = new Formatter(html);
		// System.out.println(new ASTPrinter(comp));
		System.out.println(comp.content());
	}
}
