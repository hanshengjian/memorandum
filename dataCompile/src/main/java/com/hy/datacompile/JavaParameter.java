package com.hy.datacompile;

import com.squareup.javapoet.ParameterSpec;

public class JavaParameter {
    public ParameterSpec parameterSpec;
    public String name;

    public JavaParameter(ParameterSpec parameterSpec, String name) {
        this.parameterSpec = parameterSpec;
        this.name = name;
    }
}
