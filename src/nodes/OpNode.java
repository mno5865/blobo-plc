package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class OpNode implements ExprNode {
    private Token operator;

    public OpNode(Token operator) {
        this.operator = operator;
    }

    public static OpNode parseOpNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (!(token.getTokenType() == TokenType.REL_OP || token.getTokenType() == TokenType.MATH_OP)) {
            throw new SyntaxException("parseOP expects REL_OP or MATH_OP", token.getFilename(), token.getLineNum());
        }
        return new OpNode(tokens.remove(0));
    }

    public boolean isMathOp() {
        return operator.getTokenType() == TokenType.MATH_OP;
    }

    @Override
    public String convertToJott() {
        return this.operator.getToken();
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
    public void validateTree() {

    }

    @Override
    public Token getToken() {
        return operator;
    }

    public String getType() {
        // should never be called, an operator cannot be a type
        return "";
    }
}
