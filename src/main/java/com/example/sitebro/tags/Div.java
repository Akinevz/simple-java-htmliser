package com.example.sitebro.tags;

import java.util.Properties;

import com.example.sitebro.Tag;

public class Div extends Tag {
    public Div(String textContent){
        this(textContent, null);
    }
    public Div(String textContent, Properties props) {
        super("div", props);
        super.setTextContent(textContent);
    }
}
