package nodes;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class WhileLoopNode implements JottTree {
    public WhileLoopNode(){

    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens){
        return new WhileLoopNode();
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
