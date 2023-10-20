package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class StringNode implements ExprNode {
    private final Token stringLiteral;

    public StringNode(Token str) {
        this.stringLiteral = str;
    }

    public static StringNode parseStringNode(ArrayList<Token> tokens) throws SyntaxException {
        Token token = tokens.get(0);
        if (token.getTokenType() != TokenType.STRING) {
            throw new SyntaxException("parseString expects string", token.getFilename(), token.getLineNum());
        }
        return new StringNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.stringLiteral.getToken();
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
