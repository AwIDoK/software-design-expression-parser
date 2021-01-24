package com.awidok.expressions.states;

import com.awidok.expressions.tokens.NumberToken;

import java.io.IOException;

public class NumberState implements State {
    long number = 0;

    public void next(StateContext context) throws IOException {
        int c = context.getCurrentChar();
        if (c >= 0 && Character.isDigit(c)) {
            number = number * 10 + Character.getNumericValue(c);
            context.nextChar();
        } else {
            context.createToken(new NumberToken(number));
            context.setState(new StartState());
        }
    }
}
