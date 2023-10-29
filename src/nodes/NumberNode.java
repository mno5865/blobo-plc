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
        //todo this is a base node so it'd only be instantiated if it's true? i'm guessing this is always true but check this to be sure
        return true;
    }

    @Override
    public boolean isInteger() {
        return this.evaluate() % 1 == 0;
    }

    public double evaluate() {
        return Double.parseDouble(number.getToken());
    }
}
