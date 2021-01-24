package com.awidok.expressions.visitors;

import com.awidok.expressions.tokens.Brace;
import com.awidok.expressions.tokens.NumberToken;
import com.awidok.expressions.tokens.Operation;
import com.awidok.expressions.tokens.Token;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {

    OutputStream outputStream;
    PrintStream printStream;

    public PrintVisitor(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.printStream = new PrintStream(outputStream);
    }

    public void print(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
    }

    @Override
    public void visit(NumberToken token) {
        printStream.print(token.toString() + " ");
    }

    @Override
    public void visit(Brace token) {
        printStream.print(token.toString() + " ");
    }

    @Override
    public void visit(Operation token) {
        printStream.print(token.toString() + " ");
    }
}
