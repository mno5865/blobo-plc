package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BoolNode implements ExprNode {
    private final Token bool;

    public BoolNode(Token bool) {
        this.bool = bool;
    }

    public static BoolNode parseBoolNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        boolean tokenIsBoolean = tokens.get(0).getTokenString().equals("True") || tokens.get(0).getTokenString().equals("False");
        if (token.getTokenType() != TokenType.ID_KEYWORD || !tokenIsBoolean) {
            throw new SyntaxException("Boolean value expects a KEYWORD", token.getFilename(), token.getLineNum());
        }
        return new BoolNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.bool.getTokenString();
    }

    @Override
    public String convertToJava() {
        return this.bool.getTokenString().toLowerCase();
    }

    @Override
    public String convertToC() {
        return this.bool.getTokenString().toLowerCase();
    }

    @Override
    public String convertToPython() {
        return this.bool.getTokenString();
    }

    @Override
    public void validateTree() {
    }

    public String getType() {
        return "Boolean";
    }

    @Override
    public Token getToken() {
        return bool;
    }
}
