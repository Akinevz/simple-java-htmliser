package com.example.sitebro.ast;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.lang.*;

import com.example.exceptions.RootUnidentifiedException;
import com.example.exceptions.UnrelatedTreesException;

@FunctionalInterface
public interface ASTTree extends ASTLeaf {
    ASTLeaf[] children();

    static ASTTree of(ASTLeaf... s) {
        return () -> s;
    }

    default Stream<ASTLeaf> stream() {
        return Stream.ofNullable(children()).flatMap(Stream::of);
    }

    @Override
    default String content() {
        return stream()
                .map(ASTLeaf::content)
                .filter(x -> x != null)
                .reduce(String::concat)
                .orElseGet(() -> "");
    }

    default ASTTree concat(ASTTree other) {
        if (children() == null)
            return other;
        if (other.children() == null)
            return this;
        return () -> Stream.concat(Stream.of(children()), Stream.of(other.children())).toArray(ASTLeaf[]::new);
    }

    default ASTTree append(ASTLeaf next) {
        if (children() == null)
            return () -> new ASTLeaf[] { next };
        return () -> Stream.concat(Stream.of(children()), Stream.of(next)).toArray(ASTLeaf[]::new);
    }

    default ASTTree replace(ASTTree other) {
        return other;
    }

    default boolean contains(Class<?> clazz) {
        return stream().anyMatch(s -> clazz.isAssignableFrom(s.getClass()));
    }

    static ASTTree rewrite(@NonNull ASTTree main, @NonNull ASTTree aux)
            throws UnrelatedTreesException, RootUnidentifiedException {
        if (main.children() == null) {
            return aux;
        }
        var first = aux.first();
        String id, tagName;
        if (first instanceof ASTTagOpening ato) {
            id = Optional.ofNullable(ato.getTagId()).orElseThrow(RootUnidentifiedException::new);
            tagName = Optional.of(ato.getTagName()).orElseThrow(RootUnidentifiedException::new);
        } else {
            throw new RootUnidentifiedException(first.getClass());
        }
        
        var all = new ASTFlattener(main);
        var has = all.stream().anyMatch(s -> (s instanceof ASTTagOpening op) && op.getTagId() == id);
        if (has) {
            throw new UnrelatedTreesException(id, tagName, new UnrelatedTreesException(main, aux));
        }
        class Rewrite {
            static <T extends ASTLeaf> Predicate<ASTTree> find(Class<T> clazz, Predicate<? super T> pred) {
                return tree -> {
                    return tree.stream().filter(clazz::isInstance).map(clazz::cast).anyMatch(pred);
                };
            }

            static ASTTree rewritten(ASTTree source, Supplier<ASTTree> transformation, Predicate<ASTTree> pred) {
                return () -> source.stream().flatMap(tree -> (tree instanceof ASTTree t)
                        ? (pred.test(t))
                                ? transformation.get().stream()
                                : rewritten(t, transformation, pred).stream()
                        : Stream.of(tree)).toArray(ASTLeaf[]::new);
            }
        }
        return Rewrite.rewritten(main, () -> aux, Rewrite.find(ASTTagOpening.class,
                t -> t.getTagId().equals(id) && t.getTagName().equalsIgnoreCase(tagName)));
    }

    default ASTLeaf first() {
        return Optional.ofNullable(children()).map(s -> s[0]).orElse(null);
    }

}