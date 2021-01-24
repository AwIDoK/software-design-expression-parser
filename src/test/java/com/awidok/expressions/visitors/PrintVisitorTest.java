package com.awidok.expressions.visitors;

import com.awidok.expressions.Tokenizer;
import com.awidok.expressions.tokens.Operation;
import org.junit.jupiter.api.Test;
import com.awidok.expressions.tokens.Token;

import java.io.*;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintVisitorTest {
    @Test
    public void testAllTokens() throws IOException, ParseException {
        InputStream input = new ByteArrayInputStream("+-*/123()".getBytes());
        List<Token> tokens = Tokenizer.tokenize(input);
        tokens.add(new Operation(Operation.OperationType.UNARY_MINUS));
        OutputStream outputStream = new ByteArrayOutputStream();
        new PrintVisitor(outputStream).print(tokens);
        assertEquals("PLUS MINUS MULTIPLY DIVIDE NUMBER(123) LEFT RIGHT UNARY_MINUS ", outputStream.toString());
    }
}
