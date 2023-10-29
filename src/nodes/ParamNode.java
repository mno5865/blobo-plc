package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ParamNode implements JottTree {
    private final ExprNode expr;
    private final ArrayList<ParamTailNode> paramTail;

    public ParamNode(ExprNode expr, ArrayList<ParamTailNode> paramTail) {
        this.expr = expr;
        this.paramTail = paramTail;
    }

    public static ParamNode parseParamNode(ArrayList<Token> tokens) throws SyntaxException {
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

    public List<String> getParams() {
        List<String> params = new ArrayList<>();
        params.add(this.expr.convertToJott()); //todo maybe refactor this to not use convert to jott later... it works tho 🤭
        for (ParamTailNode param : paramTail) {
            params.add(param.convertToJott());
        }
        return params;
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
        String out = "";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        return out;
    }

    @Override
    public boolean validateTree() { //TODO VALIDATE TREE FOR PARAM NODE NODE
        if (expr == null) return true;
        boolean valid = true;
        expr.validateTree();
        for (ParamTailNode param : paramTail) {
            param.validateTree();
        }
        return valid;
    }
}
