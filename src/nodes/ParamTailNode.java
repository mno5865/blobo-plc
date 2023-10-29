package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static nodes.BasicParsers.parseToken;

public class ParamTailNode implements JottTree {

    private final ExprNode expr;

    public ParamTailNode(ExprNode expr) {
        this.expr = expr;
    }

    public static ParamTailNode parseParamTailNode(ArrayList<Token> tokens) throws SyntaxException {
        parseToken(TokenType.COMMA, tokens);
        ExprNode exprNode = ExprNode.parseExprNode(tokens);
        return new ParamTailNode(exprNode);
    }

    @Override
    public String convertToJott() {
        return ", " + expr.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    @Override
    public boolean validateTree() { // TODO VALIDATE TREE FOR A PARAM TAIL NODE
        return expr.validateTree();
    }

    public ExprNode getExpr() {
        return expr;
    }
}
