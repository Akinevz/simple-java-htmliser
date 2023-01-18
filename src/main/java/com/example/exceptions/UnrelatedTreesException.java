package com.example.exceptions;

import com.example.sitebro.ast.ASTTree;

public class UnrelatedTreesException extends Exception {

    ASTTree main, aux;

    public UnrelatedTreesException(String id, String tagName, UnrelatedTreesException cause) {
        super("Replacement tree does not contain id "+id+" as a tag "+tagName, cause);
        this.main = cause.main;
        this.aux = cause.aux;
    }

    public UnrelatedTreesException(ASTTree main, ASTTree aux) {
        this.main = main;
        this.aux = aux;
    }

    

}
