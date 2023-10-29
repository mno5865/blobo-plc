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
        boolean tokenIsBoolean = tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False");
        if (token.getTokenType() != TokenType.ID_KEYWORD || !tokenIsBoolean) {
            throw new SyntaxException("Boolean value expects a KEYWORD", token.getFilename(), token.getLineNum());
        }
        return new BoolNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.bool.getToken();
    }

    @Override
    public String convertToJava(String className) {
        String out = "";
        return out;
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
        //todo someone should check to make sure this is correct (i'm pretty sure base nodes can't have any errors)
        return true;
    }

    public String getType() {
        return "Boolean";
    }
}
