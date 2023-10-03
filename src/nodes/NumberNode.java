package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class NumberNode implements JottTree {
    private Token number;

    public NumberNode(Token number) {
        this.number = number;
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.NUMBER) {
            throw new SyntaxException("", token.getFilename(), token.getLineNum()); //todo syntax exception
        }
        return new NumberNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.number.getToken();
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