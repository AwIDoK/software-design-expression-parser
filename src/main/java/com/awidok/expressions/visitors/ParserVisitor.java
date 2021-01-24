package com.awidok.expressions.visitors;

import com.awidok.expressions.tokens.Brace;
import com.awidok.expressions.tokens.NumberToken;
import com.awidok.expressions.tokens.Operation;
import com.awidok.expressions.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    Stack<Token> stack = new Stack<>();
    List<Token> reversePolishNotation = new ArrayList<>();
    Token lastVisited = null;

    @Override
    public void visit(NumberToken token) {
        lastVisited = token;
        reversePolishNotation.add(token);
    }

    @Override
    public void visit(Brace token) {
        lastVisited = token;
        if (token.getType() == Brace.BraceType.LEFT) {
            stack.add(token);
        } else {
            while (!stack.empty() && (!(stack.peek() instanceof Brace) || ((Brace) stack.peek()).getType() != Brace.BraceType.LEFT)) {
                moveToOutput();
            }
            if (stack.empty()) {
                throw new IllegalStateException("Brackets don't match");
            }
            stack.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        if (token.getType() == Operation.OperationType.MINUS) {
            if (lastVisited == null || lastVisited instanceof Operation ||
                    (lastVisited instanceof Brace && ((Brace) lastVisited).getType() == Brace.BraceType.LEFT)) {
                stack.add(new Operation(Operation.OperationType.UNARY_MINUS));
                return;
            }
        }

        while (!stack.empty() && stack.peek() instanceof Operation) {
            Operation operation = (Operation) stack.peek();
            if (operation.getType() == Operation.OperationType.UNARY_MINUS) {
                moveToOutput();
            } else if (getPriority(operation) >= getPriority(token)) {
                moveToOutput();
            } else {
                break;
            }
        }
        stack.add(token);
        lastVisited = token;
    }

    private void moveToOutput() {
        reversePolishNotation.add(stack.pop());
    }

    private int getPriority(Operation operation) {
        switch (operation.getType()) {
            case PLUS:
            case MINUS:
                return 0;
            case DIVIDE:
            case MULTIPLY:
                return 1;
        }
        throw new IllegalStateException("Impossible to get here");
    }

    public List<Token> parse(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        while (!stack.empty()) {
            if (!(stack.peek() instanceof Operation)) {
                throw new IllegalStateException("Brackets don't match");
            }
            moveToOutput();
        }
        return reversePolishNotation;
    }
}
