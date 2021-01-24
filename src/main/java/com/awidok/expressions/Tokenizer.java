package com.awidok.expressions;

import com.awidok.expressions.states.EndState;
import com.awidok.expressions.states.ErrorState;
import com.awidok.expressions.states.StateContext;
import com.awidok.expressions.tokens.Token;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

public class Tokenizer {
    public static List<Token> tokenize(InputStream inputStream) throws IOException, ParseException {
        StateContext context = new StateContext(inputStream);
        while (!(context.getState() instanceof EndState) && !(context.getState() instanceof ErrorState)) {
            context.next();
        }
        if (context.getState() instanceof ErrorState) {
            throw new ParseException(context.getErrorMessage(), context.getPosition());
        }
        return context.getTokens();
    }
}
