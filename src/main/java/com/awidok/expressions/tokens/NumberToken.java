package com.awidok.expressions.tokens;

import com.awidok.expressions.visitors.TokenVisitor;

public class NumberToken implements Token{
    public long getNumber() {
        return number;
    }

    long number;
    public NumberToken(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "NUMBER(" + number + ")";
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
