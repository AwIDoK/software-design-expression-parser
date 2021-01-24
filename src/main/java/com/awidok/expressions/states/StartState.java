package com.awidok.expressions.states;

import com.awidok.expressions.tokens.*;

import java.io.IOException;

public class StartState implements State {
    public void next(StateContext context) throws IOException {
        int c;
        while(true) {
            c = context.getCurrentChar();
            if (c < 0) {
                context.setState(new EndState());
                return;
            }
            if (Character.isWhitespace(c)) {
                context.nextChar();
                continue;
            }
            break;
        }
        if (Character.isDigit(c)) {
            context.setState(new NumberState());
            return;
        }
        switch (c) {
            case '+':
                context.createToken(new Operation(Operation.OperationType.PLUS));
                break;
            case '-':
                context.createToken(new Operation(Operation.OperationType.MINUS));
                break;
            case '*':
                context.createToken(new Operation(Operation.OperationType.MULTIPLY));
                break;
            case '/':
                context.createToken(new Operation(Operation.OperationType.DIVIDE));
                break;
            case '(':
                context.createToken(new Brace(Brace.BraceType.LEFT));
                break;
            case ')':
                context.createToken(new Brace(Brace.BraceType.RIGHT));
                break;
            default:
                context.setErrorMessage("Unknown symbol: " + Character.toString(c));
                context.setState(new ErrorState());
                return;
        }
        context.nextChar();
    }
}
