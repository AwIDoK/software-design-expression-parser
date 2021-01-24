package com.awidok.expressions;

import com.awidok.expressions.tokens.Token;
import com.awidok.expressions.visitors.CalcVisitor;
import com.awidok.expressions.visitors.ParserVisitor;
import com.awidok.expressions.visitors.PrintVisitor;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Token> tokens;
        try {
            tokens = Tokenizer.tokenize(System.in);
        } catch (IOException | ParseException e) {
            System.err.println(e.getMessage());
            return;
        }

        PrintVisitor printVisitor = new PrintVisitor(System.out);

        List<Token> polish = new ParserVisitor().parse(tokens);
        System.out.println("Reverse polish notation:");
        printVisitor.print(polish);
        System.out.println("\nResult: " + new CalcVisitor().calc(polish));
    }
}
