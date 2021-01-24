package com.awidok.expressions.tokens;

import com.awidok.expressions.visitors.TokenVisitor;

public class Operation implements Token {
    public enum OperationType {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        UNARY_MINUS
    }

    OperationType type;

    public OperationType getType() {
        return type;
    }

    public Operation(OperationType type) {
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
