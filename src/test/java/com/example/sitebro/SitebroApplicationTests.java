package com.example.sitebro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sitebro.tags.Div;

@SpringBootTest
class SitebroApplicationTests {
	@Test
	void testSimple() throws ParseException {
		var headTag = new Tag("head");
		var div = new Div("hello world", Prop.parse("style=background-color:red;"));
		var bodyTag = new Tag("body", div);
		var tag = new Tag("html", headTag, bodyTag);
		System.out.println(tag.repr());
	}

	@Test
	void testEmpty() {
		var emptyTag = new Tag("html");
		System.out.println(emptyTag.repr());
	}

	@Test
	void testSimpler() {
		var emptyTag = new Tag("html");
		var headOnly = emptyTag.setChildren(new Tag("head"), new Tag("body").setChildren(new Div("hello world", null)));
		System.out.println(headOnly.repr());
	}

	@Test
	void testNested() {
		var html = new Tag("html").with(
				new Tag("head"),
				new Tag("body").with(new Div("Hello world").with(new Div("childNested")))
		);
		System.out.println(html.repr());
	}
}
