package com.example.sitebro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sitebro.ast.*;

@SpringBootTest
class ASTCombinatoricsTests {
    @Test
    public void combine() {
        ASTLeaf a = () -> "a";
        ASTLeaf b = () -> "b";
        ASTTree both = ASTTree.of(a, b);

        assertEquals("ab", both.content());
    }

    @Test
    public void combineNullLeft() {
        ASTLeaf a = () -> null;
        ASTLeaf b = () -> "b";
        ASTTree both = ASTTree.of(a, b);

        assertEquals("b", both.content());
    }

    @Test
    public void combineNullRight() {
        ASTLeaf a = () -> "a";
        ASTLeaf b = () -> null;
        ASTTree both = ASTTree.of(a, b);

        assertEquals("a", both.content());
    }

    @Test
    public void combineBothNull() {
        ASTLeaf a = () -> null;
        ASTLeaf b = () -> null;
        ASTTree both = ASTTree.of(a, b);

        assertEquals("", both.content());
    }

    @Test
    public void combineTrees() {
        var left = ASTTree.of(() -> "a", () -> "b");
        var right = ASTTree.of(() -> "c", () -> "d");

        ASTTree both = left.concat(right);
        assertEquals("abcd", both.content());
    }

    @Test
    public void combineTreesAppend() {
        var left = ASTTree.of(() -> "a", () -> "b");
        var right = ASTTree.of(() -> "c", () -> "d");

        ASTTree both = left.append(right);
        assertEquals("abcd", both.content());
    }

    @Test
    public void prependEach() {
        ASTTree left = ASTTree.of(() -> "b", () -> "c");
        ASTTree right = ASTTree.of(() -> "abac");
        System.out.println(new ASTPrinter(left));
        ASTTree prepended = () -> left.stream()
                .flatMap(t -> ASTTree.of(() -> "a").append(t).stream())
                .toArray(ASTLeaf[]::new);
        System.out.println(new ASTPrinter(prepended));
        assertEquals(right.content(), prepended.content());
    }
}
