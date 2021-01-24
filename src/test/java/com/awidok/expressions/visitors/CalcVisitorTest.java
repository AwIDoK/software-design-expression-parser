package com.awidok.expressions.visitors;

import com.awidok.expressions.Tokenizer;
import com.awidok.expressions.tokens.Token;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalcVisitorTest {
    @Test
    public void singleNumber() throws IOException, ParseException {
        checkCalc("5", 5L);
    }

    @Test
    public void unaryMinus() throws IOException, ParseException {
        checkCalc("-5", -5L);
        checkCalc("--5", 5L);
    }

    @Test
    public void emptyBrackets(){
        assertThrows(IllegalStateException.class, () -> checkCalc("()", null));
    }

    @Test
    public void singleOperation() throws IOException, ParseException {
        checkCalc("2+2", 4L);
    }

    @Test
    public void minusAfterLeftBracket() throws IOException, ParseException {
        checkCalc("(-2)", -2L);
    }

    @Test
    public void unaryMinusWithBinaryOperation() throws IOException, ParseException {
        checkCalc("5*-2", -10L);
        checkCalc("-5*2", -10L);
    }

    @Test
    public void operationsWithDifferentPriority() throws IOException, ParseException {
        checkCalc("2+2*2", 6L);
    }

    @Test
    public void operationsWithSamePriority() throws IOException, ParseException {
        checkCalc("2*2/2", 2L);
        checkCalc("2/2*2", 2L);
        checkCalc("2+2-2", 2L);
        checkCalc("2-2+2", 2L);
    }

    @Test
    public void incorrectExpression() {
        assertThrows(IllegalStateException.class, () -> checkCalc("(/5)", null));
        assertThrows(IllegalStateException.class, () -> checkCalc("3 3", null));
        assertThrows(IllegalStateException.class, () -> checkCalc("2(2)", null));
        assertThrows(IllegalStateException.class, () -> checkCalc("8*8//7", null));
        assertThrows(IllegalStateException.class, () -> checkCalc("4-", null));
    }

    private void checkCalc(String inputStr, Long expected) throws IOException, ParseException {
        InputStream input = new ByteArrayInputStream(inputStr.getBytes());
        List<Token> tokens = Tokenizer.tokenize(input);
        List<Token> parsed = new ParserVisitor().parse(tokens);
        Long result = new CalcVisitor().calc(parsed);
        assertEquals(result, expected);
    }
}
