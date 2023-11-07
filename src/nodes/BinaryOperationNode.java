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
        if (!leftExpr.getType().equals("Integer") && !leftExpr.getType().equals("Double"))
            throw new SemanticException("You can only perform operations on numbers", leftExpr.getToken());

        boolean matchingInt = leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer");
        boolean matchingDouble = leftExpr.getType().equals("Double") && rightExpr.getType().equals("Double");
        leftExpr.validateTree();
        rightExpr.validateTree();
    }


    public double evaluate() { //todo this
        return 0;
    }

    @Override
    public String getType() {
        if (operator.isMathOp()) {
            if (leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer")) return "Integer";
            if (leftExpr.getType().equals("Double") && rightExpr.getType().equals("Double")) return "Double";
        } else {
            return "Boolean";
        }
    }

    @Override
    public Token getToken() {
        return leftExpr.getToken();
    }


}
