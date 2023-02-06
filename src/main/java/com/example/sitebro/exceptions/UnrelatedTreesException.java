package com.example.sitebro.exceptions;

import com.example.sitebro.ast.ASTTree;

public class UnrelatedTreesException extends Exception {

    ASTTree main, aux;
    public UnrelatedTreesException(ASTTree main, ASTTree aux) {
        super("Second tree does not contain id in common with the first tree");
        this.main = main;
        this.aux = aux;
    }

    

}
