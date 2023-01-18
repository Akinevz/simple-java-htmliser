package com.example.sitebro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.exceptions.RootUnidentifiedException;
import com.example.exceptions.UnrelatedTreesException;
import com.example.sitebro.ast.ASTTag;
import com.example.sitebro.ast.ASTTree;

@SpringBootTest
public class ASTMergingTests {

    @Test
    public void testReplaceEmpty() throws RootUnidentifiedException, UnrelatedTreesException {
        ASTTree ast = () -> null;
        Tag tag = new Tag("div", Prop.of("id=key"), Tags.span("hello", null), Tags.span("world", null));
        var out = ASTTree.rewrite(ast, new ASTTag(tag));

        assertEquals(tag.repr().content(), out.content());
    }

    @Test
    public void testReplaceUnrelated() {
        var left = Tags.div("template", Tags.properties("id=root"));
        var right = Tags.div("test", Tags.properties("id=unrelated"));
        assertThrows(UnrelatedTreesException.class, () -> ASTTree.rewrite(new ASTTag(left), new ASTTag(right)));
    }

    @Test
    public void testReplaceRelated() throws RootUnidentifiedException, UnrelatedTreesException {
        var left = Tags.div("gone", Tags.properties("id=null"));
        var right = Tags.div("fill", Tags.properties("id=null"));
        var container = new Tag("html");
        var combo = ASTTree.rewrite(new ASTTag(container.with(left)), new ASTTag(right));
        System.out.println(combo.content());

        assertEquals(new ASTTag(container.with(right)).content(), combo.content());
    }
}
