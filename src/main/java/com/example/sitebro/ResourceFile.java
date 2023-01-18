package com.example.sitebro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.example.sitebro.ast.ASTTree;

import lombok.NonNull;

public class ResourceFile {

    private List<String> content;

    public ResourceFile(File f) throws IOException {
        this.content = Files.readAllLines(f.toPath());
    }
    
    @NonNull
    public ASTTree read() {
        
        return null;
    }
}
