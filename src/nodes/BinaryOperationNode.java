package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

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
    public boolean validateTree() {
        if (!leftExpr.validateTree()) {
            return false;
        } if (!rightExpr.validateTree()) {
            return false;
        } if (leftExpr.isInteger() != rightExpr.isInteger()) {
            return false;
        } /**if (!possibleOps.contains(op)) {
         return false;
         }*/

        return true;
    }

    @Override
    public boolean isInteger() {
        if (this.operator.getOperator().equals("/")) { // todo ask about the case of an integer / integer like 4 / 2
            return false;
        }
        return leftExpr.isInteger() && rightExpr.isInteger();
    }

    public double evaluate() { //todo understand this function, i might have to implement symbol table first?
        return 0;
    }
}
