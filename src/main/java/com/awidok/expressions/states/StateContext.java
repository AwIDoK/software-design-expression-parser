package com.awidok.expressions.states;

import com.awidok.expressions.tokens.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StateContext {
    private final InputStream inputStream;
    private final List<Token> tokens = new ArrayList<>();
    private State state;
    private int position = 0;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    private int currentChar;

    public StateContext(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        state = new StartState();
        nextChar();
    }

    public void createToken(Token token) {
        tokens.add(token);
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public State getState() {
        return state;
    }

    public void nextChar() throws IOException {
        currentChar = inputStream.read();
    }

    public int getCurrentChar() {
        return currentChar;
    }

    public int getPosition() {
        return position;
    }

    public void next() throws IOException {
        state.next(this);
        position += 1;
    }
}
