package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ReturnStmtNode implements JottTree {
    public ReturnStmtNode() {

    }

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) {
        return new ReturnStmtNode();
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
