package nodes;

import errors.SyntaxException;
import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class IfStmtNode implements JottTree {
    public IfStmtNode(){

    }

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws SyntaxException {
        return new IfStmtNode();
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
        String out = "";
        return out;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
