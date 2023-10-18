package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamTailNode implements JottTree {

    private final ExprNode expr;

    public ParamTailNode(ExprNode expr) {
        this.expr = expr;
    }

    public static ParamTailNode parseParamTailNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.COMMA) {
            throw new SyntaxException("Missing comma between parameters",
                    token.getFilename(), token.getLineNum());
        }
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
    public boolean validateTree() {
        return false;
    }
}
