package com.awidok.expressions;

import com.awidok.expressions.Tokenizer;
import org.junit.jupiter.api.Test;
import com.awidok.expressions.tokens.Brace;
import com.awidok.expressions.tokens.NumberToken;
import com.awidok.expressions.tokens.Operation;
import com.awidok.expressions.tokens.Token;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {

    private void compareTokenLists(List<Token> a, List<Token> b) {
        assertEquals(a.size(), b.size());
        for (int i = 0; i < a.size(); i++) {
            assertEquals(a.get(i).toString(), b.get(i).toString());
        }
    }

    @Test
    public void testEmptyInput() throws IOException, ParseException {
        InputStream input = new ByteArrayInputStream("".getBytes());
        List<Token> tokens = Tokenizer.tokenize(input);
        assertTrue(tokens.isEmpty());
    }

    @Test
    public void testNumber() throws IOException, ParseException {
        InputStream input = new ByteArrayInputStream("123".getBytes());
        List<Token> tokens = Tokenizer.tokenize(input);
        List<Token> expected = new ArrayList<>();
        expected.add(new NumberToken(123));
        compareTokenLists(tokens, expected);
    }

    @Test
    public void testAllTokens() throws IOException, ParseException {
        InputStream input = new ByteArrayInputStream("+-*/123()".getBytes());
        List<Token> tokens = Tokenizer.tokenize(input);
        List<Token> expected = new ArrayList<>();
        expected.add(new Operation(Operation.OperationType.PLUS));
        expected.add(new Operation(Operation.OperationType.MINUS));
        expected.add(new Operation(Operation.OperationType.MULTIPLY));
        expected.add(new Operation(Operation.OperationType.DIVIDE));
        expected.add(new NumberToken(123));
        expected.add(new Brace(Brace.BraceType.LEFT));
        expected.add(new Brace(Brace.BraceType.RIGHT));
        compareTokenLists(tokens, expected);
    }

    @Test
    public void testUnknownSymbol() {
        InputStream input = new ByteArrayInputStream("a".getBytes());
        assertThrows(ParseException.class, () -> Tokenizer.tokenize(input));
    }
}
