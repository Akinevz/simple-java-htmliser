package com.example.sitebro;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.sitebro.ast.ASTTree;

import lombok.NonNull;

public class ResourceFile {

    private Document content;

    public ResourceFile(File f) throws IOException {
        this.content = Jsoup.parse(f);
    }
    
    @NonNull
    public ASTTree read() {
        return new HTMLParser(content);
    }
}
