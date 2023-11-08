package nodes;

import errors.SemanticException;
import provided.Token;

public class BinaryOperationNode implements ExprNode {
    private final OpNode operator;
    private final ExprNode leftExpr;
    private final ExprNode rightExpr;

    public BinaryOperationNode(OpNode operator, ExprNode left, ExprNode right) {
        this.operator = operator;
        this.leftExpr = left;
        this.rightExpr = right;
    }

    @Override
    public String convertToJott() {
        return leftExpr.convertToJott() + operator.convertToJott() + rightExpr.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public void validateTree() throws SemanticException { //TODO ERROR CASE FOR WHEN THIS FAILS
        if (!(leftExpr.getType().equals("Integer") || leftExpr.getType().equals("Double")))
            throw new SemanticException("You can only perform operations on numbers", leftExpr.getToken());
        if (!(rightExpr.getType().equals("Integer") || rightExpr.getType().equals("Double")))
            throw new SemanticException("You can only perform operations on numbers", rightExpr.getToken());

        boolean matchingInt = leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer");
        boolean matchingDouble = leftExpr.getType().equals("Double") && rightExpr.getType().equals("Double");

        if (!(matchingInt || matchingDouble)) //todo ask scott if x / 10 should fail vs x / 10.0 when x is a double
            throw new SemanticException("You can only perform operations on a pair of integers," +
                    " or a pair of doubles", operator.getToken());

        this.evaluate(); //should catch division by zero

        leftExpr.validateTree();
        rightExpr.validateTree();
    }

    @Override
    public String getType() throws SemanticException {
        if (operator.isMathOp()) {
            if (leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer")) return "Integer";
            if (leftExpr.getType().equals("Double") && rightExpr.getType().equals("Double")) return "Double";
        }
        return "Boolean";
    }

    @Override
    public Token getToken() {
        return leftExpr.getToken();
    }

    @Override
    public double evaluate() throws SemanticException {
        double value = 0;
        try {
            if (operator.getToken().getToken().equals("+")) value = leftExpr.evaluate() + rightExpr.evaluate();
            else if (operator.getToken().getToken().equals("-")) value = leftExpr.evaluate() - rightExpr.evaluate();
            else if (operator.getToken().getToken().equals("*")) value = leftExpr.evaluate() * rightExpr.evaluate();
            else if (operator.getToken().getToken().equals("/")) value = leftExpr.evaluate() / rightExpr.evaluate();
        } catch (ArithmeticException e) {
            throw new SemanticException("Division by zero is attempted", rightExpr.getToken());
        }
        if (Double.isInfinite(value)) throw new SemanticException("Division by zero is attempted", rightExpr.getToken());
        return this.getType().equals("Integer") ? (int)value : value;
    }
}
