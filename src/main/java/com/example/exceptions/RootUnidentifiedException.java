package com.example.exceptions;

import com.example.sitebro.ast.ASTLeaf;

public class RootUnidentifiedException extends RuntimeException {

    public RootUnidentifiedException() {
        super("Last parsed tree has no valid root-level id");
    }

    public RootUnidentifiedException(Class<? extends ASTLeaf> class1) {
        super("Last parsed tree starts with unexpected opening tag "+class1.getSimpleName());
    }

}
