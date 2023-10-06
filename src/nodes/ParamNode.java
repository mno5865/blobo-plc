package nodes;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ParamNode implements JottTree {
    ExprNode expr;
    ArrayList<ParamTailNode> paramTail;

    public ParamNode(ExprNode expr, ArrayList<ParamTailNode> paramTail) {
        this.expr = expr;
        this.paramTail = paramTail;
    }

    public static ParamNode parseParamNode(ArrayList<Token> tokens) {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.R_BRACKET) {
            ExprNode expr = ExprNode.parseExprNode(tokens);

            token = tokens.get(0);
            ArrayList<ParamTailNode> paramTail = new ArrayList<>();
            while (token.getTokenType() != TokenType.R_BRACKET) {
                paramTail.add(ParamTailNode.parseParamTailNode(tokens));
                token = tokens.get(0);
            }
            return new ParamNode(expr, paramTail);
        }

        return new ParamNode(null, null);
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (expr != null) {
            out += this.expr.convertToJott();
            for (ParamTailNode param : paramTail) {
                out += param.convertToJott();
            }
        }
        return out;
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
