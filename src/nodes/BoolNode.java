package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class BoolNode implements ExprNode {
    private Token bool;

    public BoolNode(Token bool) {
        this.bool = bool;
    }

    public static BoolNode parseBoolNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        boolean tokenIsBoolean = tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False");
        if (token.getTokenType() != TokenType.ID_KEYWORD || !tokenIsBoolean) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo syntax exception
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
        String out = "";
        return out;
    }

    @Override
    public String convertToPython() {
        String out = "";
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
