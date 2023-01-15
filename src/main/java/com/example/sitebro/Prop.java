package com.example.sitebro;

import java.text.ParseException;
import java.util.Map;
import java.util.Properties;

public class Prop {

    public static String format(Properties prop) {
        return prop.entrySet().stream()
                .map(Prop::formatEntry)
                .reduce("", Prop::spaceDelim);
    }

    public static String spaceDelim(String a, String b) {
        return a + " " + b;
    }

    public static String formatEntry(Map.Entry<Object, Object> entry) {
        return String.format("%s=%s", formatKey(entry), formatValue(entry));
    }

    public static String formatValue(Map.Entry<Object, Object> entry) {
        return "\"" + entry.getValue().toString() + "\"";
    }

    public static String formatKey(Map.Entry<Object, Object> entry) {
        return entry.getKey().toString();
    }

    public static Properties parse(String... props) throws ParseException {
        Properties result = new Properties();
        for (String string : props) {
            var parts = string.split("=", 2);
            if(parts.length == 2) {
                result.setProperty(parts[0], parts[1]);
            } else {
                result.setProperty(parts[0], "true");
            }
        }
        return result;
    }

}
