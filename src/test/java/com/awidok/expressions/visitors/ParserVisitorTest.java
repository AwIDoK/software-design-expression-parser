package com.awidok.expressions.visitors;

import com.awidok.expressions.Tokenizer;
import com.awidok.expressions.tokens.Token;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserVisitorTest {
    @Test
    public void singleNumber() throws IOException, ParseException {
        checkParsing("5", "NUMBER(5) ");
    }

    @Test
    public void unaryMinus() throws IOException, ParseException {
        checkParsing("-5", "NUMBER(5) UNARY_MINUS ");
        checkParsing("--5", "NUMBER(5) UNARY_MINUS UNARY_MINUS ");
    }

    @Test
    public void emptyBrackets() throws IOException, ParseException {
        checkParsing("()", "");
    }

    @Test
    public void singleOperation() throws IOException, ParseException {
        checkParsing("2+2", "NUMBER(2) NUMBER(2) PLUS ");
    }

    @Test
    public void minusAfterLeftBracket() throws IOException, ParseException {
        checkParsing("(-2)", "NUMBER(2) UNARY_MINUS ");
    }

    @Test
    public void unaryMinusWithBinaryOperation() throws IOException, ParseException {
        checkParsing("5*-2", "NUMBER(5) NUMBER(2) UNARY_MINUS MULTIPLY ");
        checkParsing("-5*2", "NUMBER(5) UNARY_MINUS NUMBER(2) MULTIPLY ");
    }

    @Test
    public void operationsWithDifferentPriority() throws IOException, ParseException {
        checkParsing("2+2*2", "NUMBER(2) NUMBER(2) NUMBER(2) MULTIPLY PLUS ");
    }

    @Test
    public void operationsWithSamePriority() throws IOException, ParseException {
        checkParsing("2*2/2", "NUMBER(2) NUMBER(2) MULTIPLY NUMBER(2) DIVIDE ");
        checkParsing("2/2*2", "NUMBER(2) NUMBER(2) DIVIDE NUMBER(2) MULTIPLY ");
        checkParsing("2+2-2", "NUMBER(2) NUMBER(2) PLUS NUMBER(2) MINUS ");
        checkParsing("2-2+2", "NUMBER(2) NUMBER(2) MINUS NUMBER(2) PLUS ");
    }

    @Test
    public void incorrectBrackets() {
        assertThrows(IllegalStateException.class, () -> checkParsing("(", null));
        assertThrows(IllegalStateException.class, () -> checkParsing("(5+5", null));
        assertThrows(IllegalStateException.class, () -> checkParsing("(5+5))", null));
    }


    private void checkParsing(String inputStr, String expected) throws IOException, ParseException {
        InputStream input = new ByteArrayInputStream(inputStr.getBytes());
        List<Token> tokens = Tokenizer.tokenize(input);
        List<Token> parsed = new ParserVisitor().parse(tokens);
        OutputStream outputStream = new ByteArrayOutputStream();
        new PrintVisitor(outputStream).print(parsed);
        assertEquals(expected, outputStream.toString());
    }
}
