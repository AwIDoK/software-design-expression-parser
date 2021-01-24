package com.awidok.expressions.tokens;

import com.awidok.expressions.visitors.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
