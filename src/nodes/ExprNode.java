package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ExprNode implements JottTree {
    public static ExprNode parseExprNode(ArrayList<Token> tokens) {
        return null;
    }

    @Override
    public String convertToJott() {
        String out = "";
        return out;
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
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}