package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

public class BinaryOperationNode implements ExprNode {
    private final ExprNode operator;
    private final ExprNode leftExpr;
    private final ExprNode rightExpr;

    public BinaryOperationNode(ExprNode operator, ExprNode left, ExprNode right) {
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
    public boolean validateTree() {
        return false;
    }
}
