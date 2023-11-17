package nodes;

import errors.SemanticException;
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
        return this.stringLiteral.getTokenString();
    }

    @Override
    public String convertToJava(String className) {
        return this.stringLiteral.getTokenString();
    }

    @Override
    public String convertToC() {
        return this.stringLiteral.getTokenString();
    }

    @Override
    public String convertToPython() {
        return this.stringLiteral.getTokenString();
    }

    @Override
    public void validateTree() throws SemanticException {
        // todo someone should check to make sure this is correct (i'm pretty sure base nodes can't have any errors)
        char[] characters = getToken().getTokenString().toCharArray();
        for (int i = 1; i < characters.length - 1; i++) {
            if (!(Character.isLetterOrDigit(characters[i]) || characters[i] == ' '))
                throw new SemanticException("Strings can only contain alphanumeric characters or spaces",
                        stringLiteral);

        }
    }

    @Override
    public String getType() {
        return "String";
    }

    @Override
    public Token getToken() {
        return stringLiteral;
    }
}
