package nodes;

import provided.Token;

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
        return this.number.getTokenString();
    }

    @Override
    public String convertToJava() {
        return this.number.getTokenString();
    }

    @Override
    public String convertToC() {
        return this.number.getTokenString();
    }

    @Override
    public String convertToPython() {
        return this.number.getTokenString();
    }

    @Override
    public void validateTree() {
    }

    public boolean isInteger() {
        return !this.number.getTokenString().contains("."); //todo that weird error when I remove !
    }


    @Override
    public String getType() {
        if (isInteger()) return "Integer";
        return "Double";
    }

    @Override
    public Token getToken() {
        return number;
    }

    @Override
    public double evaluate() {
        return Double.parseDouble(number.getTokenString());
    }
}
