package nodes;

import errors.SyntaxException;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class NumberNode implements ExprNode {
    private final Token number;

    public NumberNode(Token number) {
        this.number = number;
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) {
        return new NumberNode(tokens.remove(0));
    }

    @Override
    public String convertToJott() {
        return this.number.getToken();
    }

    @Override
    public String convertToJava(String className) {
        return this.number.getToken();
    }

    @Override
    public String convertToC() {
        return this.number.getToken();
    }

    @Override
    public String convertToPython() {
        return this.number.getToken();
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
