package com.awidok.expressions.visitors;

import com.awidok.expressions.tokens.Brace;
import com.awidok.expressions.tokens.NumberToken;
import com.awidok.expressions.tokens.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(Brace token);

    void visit(Operation token);
}
