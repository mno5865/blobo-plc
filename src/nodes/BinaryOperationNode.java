package nodes;

import errors.SemanticException;

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
        leftExpr.validateTree();
        rightExpr.validateTree();
        //valid = valid && (leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer"));
        //valid = valid && (leftExpr.getType().equals("Double") && rightExpr.getType().equals("Double"));
        /**if (!possibleOps.contains(op)) {
         return false;
         }*/
    }


    public double evaluate() { //todo understand this function, i might have to implement symbol table first?
        return 0;
    }

    @Override
    public String getType() { // function todo
        /**
        if (this.operator.getOperator().equals("/")) { // todo ask about the case of an integer / integer like 4 / 2
            return false;
        }*/
        if (leftExpr.getType().equals("Integer") && rightExpr.getType().equals("Integer")) return "Integer";
        return "";
    }
}
