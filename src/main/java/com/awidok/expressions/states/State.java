package com.awidok.expressions.states;

import java.io.IOException;

public interface State {
    void next(StateContext context) throws IOException;
}
