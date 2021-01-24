package com.awidok.expressions.tokens;

import com.awidok.expressions.visitors.TokenVisitor;

public class Brace implements Token {

    public enum BraceType {
        LEFT,
        RIGHT
    }

    public BraceType getType() {
        return type;
    }

    BraceType type;

    public Brace(BraceType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
