package com.awidok.expressions.visitors;

import com.awidok.expressions.tokens.Brace;
import com.awidok.expressions.tokens.NumberToken;
import com.awidok.expressions.tokens.Operation;
import com.awidok.expressions.tokens.Token;

import java.util.List;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    Stack<Long> stack = new Stack<>();

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getNumber());
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalStateException("There must be no brackets in reverse polish notation");
    }

    @Override
    public void visit(Operation token) {
        switch (token.getType()) {
            case UNARY_MINUS:
                checkStackSize(1);
                stack.add(-stack.pop());
                return;
            case MULTIPLY:
                checkStackSize(2);
                stack.add(stack.pop() * stack.pop());
                return;
            case PLUS:
                checkStackSize(2);
                stack.add(stack.pop() + stack.pop());
                return;
            case MINUS: {
                checkStackSize(2);
                long rhs = stack.pop();
                long lhs = stack.pop();
                stack.add(lhs - rhs);
                return;
            }
            case DIVIDE: {
                checkStackSize(2);
                long rhs = stack.pop();
                long lhs = stack.pop();
                stack.add(lhs / rhs);
            }
        }
    }

    public long calc(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        checkStackSize(1);
        if (stack.size() > 1) {
            throw new IllegalStateException("empty has more than one value");
        }

        return stack.peek();
    }

    private void checkStackSize(int size) {
        if (stack.size() < size) {
            throw new IllegalStateException("empty stack while calculating expression");
        }
    }
}
