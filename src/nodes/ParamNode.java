package nodes;

import errors.SemanticException;
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

    public void handleParamIsDefinedError(ExprNode expression) throws SemanticException {
        if (expression instanceof IDNode) {
            IDNode paramID = (IDNode) expression;
            if (!SymbolTable.doesVarExistInScope(paramID.getName())) {
                throw new SemanticException("The variable " +
                        paramID.getName() + " does not exist in the current scope", paramID.getToken());
            }
        }
    }

    public List<String> getParamTypes() throws SemanticException {
        List<String> params = new ArrayList<>();
        handleParamIsDefinedError(expr);
        if (expr == null) return params;
        params.add(expr.getType());
        for (ParamTailNode param : paramTail) {
            handleParamIsDefinedError(param.getExpr());
            params.add(param.getExpr().getType());
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
    public void validateTree() throws SemanticException {
        if (expr == null) return;
        expr.validateTree();
        for (ParamTailNode param : paramTail) {
            param.validateTree();
        }
    }
}
