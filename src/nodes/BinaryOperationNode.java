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
        return leftExpr.convertToJava(className) + operator.convertToJava(className) + rightExpr.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return leftExpr.convertToC() + operator.convertToC() + rightExpr.convertToC();
    }

    @Override
    public String convertToPython() {
        return leftExpr.convertToPython() + operator.convertToPython() + rightExpr.convertToPython();
    }

    @Override
    public void validateTree() throws SemanticException {
        if (!(leftExpr.getType().equals("Integer") || leftExpr.getType().equals("Double")))
            throw new SemanticException("You can only perform operations on numbers", leftExpr.getToken());
        if (!(rightExpr.getType().equals("Integer") || rightExpr.getType().equals("Double")))
            throw new SemanticException("You can only perform operations on numbers", rightExpr.getToken());

        boolean matchingInt = leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer");
        boolean matchingDouble = leftExpr.getType().equals("Double") && rightExpr.getType().equals("Double");

        if (!(matchingInt || matchingDouble))
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
        if (rightExpr instanceof BinaryOperationNode && ((BinaryOperationNode) rightExpr).hasParamValue()) return 0;
        if (rightExpr instanceof IDNode && ((IDNode) rightExpr).hasParamVariable()) return 0;
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

    public boolean hasParamValue() {
        boolean paramValue = false;
        if (leftExpr instanceof BinaryOperationNode) {
            paramValue = ((BinaryOperationNode) leftExpr).hasParamValue();
        } if (rightExpr instanceof BinaryOperationNode) {
            paramValue = paramValue || ((BinaryOperationNode) rightExpr).hasParamValue();
        } if (leftExpr instanceof IDNode) {
            paramValue = paramValue || ((IDNode) leftExpr).hasParamVariable();
        } if (rightExpr instanceof IDNode) {
            paramValue = paramValue || ((IDNode) rightExpr).hasParamVariable();
        }
        return paramValue;
    }
}
