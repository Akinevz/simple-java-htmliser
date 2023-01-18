package com.example.sitebro;

import java.util.Properties;

import com.example.sitebro.ast.ASTLeaf;
import com.example.sitebro.ast.ASTProperties;

public class Prop {

    public static String format(Properties prop) {
        return ASTProperties.formatProps(prop);
    }

    public static ASTLeaf cast(Properties prop) {
        return () -> format(prop);
    }

    public static String spaceDelim(String a, String b) {
        return a + " " + b;
    }

    public static Properties parse(String... props) {
        Properties result = new Properties();
        for (String string : props) {
            var parts = string.split("=", 2);
            if (parts.length == 2) {
                result.setProperty(parts[0], parts[1]);
            } else {
                result.setProperty(parts[0], "true");
            }
        }
        return result;
    }

    public static Properties of(String string) {
        return parse(string);
    }

}
